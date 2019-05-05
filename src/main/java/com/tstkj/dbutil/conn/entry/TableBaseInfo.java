package com.tstkj.dbutil.conn.entry;

import java.io.Serializable;

/**
 * 
 * @author mqy
 *
 */

public class TableBaseInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7753040417349386499L;

	private String tableName;
	private String tableType;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[");
		sb.append(this.tableName).append("===").append(this.tableType).append("]");
		return sb.toString();
	}

}
