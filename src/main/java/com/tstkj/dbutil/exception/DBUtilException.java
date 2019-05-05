package com.tstkj.dbutil.exception;

/**
 *	 数据库工具异常
 * 
 * @author mqy
 *
 */
public class DBUtilException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8342416384923317382L;

	private String errorCode;

	private String description;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DBUtilException() {
		super();
	}

	public DBUtilException(String description) {
		this.errorCode = "500";
		this.description = description;
	}

	public DBUtilException(String errorCode, String description) {
		super(description);
		this.errorCode = errorCode;
		this.description = description;
	}

	public DBUtilException(String errorCode, String message, String description) {
		super(message);
		this.errorCode = errorCode;
		this.description = description;
	}

	public DBUtilException(Throwable cause, String errorCode, String description) {
		super(cause);
		this.errorCode = errorCode;
		this.description = description;
	}

	public DBUtilException(Throwable cause, String message, String errorCode, String description) {
		super(message, cause);
		this.errorCode = errorCode;
		this.description = description;
	}

}
