package com.tstkj.dbutil.conn.db;

import java.sql.Connection;
import java.util.List;

import com.tstkj.dbutil.conn.db._interface.DBUtil;
import com.tstkj.dbutil.conn.entry.TableBaseInfo;
import com.tstkj.dbutil.conn.entry.TableInfoVO;
import com.tstkj.dbutil.exception.DBUtilException;

//TODO 
public class DB2 implements DBUtil {

//	private final String DB2_URL = "jdbc:db2://@{host}/@{database}";
//	private final String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
//	private final String DEFAULT_USER = "root";
//	private final String DEFAULT_PWD = "tstkj123";

	@Override
	public boolean testConnection() throws DBUtilException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws DBUtilException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableBaseInfo> listTablesAndViews() throws DBUtilException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> listTables() throws DBUtilException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableInfoVO> listTableInfo(String tableName) throws DBUtilException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws DBUtilException {
		// TODO Auto-generated method stub

	}

}
