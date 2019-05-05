package com.tstkj.dbutil.conn.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class SQLServer implements DBUtil {

	private final String SQL_URL = "jdbc:sqlserver://@{url};DatabaseName=@{dbname}";
	private final String SQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final String DEFAULT_USER = "sa";
	private final String DEFAULT_PWD = "tstkj123";

	private final String LIST_ALL_TABLE_VIEW = "select name as table_name, xtype as table_type from sysobjects where LOWER(xtype) = 'u'"
			+ " union select name as table_name, xtype as table_type from sysobjects where LOWER(xtype) = 'v'";
	private final String LIST_TABLE = "select name as table_name from sysobjects where LOWER(xtype) = 'u'";
	private final String LIST_TABLE_INFO = "sp_columns @{table}";

	private ConnectionInfoVO vo;
	private JDBCEntry entry;
	private ConnectionStatusTools tools;
	private CovertTools convertTools = null;

	public SQLServer(ConnectionInfoVO vo) {
		this.vo = vo;
		tools = new ConnectionStatusTools();
		convertTools = new CovertTools();
		entry = convertTools.convert2JDBCEntry(vo, SQL_URL, SQL_DRIVER, DEFAULT_USER, DEFAULT_PWD);
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
	public Connection getConnection() throws DBUtilException {
		try {
			return tools.checkAndCreate(DBTYPE.MYSQL, vo.getDbName(), entry);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
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
			String sql = LIST_TABLE_INFO.replace("@{table}", tableName);
			statement = StatementUtil.create(this.getConnection(), sql);
			ResultSet rs = statement.executeQuery();
			TableInfoVO columnInfo = null;
			List<TableInfoVO> list = new ArrayList<>();
			while (rs.next()) {
				columnInfo = new TableInfoVO();
				columnInfo.setColumnName(rs.getString("column_name"));
				columnInfo.setColumnType(rs.getString("type_name"));

				columnInfo.setColumnLength(rs.getString("length"));
				columnInfo.setColumnDefault(rs.getString("column_def"));

				columnInfo.setColumnType(rs.getString("type_name"));

				list.add(columnInfo);
			}
			System.out.println(list);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
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
	public void close() throws DBUtilException {
		try {
			tools.close(DBTYPE.SQLSERVER, vo.getDbName(), entry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
