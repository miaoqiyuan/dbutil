package com.tstkj.dbutil.conn.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tstkj.dbutil.conn.entry.ConnectionInfoVO;
import com.tstkj.dbutil.conn.entry.JDBCEntry;
import com.tstkj.dbutil.conn.entry.TableBaseInfo;
import com.tstkj.dbutil.conn.entry.TableInfoVO;
import com.tstkj.dbutil.util.StringUtils;
/**
 * 
 * @author mqy
 *
 */
public class CovertTools {

	protected List<String> convert2TableName(PreparedStatement statement) throws SQLException {
		ResultSet result = statement.executeQuery();
		List<String> list = new ArrayList<>();
		while (result.next()) {
			list.add(result.getString("name"));
		}
		result.close();
		return list;
	}

	protected List<TableInfoVO> convert2TableInfo(PreparedStatement statement) throws SQLException {
		ResultSet rs = statement.executeQuery();
		List<TableInfoVO> result = new ArrayList<>();
		TableInfoVO info;
		while (rs.next()) {
			info = new TableInfoVO();
			info.setColumnName(rs.getString("COL_NAME"));
			info.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
			info.setColumnLength(rs.getString("COLUMN_LENGTH"));
			info.setColumnDesc(rs.getString("COMMENT"));
			info.setColumnType(rs.getString("DATA_TYPE"));
			info.setPrimaryKey(rs.getString("COLUMN_KEY"));
			result.add(info);
		}
		rs.close();
		return result;
	}

	protected List<TableBaseInfo> convert2BaseInfo(ResultSet rs) throws SQLException {
		List<TableBaseInfo> tableNames = new ArrayList<>();
		TableBaseInfo baseInfo = null;
		while (rs.next()) {
			baseInfo = new TableBaseInfo();
			baseInfo.setTableName(rs.getString("table_name"));
			baseInfo.setTableType(rs.getString("table_type"));
			tableNames.add(baseInfo);
		}
		return tableNames;
	}

	protected JDBCEntry convert2JDBCEntry(ConnectionInfoVO vo, String url,  String driver,
			String defautName, String defautPwd) {
		JDBCEntry entry = new JDBCEntry();
		entry.setUrl(this.buildUrl(url, vo));
		entry.setDriver(driver);
		if (StringUtils.isNullOrEmpty(vo.getUserName())) {
			entry.setUserName(defautName);
		} else {
			entry.setUserName(vo.getUserName());
		}

		if (StringUtils.isNullOrEmpty(vo.getUserPassword())) {
			entry.setPassword(defautPwd);
		} else {
			entry.setPassword(vo.getUserPassword());
		}
		return entry;
	}

	private String buildUrl(String temp, ConnectionInfoVO vo) {
		return temp.replace("@{url}", vo.getUrl()).replace("@{dbname}", vo.getDbName());
	}
}
