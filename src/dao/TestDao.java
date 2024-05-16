
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Test;


public class TestDao extends Dao{

	private String baseSql = "select * from test ";



	private List<Test> postFilter(ResultSet rSet,School school) throws Exception {
		//リストを初期化
		List<Test> list = new ArrayList<>();
		try {
			//リザルトセットを全権操作
			while (rSet.next()) {
				//学生インスタンス
				Test test = new Test();
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


	public List<Test> filter(int ent_year, String classNum, Subject subject, int num, School school) throws Exception {
		//リストを初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "inner join student on test.student_no = student.no inner join subject on test.subject_cd = subject.cd where ent_year=? and test.class_num=? and subject_name=? and test.school_cd=?";
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
			statement.setString(3,subject.getName());

			statement.setString(4, school.getCd());
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
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
	public Test get(String student_no) throws Exception {
		//学生インスタンスを初期化
		Test test = new Test();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from student where student_no=?");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, student_no);
			//プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			//学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				//リザルトセットが存在する場合
				//学生インスタンスに検索結果をセット
				test.setStudent_no(rSet.getString("student_no"));
				test.setSubject_cd(rSet.getString("subject_cd"));
				test.setSchool_cd(rSet.getString("school_cd"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				//学校フィールドには学校コードで検索した学校インスタンスをセット
				test.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				//リザルトセットが存在しない場合
				//学生インスタンスにnullをセット
				test = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				try {
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

		return test;
	}
}