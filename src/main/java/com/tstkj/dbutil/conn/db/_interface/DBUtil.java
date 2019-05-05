package com.tstkj.dbutil.conn.db._interface;

import java.sql.Connection;
import java.util.List;

import com.tstkj.dbutil.conn.entry.TableBaseInfo;
import com.tstkj.dbutil.conn.entry.TableInfoVO;
import com.tstkj.dbutil.exception.DBUtilException;

/**
 * 
 * @author mqy
 *
 */
public interface DBUtil {

	boolean testConnection() throws DBUtilException;

	Connection getConnection() throws DBUtilException;

	List<TableBaseInfo> listTablesAndViews() throws DBUtilException;

	List<String> listTables() throws DBUtilException;

	List<TableInfoVO> listTableInfo(String tableName) throws DBUtilException;

	void close() throws DBUtilException;

}
