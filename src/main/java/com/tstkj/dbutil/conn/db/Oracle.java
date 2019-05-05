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

public class Oracle implements DBUtil {

	private final String ORACLE_URL = "jdbc:oracle:thin:@@{url}:@{dbname}";
	private final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final String DEFAULT_USER = "orcl_test";
	private final String DEFAULT_PWD = "fucklenovo";

	private final String LIST_TABLE = "select table_name as name from user_tables";
	private final String LIST_TABLE_VIEW = "select table_name, 'T' as table_type from user_tables union select view_name as table_name, 'V' as table_type from user_views ";
	private final String LIST_TABLE_INFO = " select  utc.column_name as COL_NAME, utc.data_type as DATA_TYPE, utc.data_length as COLUMN_LENGTH,utc.data_default as COLUMN_DEFAULT, ucc.comments from   user_tab_columns utc inner join user_col_comments ucc on utc.table_name = ucc.table_name  and utc.column_name = ucc.column_name "
			+ " where utc.table_name = '@{table}' order by  column_id ";
//	private final String LIST_PK = " select col.column_name from user_constraints con inner join user_cons_columns col on con.constraint_name = col.constraint_name where con.constraint_type='P' "
//			+ " and col.table_name = '@{table}' ";

	private ConnectionInfoVO vo;
	private JDBCEntry entry;
	private ConnectionStatusTools tools;
	private CovertTools convertTools = null;

	public Oracle(ConnectionInfoVO vo) {
		this.vo = vo;
		tools = new ConnectionStatusTools();
		convertTools = new CovertTools();
		entry = convertTools.convert2JDBCEntry(vo, ORACLE_URL, ORACLE_DRIVER, DEFAULT_USER, DEFAULT_PWD);
	}

	@Override
	public boolean testConnection() throws DBUtilException {
		try {
			return tools.test(entry);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		}
	}

	@Override
	public Connection getConnection() throws DBUtilException {
		try {
			return tools.checkAndCreate(DBTYPE.ORACLE, vo.getDbName(), entry);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		}

	}

	@Override
	public List<TableBaseInfo> listTablesAndViews() throws DBUtilException {
		ResultSet rs = null;
		try {
			PreparedStatement statement = StatementUtil.create(this.getConnection(), LIST_TABLE_VIEW);
			rs = statement.executeQuery();
			return convertTools.convert2BaseInfo(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				throw new DBUtilException(e.getMessage());
			}

		}

	}

	@Override
	public List<String> listTables() throws DBUtilException {
		PreparedStatement statement = null;
		try {
			statement = StatementUtil.create(this.getConnection(), LIST_TABLE);
			return convertTools.convert2TableName(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		} finally {
			try {
				StatementUtil.close(statement);
			} catch (SQLException e) {
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

	@Override
	public void close() throws DBUtilException {
		try {
			tools.close(DBTYPE.ORACLE, vo.getDbName(), entry);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		}
	}

}
