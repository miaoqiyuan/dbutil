package com.tstkj.dbutil.conn.db;

import java.sql.Connection;
import java.util.List;

import com.tstkj.dbutil.conn.db._interface.DBUtil;
import com.tstkj.dbutil.conn.entry.TableBaseInfo;
import com.tstkj.dbutil.conn.entry.TableInfoVO;
import com.tstkj.dbutil.exception.DBUtilException;

//TODO 
public class MangoDB implements DBUtil {

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
