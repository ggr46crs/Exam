package dao;

import java.sql.Connection;

public class Dao {
	static DataSource ds;

	public Connection getConnection() throws Exception {
		//データベースへのコネクションを返却
		return ds.getConnection
	}
}