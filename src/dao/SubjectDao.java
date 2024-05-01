package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

	private String baseSql = "select * from subject where school_cd=? ";

	public Subject get(String cd) throws Exception {
		//学生インスタンスを初期化
		Subject subject = new Subject();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where cd=?");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, cd);
			//プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			//学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				//リザルトセットが存在する場合
				//学生インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				//学校フィールドには学校コードで検索した学校インスタンスをセット
				subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				//リザルトセットが存在しない場合
				//学生インスタンスにnullをセット
				subject = null;
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

		return subject;
	}

	private List<Subject> postFilter(ResultSet rSet, School school) throws Exception {
		//リストを初期化
		List<Subject> list = new ArrayList<>();
		try {
			//リザルトセットを全権操作
			while (rSet.next()) {
				//学生インスタンスを初期化
				Subject subject = new Subject();
				//学生インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				//リストに追加
				list.add(subject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}



	public List<Subject> filter(School school) throws Exception {
		//リストを初期化
		List<Subject> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文のソート
		String order = " order by cd asc";

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
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


	public boolean save(Subject subject) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数
		int count = 0;

		try {
			//データベースから学年を取得
			Subject old = get(subject.getCd());
			if (old == null) {
				//学年が存在しなかった場合
				//プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into subject(cd, name,school_cd) values(?, ?, ?)");
				//プリペアードステートメントに値をバインド
				statement.setString(1, subject.getCd());
				statement.setString(2, subject.getName());
				statement.setString(3, subject.getSchool().getCd());
			} else {
				//学生が存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
						.prepareStatement("update subject set name =? where cd=?");
				//プリペアードステートメントに値をバインド
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getCd());
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
	public boolean delete(Subject subject) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		int count = 0;
		try {
			statement = connection.prepareStatement(
					"delete from subject where(school_cd = ? and cd = ?)");
			//プリペアードステートメントに値をバインド
			statement.setString(1, subject.getSchool().getCd());
			statement.setString(2, subject.getCd());

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
}
