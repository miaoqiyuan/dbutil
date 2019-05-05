package com.tstkj.dbutil.pool.entry;

import com.tstkj.dbutil.conn.db.util.DBTYPE;

/**
 * 
 * @author mqy
 *
 */
public class ConnectionInfoEntry {

	private String driverClass;

	private String url;

	private String userName;

	private String password;

	private String dbType;

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		this.setDbType(url);
	}

	public String getDbType() {
		return dbType;
	}

	private void setDbType(String url) {
		if (url.contains("mysql")) {
			dbType = DBTYPE.MYSQL;
		} else if (url.contains("oracle")) {
			dbType = DBTYPE.ORACLE;
		} else if (url.contains("hive")) {
			dbType = DBTYPE.HIVE;
		} else if (url.contains("sqlserver")) {
			dbType = DBTYPE.SQLSERVER;
		}
	}

}
