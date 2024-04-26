package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

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
		StudentDao sDao = new StudentDao();//学生Dao
		int entYear;//入学年度
		String no = "";//学生番号
		String name = "";//氏名
		String classNum = "";//クラス番号
		String isAttendStr = "";
		Boolean isAttend=false;//入力された在学フラグ
		Student student = null;//学生
		//Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		entYear = Integer.parseInt(req.getParameter("ent_year"));//入学年度
		no = req.getParameter("no");//学生番号
		name = req.getParameter("name");//氏名
		classNum = req.getParameter("class_num");//クラス番号
		isAttendStr = req.getParameter("si_attend");
		if(isAttendStr!=null){
			isAttend = true;
		}

		//DBからデータ取得 3

		student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(school);
		//student.setSchool(((Teacher)session.getAttribute("user")).getSchool());
		// 学生を保存
		sDao.save(student);

		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}
}
