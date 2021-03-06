/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.configuration.persistence;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerProvider;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.net.URI;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.sql.DataSource;

import org.apache.felix.cm.NotCachablePersistenceManager;
import org.apache.felix.cm.PersistenceManager;
import org.apache.felix.cm.file.ConfigurationHandler;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 * @author Sampsa Sohlman
 */
public class ConfigurationPersistenceManager
	implements NotCachablePersistenceManager, PersistenceManager,
			   ReloadablePersistenceManager {

	@Override
	public void delete(final String pid) throws IOException {
		if (System.getSecurityManager() != null) {
			try {
				AccessController.doPrivileged(
					new PrivilegedExceptionAction<Void>() {

						@Override
						public Void run() throws Exception {
							doDelete(pid);

							return null;
						}

					});
			}
			catch (PrivilegedActionException pae) {
				throw (IOException)pae.getException();
			}
		}
		else {
			doDelete(pid);
		}
	}

	@Override
	public boolean exists(String pid) {
		Lock lock = _readWriteLock.readLock();

		try {
			lock.lock();

			return _dictionaries.containsKey(pid);
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public Enumeration<?> getDictionaries() {
		Lock lock = _readWriteLock.readLock();

		try {
			lock.lock();

			return Collections.enumeration(_dictionaries.values());
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public Dictionary<?, ?> load(String pid) {
		Lock lock = _readWriteLock.readLock();

		try {
			lock.lock();

			return _dictionaries.get(pid);
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void reload(String pid) throws IOException {
		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			_dictionaries.remove(pid);

			if (hasPid(pid)) {
				Dictionary<?, ?> dictionary = getDictionary(pid);

				_dictionaries.put(pid, dictionary);
			}
		}
		finally {
			lock.unlock();
		}
	}

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	public void start() {
		Lock readLock = _readWriteLock.readLock();
		Lock writeLock = _readWriteLock.writeLock();

		try {
			readLock.lock();

			if (hasConfigurationTable()) {
				readLock.unlock();
				writeLock.lock();

				try {
					_verifyConfigurations();
				}
				finally {
					readLock.lock();
					writeLock.unlock();
				}
			}
			else {
				readLock.unlock();
				writeLock.lock();

				try {
					createConfigurationTable();
				}
				finally {
					readLock.lock();
					writeLock.unlock();
				}
			}

			populateDictionaries();
		}
		finally {
			readLock.unlock();
		}
	}

	public void stop() {
		_dictionaries.clear();
	}

	@Override
	public void store(
			final String pid,
			@SuppressWarnings("rawtypes") final Dictionary dictionary)
		throws IOException {

		if (System.getSecurityManager() != null) {
			try {
				AccessController.doPrivileged(
					new PrivilegedExceptionAction<Void>() {

						@Override
						public Void run() throws Exception {
							doStore(pid, dictionary);

							return null;
						}

					});
			}
			catch (PrivilegedActionException pae) {
				throw (IOException)pae.getException();
			}
		}
		else {
			doStore(pid, dictionary);
		}
	}

	protected String buildSQL(String sql) throws IOException {
		DB db = DBManagerUtil.getDB();

		return db.buildSQL(sql);
	}

	protected void cleanUp(
		Connection connection, Statement statement, ResultSet resultSet) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
		}
		catch (SQLException sqle) {
			ReflectionUtil.throwException(sqle);
		}
		finally {
			try {
				if (statement != null) {
					statement.close();
				}
			}
			catch (SQLException sqle) {
				ReflectionUtil.throwException(sqle);
			}
			finally {
				try {
					if (connection != null) {
						connection.close();
					}
				}
				catch (SQLException sqle) {
					ReflectionUtil.throwException(sqle);
				}
			}
		}
	}

	protected void createConfigurationTable() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			statement = connection.createStatement();

			statement.executeUpdate(
				buildSQL(
					"create table Configuration_ (configurationId " +
						"VARCHAR(255) not null primary key, dictionary TEXT)"));
		}
		catch (IOException | SQLException e) {
			ReflectionUtil.throwException(e);
		}
		finally {
			cleanUp(connection, statement, resultSet);
		}
	}

	protected void deleteFromDatabase(String pid) throws IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = prepareStatement(
				connection,
				"delete from Configuration_ where configurationId = ?");

			preparedStatement.setString(1, pid);

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqle) {
			throw new IOException(sqle);
		}
		finally {
			cleanUp(connection, preparedStatement, null);
		}
	}

	protected void doDelete(String pid) throws IOException {
		ConfigurationModelListener configurationModelListener = null;

		if (!pid.endsWith("factory") && hasPid(pid)) {
			Dictionary dictionary = getDictionary(pid);

			String pidKey = (String)dictionary.get(
				ConfigurationAdmin.SERVICE_FACTORYPID);

			if (pidKey == null) {
				pidKey = (String)dictionary.get(Constants.SERVICE_PID);
			}

			if (pidKey == null) {
				pidKey = pid;
			}

			configurationModelListener =
				ConfigurationModelListenerProvider.
					getConfigurationModelListener(pidKey);
		}

		if (configurationModelListener != null) {
			configurationModelListener.onBeforeDelete(pid);
		}

		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			Dictionary<?, ?> dictionary = _dictionaries.remove(pid);

			if ((dictionary != null) && hasPid(pid)) {
				deleteFromDatabase(pid);
			}
		}
		finally {
			lock.unlock();
		}

		if (configurationModelListener != null) {
			configurationModelListener.onAfterDelete(pid);
		}
	}

	protected void doStore(
			String pid, @SuppressWarnings("rawtypes") Dictionary dictionary)
		throws IOException {

		ConfigurationModelListener configurationModelListener = null;

		if (hasPid(pid)) {
			configurationModelListener =
				ConfigurationModelListenerProvider.
					getConfigurationModelListener(pid);
		}

		if (configurationModelListener != null) {
			configurationModelListener.onBeforeSave(pid, dictionary);
		}

		Dictionary<Object, Object> newDictionary = _copyDictionary(dictionary);

		String fileName = (String)newDictionary.get(
			_FELIX_FILE_INSTALL_FILENAME);

		if (fileName != null) {
			File file = new File(URI.create(fileName));

			newDictionary.put(_FELIX_FILE_INSTALL_FILENAME, file.getName());
		}

		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			storeInDatabase(pid, newDictionary);

			if (fileName != null) {
				newDictionary.put(_FELIX_FILE_INSTALL_FILENAME, fileName);
			}

			_dictionaries.put(pid, newDictionary);
		}
		finally {
			lock.unlock();
		}

		if (configurationModelListener != null) {
			configurationModelListener.onAfterSave(pid, dictionary);
		}
	}

	protected Dictionary<?, ?> getDictionary(String pid) throws IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = prepareStatement(
				connection,
				"select dictionary from Configuration_ where configurationId " +
					"= ?");

			preparedStatement.setString(1, pid);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return toDictionary(resultSet.getString(1));
			}

			return _emptyDictionary;
		}
		catch (SQLException sqle) {
			return ReflectionUtil.throwException(sqle);
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected boolean hasConfigurationTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = prepareStatement(
				connection, "select count(*) from Configuration_");

			resultSet = preparedStatement.executeQuery();

			int count = 0;

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			if (count >= 0) {
				return true;
			}

			return false;
		}
		catch (IOException | SQLException e) {
			return false;
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected boolean hasPid(String pid) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = prepareStatement(
				connection,
				"select count(*) from Configuration_ where configurationId = " +
					"?");

			preparedStatement.setString(1, pid);

			resultSet = preparedStatement.executeQuery();

			int count = 0;

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			if (count > 0) {
				return true;
			}

			return false;
		}
		catch (IOException | SQLException e) {
			return ReflectionUtil.throwException(e);
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected void populateDictionaries() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = connection.prepareStatement(
				buildSQL(
					"select configurationId, dictionary from Configuration_ " +
						"ORDER BY configurationId ASC"),
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String pid = resultSet.getString(1);
				String dictionaryString = resultSet.getString(2);

				_dictionaries.putIfAbsent(pid, toDictionary(dictionaryString));
			}
		}
		catch (IOException | SQLException e) {
			ReflectionUtil.throwException(e);
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected PreparedStatement prepareStatement(
			Connection connection, String sql)
		throws IOException, SQLException {

		return connection.prepareStatement(buildSQL(sql));
	}

	protected void store(ResultSet resultSet, Dictionary<?, ?> dictionary)
		throws IOException, SQLException {

		OutputStream outputStream = new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(outputStream, dictionary);

		resultSet.updateString(2, outputStream.toString());
	}

	protected void storeInDatabase(String pid, Dictionary<?, ?> dictionary)
		throws IOException {

		UnsyncByteArrayOutputStream outputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(outputStream, dictionary);

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(
				buildSQL(
					"update Configuration_ set dictionary = ? where " +
						"configurationId = ?"));

			preparedStatement.setString(1, outputStream.toString());
			preparedStatement.setString(2, pid);

			if (preparedStatement.executeUpdate() == 0) {
				preparedStatement = prepareStatement(
					connection,
					"insert into Configuration_ (configurationId, " +
						"dictionary) values (?, ?)");

				preparedStatement.setString(1, pid);
				preparedStatement.setString(2, outputStream.toString());

				preparedStatement.executeUpdate();
			}

			connection.commit();
		}
		catch (SQLException sqle) {
			ReflectionUtil.throwException(sqle);
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);

			outputStream.close();
		}
	}

	@SuppressWarnings("unchecked")
	protected Dictionary<?, ?> toDictionary(String dictionaryString)
		throws IOException {

		Dictionary<Object, Object> dictionary = ConfigurationHandler.read(
			new UnsyncByteArrayInputStream(
				dictionaryString.getBytes(StringPool.UTF8)));

		String fileName = (String)dictionary.get(_FELIX_FILE_INSTALL_FILENAME);

		if (fileName != null) {
			File file = new File(
				PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, fileName);

			file = file.getAbsoluteFile();

			URI uri = file.toURI();

			dictionary.put(_FELIX_FILE_INSTALL_FILENAME, uri.toString());
		}

		return dictionary;
	}

	private Dictionary<Object, Object> _copyDictionary(
		Dictionary<?, ?> dictionary) {

		Dictionary<Object, Object> newDictionary = new HashMapDictionary<>();

		Enumeration<?> keys = dictionary.keys();

		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();

			newDictionary.put(key, dictionary.get(key));
		}

		return newDictionary;
	}

	private void _verifyConfigurations() {
		try (Connection connection = _dataSource.getConnection();
			PreparedStatement selectPS = connection.prepareStatement(
				buildSQL(
					"select configurationId, dictionary from Configuration_ " +
						"where dictionary like " +
							"'%felix.fileinstall.filename=\"file:%'"));
			PreparedStatement updatePS = connection.prepareStatement(
				buildSQL(
					"update Configuration_ set dictionary = ? where " +
						"configurationId = ?"));
			ResultSet rs = selectPS.executeQuery()) {

			while (rs.next()) {
				String pid = rs.getString(1);
				String dictionaryString = rs.getString(2);

				@SuppressWarnings("unchecked")
				Dictionary<Object, Object> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				String fileName = (String)dictionary.get(
					_FELIX_FILE_INSTALL_FILENAME);

				File file = new File(URI.create(fileName));

				dictionary.put(_FELIX_FILE_INSTALL_FILENAME, file.getName());

				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				ConfigurationHandler.write(
					unsyncByteArrayOutputStream, dictionary);

				updatePS.setString(1, unsyncByteArrayOutputStream.toString());

				updatePS.setString(2, pid);

				updatePS.executeUpdate();
			}
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	private static final String _FELIX_FILE_INSTALL_FILENAME =
		"felix.fileinstall.filename";

	private static final Dictionary<?, ?> _emptyDictionary = new Hashtable<>();

	private DataSource _dataSource;
	private final ConcurrentMap<String, Dictionary<?, ?>> _dictionaries =
		new ConcurrentHashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock(
		true);

}