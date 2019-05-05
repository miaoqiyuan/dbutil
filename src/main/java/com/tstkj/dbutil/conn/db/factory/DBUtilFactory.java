package com.tstkj.dbutil.conn.db.factory;

import com.tstkj.dbutil.conn.db.Mysql;
import com.tstkj.dbutil.conn.db.Oracle;
import com.tstkj.dbutil.conn.db.SQLServer;
import com.tstkj.dbutil.conn.db._interface.DBUtil;
import com.tstkj.dbutil.conn.db.util.DBTYPE;
import com.tstkj.dbutil.conn.entry.ConnectionInfoVO;

public class DBUtilFactory {

	private DBUtilFactory() {
	}

	public static DBUtil newInstance(String url, String userName, String password, String type) {
		ConnectionInfoVO vo = new ConnectionInfoVO();
		vo.setType(type);
		vo.setUrl(url);
		vo.setUserName(userName);
		vo.setUserPassword(password);
		return newInstance(vo);

	}

	public static DBUtil newInstance(ConnectionInfoVO vo) {
		return get(vo);
	}

	private static DBUtil get(ConnectionInfoVO vo) {
		switch (vo.getType().toLowerCase()) {
		case DBTYPE.MYSQL:
			return new Mysql(vo);
		case DBTYPE.ORACLE:
			return new Oracle(vo);
		case DBTYPE.SQLSERVER:
			return new SQLServer(vo);
		default:
			return new Mysql(vo);
		}
	}
}
