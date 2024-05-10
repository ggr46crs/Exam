package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Test;

public class TestDao extends Dao {


	private String baseSql = "select * from Test where school_cd=? ";

	public Test get(String student_no,String subject_cd,String school_cd,int no) throws Exception {
		//学生インスタンスを初期化
		Test test = new Test();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from student where student_no=? and subject_cd=? and school_cd=? and no=?");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, student_no);
			statement.setString(2, subject_cd);
			statement.setString(3, school_cd);
			statement.setInt(4, no);
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

	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		//リストを初期化
		List<Test> list = new ArrayList<>();
		try {
			//リザルトセットを全権操作
			while (rSet.next()) {
				//学生インスタンスを初期化
				Test test = new Test();
				//学生インスタンスに検索結果をセット
				test.setStudent_no(rSet.getString("student_no"));
				test.setSubject_cd(rSet.getString("subject_cd"));
				test.setSchool_cd(rSet.getString("school_cd"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setSchool(school);
				//リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Test> filter(School school, String student_no,String subject_cd,int no) throws Exception {
		//リストを初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "and student_no=? and subject_cd=? and no=?";
		//SQL文のソート
		String order = " order by no asc";

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, student_no);
			statement.setString(2, subject_cd);
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
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
	public List<Test> filter(School school, String student_no,String subject_cd) throws Exception {
		//リストを初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "and student_no=? and subject_cd=?";
		//SQL文のソート
		String order = " order by no asc";

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, student_no);
			statement.setString(2, subject_cd);
			statement.setString(3, school.getCd());
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
	public Test noget(String student_no) throws Exception {
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
/**
	public boolean save(Test test) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数
		int count = 0;

		try {
			//データベースから学年を取得
			Test old = get(test.getStudent_no(),test.getSubject_cd(),test.getSchool_cd(),test.getNo());
			if (old == null) {
				//学年が存在しなかった場合
				//プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)");
				//プリペアードステートメントに値をバインド
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());
			} else {
				//学生が存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
						.prepareStatement("update student set name =?, ent_year=?, class_num=?, is_attend=? where no=?");
				//プリペアードステートメントに値をバインド
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getNo());
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

	public void delete(int no) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		try {
			//データベースから学年を取得

			statement = connection
					.prepareStatement("delete from student where no=?");
			//プリペアードステートメントに値をバインド
			statement.setInt(1, no);
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
	}
**/
}
