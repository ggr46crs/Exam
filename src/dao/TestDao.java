package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TestListSubject;

public class TestDao extends Dao {


	private String baseSql = "select * from test ";




	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		try {
			//リザルトセットを全権操作
			while (rSet.next()) {
				//学生インスタンスを初期化
				TestListSubject test = new TestListSubject();
				//学生インスタンスに検索結果をセット
				test.setStudentNo(rSet.getString("student_no"));
				test.setSubjectCd(rSet.getString("subject_cd"));
				test.setSchoolCd(rSet.getString("school_cd"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setName(rSet.getString("name"));
				test.setEntYear(rSet.getInt("ent_year"));
				test.setClassNum(rSet.getString("class_num"));
				test.setAttend(rSet.getBoolean("is_attend"));
				//リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	private List<TestListSubject> postFilter2(ResultSet rSet) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		try {
			//リザルトセットを全権操作
			while (rSet.next()) {
				//学生インスタンスを初期化
				TestListSubject test = new TestListSubject();
				//学生インスタンスに検索結果をセット
				test.setStudentNo(rSet.getString("student_no"));
				test.setSubjectCd(rSet.getString("subject_cd"));
				test.setSchoolCd(rSet.getString("school_cd"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setSubjectName(rSet.getString("name"));
				//リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<TestListSubject> filter(int ent_year, String classNum, String subject) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "inner join student on test.student_no = student.no where ent_year=? and test.class_num=? and subject_cd=?";
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

	public List<TestListSubject> filter(String studentNo) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "inner join subject on test.subject_cd = subject.cd where test.student_no=?";
		//SQL文のソート
		String order = " order by test.subject_cd asc";


		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(1,studentNo);
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter2(rSet);
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
