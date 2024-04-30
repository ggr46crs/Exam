package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		School school = new School();
		school.setCd("oom");
		school.setName("テスト校");

		Teacher teacher = new Teacher();
		teacher.setId("admin1");
		teacher.setName("管理者１");
		teacher.setPassword("password");
		teacher.setSchool(school);

		//HttpSession session = req.getSession();//セッション
		//Teacher teacher = (Teacher)session.getAttribute("user");

		List<Subject> subjects = null;// 学生リスト
		SubjectDao sDao = new SubjectDao();//学生Dao

		//リクエストパラメーターの取得 2
		//DBからデータ取得 3

		subjects = sDao.filter(teacher.getSchool());


		//リストを初期化
		//レスポンス値をセット 5
		// リクエストに入学年度をセット

		req.setAttribute("subjects", subjects);
		// リクエストにデータをセット
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);

	}


}