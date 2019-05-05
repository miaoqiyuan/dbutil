package com.tstkj.dbutil.conn.entry;

/**
 * 
 * @author mqy
 *
 */
public class TableInfoVO {
	// 字段名称
	private String columnName;
	// 字段描述
	private String columnDesc;
	// 字段类型
	private String columnType;
	// 长度
	private String columnLength;
	// 是否主键(是,否)
	private String primaryKey;
	// 默认值
	private String columnDefault;

	private String dataType;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnDesc() {
		return columnDesc;
	}

	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(String columnLength) {
		this.columnLength = columnLength;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "[" + this.columnName + "=" + this.columnType + "]";
	}

}
