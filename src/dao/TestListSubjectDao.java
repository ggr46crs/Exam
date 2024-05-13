package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {


	private String baseSql = "select * from test ";




	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		try {
			//リザルトセットを全権操作
			while (rSet.next()) {
				//学生インスタンス
				TestListSubject test = new TestListSubject();
				//学生インスタンスに検索結果をセット
				test.setEntYear(rSet.getInt("ent_year"));
				test.setStudentNo(rSet.getString("student_no"));
				test.setStudentName(rSet.getString("name"));
				test.setClassNum(rSet.getString("class_num"));
				//リストに追加
				test.putPoint(rSet.getInt("no"), rSet.getInt("point"));
				rSet.next();
				if(rSet.getInt("no")==2){
					test.putPoint(rSet.getInt("no"), rSet.getInt("point"));
				} else {
					rSet.previous();
				}
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}


	public List<TestListSubject> filter(int ent_year, String classNum, String subject, School school) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "inner join student on test.student_no = student.no where ent_year=? and test.class_num=? and subject_cd=? and test.school_cd=?";
		//SQL文のソート
		String order = " order by ent_year asc";


		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setInt(1, ent_year);
			//プリペアードステートメントに入学年度をバインド
			statement.setString(2, classNum);
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(3,subject);

			statement.setString(4, school.getCd());
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
