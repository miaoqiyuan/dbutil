package com.tstkj.dbutil.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.tstkj.dbutil.exception.DBUtilException;
import com.tstkj.dbutil.pool.base.ConnectionPool;
import com.tstkj.dbutil.pool.entry.ConnectionInfoEntry;

/**
 * 自定义连接池
 * 
 * @author mqy
 *
 */
public class ConnPool {

	private GenericObjectPoolConfig<Connection> config;
	private GenericObjectPool<Connection> connPool;

	public GenericObjectPoolConfig<Connection> getConfig() {
		return config;
	}

	public void setConfig(GenericObjectPoolConfig<Connection> config) {
		this.config = config;
	}

	public ConnPool(ConnectionInfoEntry info) {
		this(info.getDriverClass(), info.getUrl(), info.getUserName(), info.getPassword());
	}

	public ConnPool(String driver, String url, String userName, String password) {
		ConnectionPool pool = new ConnectionPool(driver, url, userName, password);
		if (config != null) {
			connPool = new GenericObjectPool<>(pool, config);
		} else {
			connPool = new GenericObjectPool<>(pool);
		}
	}

	public Connection get() throws Exception {
		if (connPool != null) {
			return connPool.borrowObject();
		} else {
			throw new DBUtilException("连接池未初始化!");
		}
	}

	public ResultSet excuteQusery(String sql) throws DBUtilException {
		PreparedStatement statument = null;
		try {
			statument = get().prepareStatement(sql);
			return statument.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		} finally {
			if (statument != null) {
				try {
					statument.close();
				} catch (SQLException e) {
					throw new DBUtilException(e.getMessage());
				}
			}
		}
	}

	public int excuteUpdate(String sql) throws DBUtilException {
		PreparedStatement statument = null;
		try {
			statument = get().prepareStatement(sql);
			return statument.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		} finally {
			if (statument != null) {
				try {
					statument.close();
				} catch (SQLException e) {
					throw new DBUtilException(e.getMessage());
				}
			}
		}
	}

	public boolean excuteDel(String sql) throws DBUtilException {
		PreparedStatement statument = null;
		try {
			statument = get().prepareStatement(sql);
			return statument.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		} finally {
			if (statument != null) {
				try {
					statument.close();
				} catch (SQLException e) {
					throw new DBUtilException(e.getMessage());
				}
			}
		}
	}

	public boolean excuteAdd(String sql) throws DBUtilException {
		PreparedStatement statument = null;
		try {
			statument = get().prepareStatement(sql);
			return statument.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		} finally {
			if (statument != null) {
				try {
					statument.close();
				} catch (SQLException e) {
					throw new DBUtilException(e.getMessage());
				}
			}
		}
	}

}
