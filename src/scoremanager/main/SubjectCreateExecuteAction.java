package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

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
		//HttpSession session = req.getSession();//セッション
		List<Subject> subjects = null;// 学生リスト
		SubjectDao sDao = new SubjectDao();//学生Dao
		String cd = "";//科目コード
		String name = "";//科目名
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		//ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		//Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		cd = req.getParameter("cd");//科目コード
		name = req.getParameter("name");//科目名

		//DBからデータ取得 3
		subjects = sDao.filter(teacher.getSchool());
		//subjects = sDao.get(cd);// 科目コードから学生インスタンスを取得

		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で手順4~5の内容が分岐else{
			if (subjects == null) {// 学生が未登録だった場合
				subjects = new Subject();
				// インスタンスに値をセット
				subjects.setCd(cd);
				subjects.setName(name);
				// 学生を保存
				sDao.save(subjects);
			} else {//入力された学番がDBに保存されていた場合
				errors.put("cd", "科目コードが重複しています");
			}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7

		if(!errors.isEmpty()){
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			return;
		}
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}
}
