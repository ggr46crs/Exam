package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {
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

		Subject subject = new Subject();
		//HttpSession session = req.getSession();//セッション
		//Teacher teacher = (Teacher)session.getAttribute("user");

		String f1 = "";//入学年度
		String f2 = "";//クラス番号
		String f3 = "";//科目コード
		String f4 = "";
		String entYearStr="";//入力された入学年度
		String classNum = "";//入力されたクラス番号
		int entYear = 0;// 入学年度
		String student_no="";
		String subject_cd = "";
		String school_cd = "";
		String nostr = "";
		int no = 0;// 入学年度
		List<Test> tests = null;// 学生リスト
		List<Subject> subjects = null;// 学生リスト
		LocalDate todaysDate = LocalDate.now();// LocalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		SubjectDao subDao = new SubjectDao();
		TestDao tDao = new TestDao();//学生Dao
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		f1 = req.getParameter("f1");//入学年度
		f2 = req.getParameter("f2");//クラス番号
		subject.setCd(req.getParameter("f3")); ;//科目コード
		f4 = req.getParameter("f4");//回数
		req.setAttribute("tests", tests);

		tests = tDao.filter(f1,f2,subject,f4,teacher.getSchool());

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> cNumlist = cNumDao.filter(teacher.getSchool());
		subjects = subDao.filter(teacher.getSchool());


		//リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		//10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}
		List<Integer> noSet = new ArrayList<>();
		//10年前から1年後まで年をリストに追加
		for (int i = 1; i < 10 ; i++) {
			noSet.add(i);
		}


		// リクエストに学年リストをセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", cNumlist);
		req.setAttribute("subject_cd_set", subjects);
		req.setAttribute("no_set", noSet);
		req.setAttribute("tests", tests);
		System.out.println(" test -> " + tests);
		//req.setAttribute("subjects", subjects);
		//レスポンス値をセット 5
		// リクエストに入学年度をセット
		//JSPへフォワード 7
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);

	}
}