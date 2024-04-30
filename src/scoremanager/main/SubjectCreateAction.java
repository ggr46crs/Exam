package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class SubjectCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		School school = new School();
		school.setCd("oom");//学校コードをセットする
		school.setName("テスト校");//学校名をセットする

		Teacher teacher = new Teacher();
		teacher.setId("admin1");//講師IDをセット
		teacher.setName("管理者１");//講師名をセット
		teacher.setPassword("password");//ログインパスワードをセット
		teacher.setSchool(school);//学校オブジェクトをセット



		//ローカル変数の宣言 1
		HttpSession session = req.getSession(true);// セッションを取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		//Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		//DBからデータ取得 3
		List<String> list = cNumDao.filter(teacher.getSchool());// ログインユーザーの学校コードをもとにクラス番号の一覧を取得

		//ビジネスロジック 4
		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("class_num_set", list);//クラス番号のlistをセット

		//JSPへフォワード 7
		req.getRequestDispatcher("subject_create.jsp").forward(req, res);
	}
}
