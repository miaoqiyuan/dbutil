package com.tstkj.dbutil.conn.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.tstkj.dbutil.conn.db.util.ConnList;
import com.tstkj.dbutil.conn.db.util.JDBCUtil;
import com.tstkj.dbutil.conn.entry.JDBCEntry;

public class ConnectionStatusTools {

	protected Connection checkAndCreate(String dbType, String dbName, JDBCEntry entry)
			throws ClassNotFoundException, SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append(dbType).append("-").append(entry.getUrl()).append(dbName);
		Connection conn = ConnList.connList.get(sb.toString());
		if (ConnList.connList.get(sb.toString()) == null) {
			conn = JDBCUtil.getConnection(entry);
			ConnList.connList.put(sb.toString(), conn);
		}
		return conn;
	}

	protected boolean test(JDBCEntry entry) throws SQLException, ClassNotFoundException {
		Connection conn = JDBCUtil.getConnection(entry);
		if (conn != null) {
			return true;
		}
		JDBCUtil.close(conn);
		return false;
	}

	protected void close(String dbType, String dbName, JDBCEntry entry) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append(dbType).append("-").append(entry.getUrl()).append(dbName);
		Connection conn = ConnList.connList.get(sb.toString());
		if (conn != null) {
			JDBCUtil.close(conn);
		}
		if (ConnList.connList.containsKey(sb.toString())) {
			ConnList.connList.remove(sb.toString());
		}
	}

}
