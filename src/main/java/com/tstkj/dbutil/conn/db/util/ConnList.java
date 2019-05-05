package com.tstkj.dbutil.conn.db.util;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author mqy
 *
 */
public class ConnList {
	public static  Map<String, Connection> connList = new HashMap<>(16);
}
