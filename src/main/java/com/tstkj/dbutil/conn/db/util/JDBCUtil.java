package com.tstkj.dbutil.conn.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tstkj.dbutil.conn.entry.JDBCEntry;
/**
 * 
 * @author mqy
 *
 */
public class JDBCUtil {

	public static Connection getConnection(JDBCEntry vo) throws ClassNotFoundException, SQLException {
		return getConnection(vo.getDriver(), vo.getUserName(), vo.getPassword(), vo.getUrl());
	}

	public static Connection getConnection(String driver, String userName, String password, String url)
			throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection(url, userName, password);
	}

	public static void close(Connection conn) throws SQLException {
		conn.close();
	}

}
