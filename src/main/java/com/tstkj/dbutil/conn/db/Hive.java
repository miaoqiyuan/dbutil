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

public class Hive implements DBUtil {

	private final String HIVE_URL = "jdbc:hive2://@{url}";
	private final String HIVE_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	private final String HIVE_USER = "hdfs";
	private final String HIVE_PASSWORD = "tstkj123";

//	private final String CHECK_TALBE_NAME = "DESC @{tablename}";
//	private final String CHECK_TALBE_VALUE = "SELECT * FROM @{tablename} LIMIT 1";
//	private final String DROP_TABLE = "drop table @{tablename}";
//	private final String CREATE_TABLE = "create table if not exists @{tablename} (@{column}) clustered by (@{key}) into 8 buckets stored as orc TBLPROPERTIES('transactional'='true')";
	private final String SHOW_TABLE = "show tables";
	private final String DESC_TABLE = "desc  @{tablename}";

	private ConnectionInfoVO vo;
	private JDBCEntry entry;
	private ConnectionStatusTools tools = null;

	private CovertTools convertTools = null;

	public Hive(ConnectionInfoVO vo) {
		this.vo = vo;
		tools = new ConnectionStatusTools();
		convertTools = new CovertTools();
		entry = convertTools.convert2JDBCEntry(vo, HIVE_URL, HIVE_DRIVER, HIVE_USER, HIVE_PASSWORD);
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
			return tools.checkAndCreate(DBTYPE.HIVE, vo.getDbName(), entry);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DBUtilException(e.getMessage());
		}
	}

	@Override
	public List<TableBaseInfo> listTablesAndViews() throws DBUtilException {
		return null;
	}

	@Override
	public List<String> listTables() throws DBUtilException {
		PreparedStatement statement = null;
		List<String> tables = new ArrayList<>();
		ResultSet rs = null;
		try {
			statement = StatementUtil.create(this.getConnection(), SHOW_TABLE);
			rs = statement.executeQuery();
			while (rs.next()) {
				tables.add(rs.getString("tab_name"));
			}
			return tables;
		} catch (SQLException e) {
			throw new DBUtilException(e.getMessage());
		} finally {
			try {
				StatementUtil.close(statement);
				if (rs != null) {
					rs.close();
				}
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
			String sql = DESC_TABLE.replace("@{tablename}", tableName);
			statement = StatementUtil.create(this.getConnection(), sql);
			ResultSet rs = statement.executeQuery();
			TableInfoVO columnInfo = null;
			List<TableInfoVO> list = new ArrayList<>();
			while (rs.next()) {
				columnInfo = new TableInfoVO();
				columnInfo.setColumnName(rs.getString("column_name"));
				columnInfo.setColumnType(rs.getString("data_type"));
				columnInfo.setColumnLength(rs.getString("comment"));
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
			tools.close(DBTYPE.HIVE, vo.getDbName(), entry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
