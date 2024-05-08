package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		School school = new School();
		school.setCd("oom");
		school.setName("テスト校");

		Teacher teacher = new Teacher();
		teacher.setId("admin1");
		teacher.setName("管理者１");
		teacher.setPassword("password");
		teacher.setSchool(school);

		//HttpSession session = req.getSession();//セッション
		SubjectDao sDao = new SubjectDao();//学生Dao
		String cd = "";//科目コード
		String name = "";//科目名
		Subject subject = null;//科目
		List<Subject> subjects = null;// 学生リスト
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		//Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		cd = req.getParameter("cd");//学生番号

		//DBからデータ取得 3
		subject = sDao.get(cd);// 学生番号から学生インスタンスを取得
		subjects = sDao.filter(teacher.getSchool());

		name = subject.getName();

		req.setAttribute("cd_set", cd);
		req.setAttribute("name_set", name);

		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);

	}
}