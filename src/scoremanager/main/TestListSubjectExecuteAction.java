package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

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

		int f1;//入学年度
		String f2 = "";//クラス番号
		String f3 = "";//科目コード
		List<TestListSubject> tests = null;//学生
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao subDao = new SubjectDao();
		TestListSubjectDao tesDao = new TestListSubjectDao();
		//Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化

		//リクエストパラメータ―の取得 2
		f1 = Integer.parseInt(req.getParameter("f1"));//入学年度
		f2 = req.getParameter("f2");//クラス番号
		f3 = req.getParameter("f3");//科目コード

		//DBからデータ取得 3

		tests = tesDao.filter(f1,f2,f3,teacher.getSchool());
		List<String> list1 = cNumDao.filter(teacher.getSchool());
		List<String> value = subDao.filter_name(teacher.getSchool());
		List<String> key = subDao.filter_cd(teacher.getSchool());
		Map<String,String> map = IntStream.range(0, key.size())
				.boxed()
				.collect(Collectors.toMap(key::get, value::get));



		//ビジネスロジック 4
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		req.setAttribute("class_num_set", list1);//クラス番号のlistをセット
		req.setAttribute("subject_set", map);
		req.setAttribute("ent_year_set", entYearSet);//入学年度のlistをセット
		req.setAttribute("tests", tests);
		req.setAttribute("subject_name", map.get(f3));

		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
	}
}
