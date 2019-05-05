package com.tstkj.dbutil.pool.base;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.tstkj.dbutil.pool.entry.ConnectionInfoEntry;

/**
 * 
 * @author mqy
 *
 */
public class ConnectionPool extends BasePooledObjectFactory<Connection> {

	private ConnectionInfoEntry info;

	public ConnectionPool(ConnectionInfoEntry info) {
		this.info = info;
	}

	public ConnectionPool(String driverClass, String url, String userName, String password) {
		info = new ConnectionInfoEntry();
		info.setDriverClass(driverClass);
		info.setPassword(password);
		info.setUrl(url);
		info.setUserName(userName);
	}

	@Override
	public Connection create() throws Exception {
		Class.forName(info.getDriverClass());
		return DriverManager.getConnection(info.getUrl(), info.getUserName(), info.getPassword());
	}

	@Override
	public PooledObject<Connection> wrap(Connection obj) {
		return new DefaultPooledObject<Connection>(obj);
	}

}
