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

public class SubjectCreateExecuteAction extends Action {

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
		name = req.getParameter("name");//氏名

		//DBからデータ取得 3
		subject = sDao.get(cd);// 学生番号から学生インスタンスを取得
		subjects = sDao.filter(teacher.getSchool());

		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で手順4~5の内容が分岐
		if (subject == null) {// 学生が未登録だった場合
			// 学生インスタンスを初期化
			subject = new Subject();
			// インスタンスに値をセット
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(school);
			//student.setSchool(((Teacher)session.getAttribute("user")).getSchool());
			// 学生を保存
			sDao.save(subject);
		} else {//入力された学番がDBに保存されていた場合
			errors.put("cd", "科目コードが重複しています");
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7

		if(!errors.isEmpty()){
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
			return;
		}
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}
}
