package com.tstkj.dbutil.conn.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.tstkj.dbutil.conn.db._interface.DBUtil;
import com.tstkj.dbutil.conn.db.util.DBTYPE;
import com.tstkj.dbutil.conn.db.util.StatementUtil;
import com.tstkj.dbutil.conn.entry.ConnectionInfoVO;
import com.tstkj.dbutil.conn.entry.JDBCEntry;
import com.tstkj.dbutil.conn.entry.TableBaseInfo;
import com.tstkj.dbutil.conn.entry.TableInfoVO;
import com.tstkj.dbutil.exception.DBUtilException;

/**
 * 
 * @author mqy
 *
 */
public class Mysql implements DBUtil {

	private final String MYSQL_URL = "jdbc:mysql://@{url}/@{dbname}";
	private final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private final String DEFAULT_USER = "root";
	private final String DEFAULT_PWD = "tstkj123";

	private final String LIST_TABLE = "select table_name from information_schema.tables where table_schema = '@{dbName}' and table_type = 'BASE TABLE' order by CREATE_TIME desc";
	private final String LIST_TABLE_INFO = "SELECT COLUMN_NAME AS COL_NAME, COLUMN_TYPE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH AS COLUMN_LENGTH,COLUMN_KEY,COLUMN_DEFAULT,COLUMN_COMMENT AS `COMMENT` FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '@{dbName}' AND TABLE_NAME = '@{table}'";
	private final String LIST_ALL_TABLE_VIEW = "select table_name, table_type  from information_schema.tables where table_schema = '@{dbName}' and table_type = 'BASE TABLE' union select table_name, table_type  from information_schema.tables where table_schema = '@{dbName}' and table_type = 'VIEW'";

	private ConnectionInfoVO vo;
	private JDBCEntry entry;
	private ConnectionStatusTools tools = null;

	private CovertTools convertTools = null;

	public Mysql(ConnectionInfoVO vo) {
		this.vo = vo;
		tools = new ConnectionStatusTools();
		convertTools = new CovertTools();
		entry = convertTools.convert2JDBCEntry(vo, MYSQL_URL, MYSQL_DRIVER, DEFAULT_USER, DEFAULT_PWD);
	}

	@Override
	public boolean testConnection() throws DBUtilException {
		try {
			return tools.test(entry);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DBUtilException(e.getMessage());
		}
	}

	@Override
	public void close() throws DBUtilException {
		try {
			tools.close(DBTYPE.MYSQL, vo.getDbName(), entry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() throws DBUtilException {
		try {
			return tools.checkAndCreate(DBTYPE.MYSQL, vo.getDbName(), entry);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DBUtilException(e.getMessage());
		}
	}

	@Override
	public List<TableBaseInfo> listTablesAndViews() throws DBUtilException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = StatementUtil.create(this.getConnection(),
					LIST_ALL_TABLE_VIEW.replace("@{dbName}", vo.getDbName()));
			rs = statement.executeQuery();
			return convertTools.convert2BaseInfo(rs);
		} catch (SQLException e) {
			throw new DBUtilException(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				StatementUtil.close(statement);
				this.close();
			} catch (SQLException e) {
				throw new DBUtilException(e.getMessage());
			}
		}
	}

	@Override
	public List<String> listTables() throws DBUtilException {
		PreparedStatement statement = null;
		try {
			statement = StatementUtil.create(this.getConnection(), LIST_TABLE.replace("@{dbName}", vo.getDbName()));
			return convertTools.convert2TableName(statement);
		} catch (SQLException e) {
			throw new DBUtilException(e.getMessage());
		} finally {
			try {
				StatementUtil.close(statement);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBUtilException(e.getMessage());
			}
		}
	}

	@Override
	public List<TableInfoVO> listTableInfo(String tableName) throws DBUtilException {
		PreparedStatement statement = null;
		try {
			String sql = LIST_TABLE_INFO.replace("@{dbName}", vo.getDbName()).replace("@{table}", tableName);
			statement = StatementUtil.create(this.getConnection(), sql);
			return convertTools.convert2TableInfo(statement);
		} catch (SQLException e) {
			throw new DBUtilException(e.getMessage());
		} finally {
			try {
				StatementUtil.close(statement);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBUtilException(e.getMessage());
			}
		}
	}

}
