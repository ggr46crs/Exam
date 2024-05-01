package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
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
		SubjectDao subDao = new SubjectDao();
		String cd = "";//科目コード
		String name = "";//科目名
		Subject subject = null;

		cd = req.getParameter("cd");//科目コード
		subject = subDao.get(cd);
		name = subject.getName();
		req.setAttribute("cd_set", cd);
		req.setAttribute("name_set", name);
		System.out.println("★ ★ ★ ★ ★ ★ ★ ★ ");

		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);

	}
}