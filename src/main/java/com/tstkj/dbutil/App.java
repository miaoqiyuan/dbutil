package com.tstkj.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tstkj.dbutil.conn.db._interface.DBUtil;
import com.tstkj.dbutil.conn.db.factory.DBUtilFactory;
import com.tstkj.dbutil.conn.db.util.DBTYPE;
import com.tstkj.dbutil.conn.entry.ConnectionInfoVO;
import com.tstkj.dbutil.exception.DBUtilException;
import com.tstkj.dbutil.pool.ConnPool;
import com.tstkj.dbutil.pool.entry.ConnectionInfoEntry;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {

		ConnectionInfoVO vo = new ConnectionInfoVO();
		DBUtil db = null;

		// 测试开关
		int sign = 1;

		// mysql测试
		if (sign == 0) {
			vo.setDbName("test");
			vo.setType(DBTYPE.MYSQL);
			vo.setUrl("182.18.76.249:3306");
			vo.setUserName("root");
			vo.setUserPassword("tstkj123");
			db = DBUtilFactory.newInstance(vo);
			try {
				System.out.println("==mysql==" + db.listTablesAndViews());
			} catch (DBUtilException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// oracle测试
		if (sign == 0) {
			vo.setDbName("orcl");
			vo.setType(DBTYPE.ORACLE);
			vo.setUrl("182.18.76.10:1521");
			vo.setUserName("orcl_test");
			vo.setUserPassword("fucklenovo");
			db = DBUtilFactory.newInstance(vo);
			try {
				System.out.println("==oracle==" + db.listTableInfo("CAST_EMWS_MISSILE_TAB"));
			} catch (DBUtilException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		// sqlserver测试
		if (sign != 0) {
			vo.setDbName("BEWG_SCYY_MasterDB");
			vo.setType(DBTYPE.SQLSERVER);
			vo.setUrl("182.18.76.248:1433");
			vo.setUserName("sa");
			vo.setUserPassword("sa_12345");
			db = DBUtilFactory.newInstance(vo);
			try {
				System.out.println("==sqlserver==" + db.listTableInfo("CAST_EMWS_DISTANCE_TAB"));
			} catch (DBUtilException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 连接池测试
		if (sign == 0) {
			ConnectionInfoEntry info = new ConnectionInfoEntry();
			info.setDriverClass("com.mysql.jdbc.Driver");
			info.setUrl("jdbc:mysql://182.18.76.249:3306/test");
			info.setUserName("root");
			info.setPassword("tstkj123");
			ConnPool pool = new ConnPool(info);

			Connection conn = pool.get();
			String sql = "select * from user";
			PreparedStatement s = conn.prepareStatement(sql);
			ResultSet set = s.executeQuery();
			while (set.next()) {
				System.out.println(set.getString("name"));
			}
		}
	}
}
