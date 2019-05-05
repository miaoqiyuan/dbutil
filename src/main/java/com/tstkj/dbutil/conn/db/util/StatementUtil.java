package com.tstkj.dbutil.conn.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 
 * @author mqy
 *
 */
public class StatementUtil {

	public static PreparedStatement create(Connection conn, String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}

	public static Statement createStatement(Connection connection) throws SQLException {
		return connection.createStatement();
	}

	public static PreparedStatement createPreparedStatement(Connection connection, String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	public static void close(PreparedStatement p) throws SQLException {
		if (p != null) {
			p.close();
		}
	}

}
