package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {

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
		boolean isAttend = false;// 在学フラグ
		//String classNum = "";//クラス番号
		Student student = null;//学生
		//Map<String, String> errors = new HashMap<>();// エラーメッセージ
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		//Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2

		no = req.getParameter("no");//学生番号
		//DBからデータ取得 3
		student = sDao.get(no);// 学生番号から学生インスタンスを取得
		List<String> list = cNumDao.filter(teacher.getSchool());// ログインユーザーの学校コードをもとにクラス番号の一覧を取得


		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で手順4~5の内容が分岐



		entYear = student.getEntYear();
		name = student.getName();
		isAttend = student.isAttend();

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7

		req.setAttribute("ent_year_set", entYear);//入学年度のlistをセット
		req.setAttribute("no_set", no);
		req.setAttribute("name_set", name);
		req.setAttribute("class_num_set", list);//クラス番号のlistをセット
		req.setAttribute("isAttend_set", isAttend);

		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}