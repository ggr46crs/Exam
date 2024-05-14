package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {


	private String baseSql = "select * from test ";




	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		//リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		try {
			//リザルトセットを全権操作
			while (rSet.next()) {
				//学生インスタンス
				TestListStudent test = new TestListStudent();
				//学生インスタンスに検索結果をセット
				test.setSubjectName(rSet.getString("name"));
				test.setSubjectCd(rSet.getString("subject_cd"));
				test.setNum(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}


	public List<TestListStudent> filter(Student student) throws Exception {
		//リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "inner join subject on test.subject_cd = subject.cd where student_no=? ";
		//SQL文のソート
		String order = " order by subject_cd asc, no asc";


		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, student.getNo());
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
			if(statement != null) {
				try{
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			//コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
}
