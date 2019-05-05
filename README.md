# dbutil
### 主要用于数据库的结构查询,适用于动态复制表结构等场景.
### 数据库连接池基于commons-pool2简易封装,使用PreparedStatement封装了通用的crud方法,可基于连接池信息直接执行sql语句
## 数据库查询工具
>> 简易版数据库连接池
>> 数据库结构查询工具
>>> 数据库表查询
>>>> mysql
>>>> oracle
>>>> hive
>>>> sqlserver
>>>> DB2(TODO)
>>>> MangoDB(TODO)


>>> 数据库表结构查询
>>>> mysql
>>>> oracle
>>>> hive
>>>> sqlserver
>>>> DB2(TODO)
>>>> MangoDB(TODO)

>>> 数据库视图查询
>>>> mysql
>>>> oracle
>>>> hive
>>>> sqlserver
>>>> DB2(TODO)
>>>> MangoDB(TODO)

## 使用方法
#### 数据库连接池
``` java
	public void pool(){
			ConnectionInfoEntry info = new ConnectionInfoEntry();
			info.setDriverClass("com.mysql.jdbc.Driver");
			info.setUrl("jdbc:mysql://ip:3306/test");
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
```
#### 查询工具
``` java
	public void test(){
			vo.setDbName("test");
			vo.setType(DBTYPE.MYSQL);
			vo.setUrl("ip:3306");
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
```
## 其他
具体使用方式请参见 com.tstkj.dbutil.App

