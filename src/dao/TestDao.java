
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;




public class TestDao extends Dao{

	private String baseSql = "select * from test ";


	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		//学生インスタンスを初期化
		Test test = new Test();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from test where student_no=?, subject_cd=?, school_cd=?, no=?");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			//プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			//学校Daoを初期化
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			SchoolDao schoolDao = new SchoolDao();


			if (rSet.next()) {
				//リザルトセットが存在する場合
				//学生インスタンスに検索結果をセット
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd")));
				test.setSchool(schoolDao.get(rSet.getString("school_cd")));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
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

	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		//リストを初期化
		List<Test> list = new ArrayList<>();
		try {
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			//リザルトセットを全権操作
			while (rSet.next()) {
				//学生インスタンスを初期化
				Test test = new Test();
				//学生インスタンスに検索結果をセット
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd")));
				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				//リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<Test> filter(String entYear, String classNum, Subject subject, String f4, School school) throws Exception {
		//リストを初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "inner join student on test.student_no = student.no inner join subject on test.subject_cd = subject.cd where student.ent_year=? and test.class_num=? and subject.cd=? and test.school_cd=?";
		//SQL文のソート
		String order = " order by test.student_no asc";

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, entYear);
			//プリペアードステートメントに入学年度をバインド
			statement.setString(2, classNum);
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(3, subject.getCd());
			//プライベートステートメントを実行
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

	public boolean save(Test test, Connection connection) throws Exception {
		//コネクションを確立
		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数
		int count = 0;

		try {
			//データベースから学年を取得
			Test old = get(test.getStudent(),test.getSubject(),test.getSchool(),test.getNo());
			if (old == null) {
				//学年が存在しなかった場合
				//プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");
				//プリペアードステートメントに値をバインド
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			} else {
				//学生が存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
						.prepareStatement("update test set point=?, class_num=? where student_no=?, subject_cd=?, school_cd=?, no=?");
				//プリペアードステートメントに値をバインド
				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getClassNum());
			}

			//プリペアードステートメントを実行
			count = statement.executeUpdate();

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


		if (count > 0) {
			//実行件数が１件以上ある場合
			return true;
		} else {
			//実行件数が0件の場合
			return false;
		}

	}

	public boolean delete(Test test, Connection connection) throws SQLException{
		PreparedStatement statement = null;
		int count = 0;
		try{
			statement = connection
					.prepareStatement("delete from test where student_no=?, subject_cd=?, school_cd=?, no=?");
			statement.setString(1, test.getStudent().getNo());
			statement.setString(2, test.getSubject().getCd());
			statement.setString(3, test.getSchool().getCd());
			statement.setInt(4, test.getNo());
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
		if (count > 0) {
			//実行件数が１件以上ある場合
			return true;
		} else {
			//実行件数が0件の場合
			return false;
		}

	}
}