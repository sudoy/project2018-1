package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.Accounts;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0042.html")
public class S0042Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		req.setCharacterEncoding("utf-8");

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		//アカウント権限チェック
		if(!ServletUtils.checkAccounts(req, resp)) {
			return;
		}

		//No97 アカウントIDなしでURL入力した場合
		if(req.getParameter("accountId") == null) {
			List<String> errors = new ArrayList<>();
			errors.add("不正なアクセスです。");
			session.setAttribute("errors", errors);
			resp.sendRedirect("C0020.html");
			return;
		}


		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			//データベース接続
			con = DBUtils.getConnection();

			//GETパラメータを取得
			String id = req.getParameter("accountId");

			//SQL
			sql = "SELECT account_id, name, kana, mail, password, authority, version " +
					"FROM accounts " +
					"WHERE account_id = ?";

			//準備
			ps = con.prepareStatement(sql);

			//パラメータをセット
			ps.setString(1, id);

			//実行
			rs = ps.executeQuery();

			rs.next();

			Accounts a = new Accounts(
					rs.getInt("account_id"),
					rs.getString("name"),
					rs.getString("kana"),
					rs.getString("mail"),
					rs.getString("password"),
					rs.getInt("authority"),
					rs.getInt("version")
					);

			session.setAttribute("editAccount", a);

			//フォワード
			getServletContext().getRequestDispatcher("/WEB-INF/S0042.jsp").forward(req, resp);

		}catch(Exception e){
			throw new ServletException(e);
		}finally{

			try{
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			}catch(Exception e){

			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		req.setCharacterEncoding("utf-8");

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		//アカウント権限チェック
		if(!ServletUtils.checkAccounts(req, resp)) {
			return;
		}

		//バリデーション
		List<String> errors = validate(req);
		if(errors.size() != 0) {
			session.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/S0042.jsp").forward(req, resp);

			return;
		}

		int authority = Integer.parseInt(req.getParameter("authority1")) +
				Integer.parseInt(req.getParameter("authority2"));

		Accounts versionId = (Accounts)session.getAttribute("editAccount");
		Accounts a = new Accounts(
				Integer.parseInt(req.getParameter("accountId")),
				req.getParameter("name"),
				req.getParameter("kana"),
				req.getParameter("mail"),
				req.getParameter("password1"),
				authority,
				versionId.getVersion()
				);

		session.setAttribute("editAccount", a);

		resp.sendRedirect("S0043.html");
	}

	private List<String> validate(HttpServletRequest req){
		HttpSession session = req.getSession();

		List<String> errors = new ArrayList<>();

		// 氏名の必須入力
		if(req.getParameter("name").equals("")) {
			errors.add("氏名を入力して下さい。");
		}
		//氏名の長さチェック
		if(req.getParameter("name").length() > 20) {
			errors.add("氏名が長すぎます。");
		}

		//ふりがなのチェック
		if(req.getParameter("kana").equals("")) {
			errors.add("ふりがなを入力してください");
		}else {
			if(req.getParameter("kana").length() > 50) {
				errors.add("ふりがなが長すぎます。");
			}
			if(!req.getParameter("kana").matches("^[ぁ-ん]*$")) {
				errors.add("ふりがなには平仮名を入力してください。");
			}
		}


		//メールアドレスチェック
		String mail = req.getParameter("mail");
		//必須チェック
		if(mail.equals("") || mail == null) {
			errors.add("メールアドレスを入力して下さい。");

		}else if(!mail.equals("")){
			//長さチェック
			if(mail.length() > 100) {
				errors.add("メールアドレスが長すぎます。");
			}

			//メールアドレスの形式チェック
			//先頭文字チェック
			if(!mail.matches("[a-zA-Z0-9].*")) {
				errors.add("メールアドレスの先頭はアルファベットか数字を入力して下さい。");
			}

			if(mail.contains("@")) {
				if(!mail.matches("^[a-zA-Z0-9\\._\\-]*@[a-zA-Z0-9\\._\\-]{1}[a-zA-Z0-9\\._\\-]*$")) {
					errors.add("メールアドレスに使用可能な記号は「._-」です。");
				}
				if(!mail.substring(mail.indexOf("@")).contains(".")) {
					errors.add("メールアドレスの@以降には.を入れて下さい。");
				}
			}else{
				errors.add("メールアドレスには@を入力して下さい。");
				errors.add("@以降には.を入れて下さい。");
				if(!mail.matches("^[a-zA-Z0-9\\._\\-]*$")) {
					errors.add("メールアドレスに使用可能な記号は「._-」です。");
				}
			}

			if(mail.charAt(mail.length() -1) == '.') {
				errors.add("メールアドレスは.で終わらないでください。");
			}

			//メールアドレスの重複チェック
			Accounts editAccount = (Accounts) session.getAttribute("editAccount");
			if(!editAccount.getMail().equals(mail)){
				if(ServletUtils.overlapMail(mail)) {
					errors.add("メールアドレスが既に登録されています。");
				}
			}
		}

		//パスワード長さチェック
		if(req.getParameter("password1").length() > 30) {
			errors.add("パスワードが長すぎます。");
		}

		//パスワード一致チェック
		if(!req.getParameter("password1").equals("") || !req.getParameter("password2").equals("")) {
			if(!req.getParameter("password1").equals(req.getParameter("password2"))) {
				errors.add("パスワードとパスワード(確認)が一致していません。");
			}
		}
		//売上登録権限必須チェック
		if(req.getParameter("authority1") == null || req.getParameter("authority1").equals("")) {
			errors.add("売上登録権限を入力して下さい。");
		}
		//売上登録権限値チェック
		else if(!req.getParameter("authority1").equals("0") && !req.getParameter("authority1").equals("1")) {
			errors.add("売上登録権限に正しい値を入力して下さい。");
		}

		//アカウント登録権限必須チェック
		if(req.getParameter("authority2") == null || req.getParameter("authority2").equals("")) {
			errors.add("アカウント登録権限を入力して下さい。");
		}
		//アカウント登録権限値チェック
		else if(!req.getParameter("authority2").equals("0") && !req.getParameter("authority2").equals("10")) {
			errors.add("アカウント登録権限に正しい値を入力して下さい。");
		}

		return errors;

	}

}
