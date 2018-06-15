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
			sql = "SELECT account_id, name, mail, password, authority " +
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
					rs.getString("mail"),
					rs.getString("password"),
					rs.getInt("authority")
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

		Accounts a = new Accounts(
				Integer.parseInt(req.getParameter("accountId")),
				req.getParameter("name"),
				req.getParameter("mail"),
				req.getParameter("password1"),
				authority
				);

		session.setAttribute("editAccount", a);

		resp.sendRedirect("S0043.html");
	}

	private List<String> validate(HttpServletRequest req){

		List<String> list = new ArrayList<>();

		// 氏名の必須入力
		if(req.getParameter("name").equals("")) {
			list.add("氏名を入力して下さい。");
		}
		//氏名の長さチェック
		if(req.getParameter("name").length() > 21) {
			list.add("氏名が長すぎます。");
		}

		//メールアドレス必須チェック
		if(req.getParameter("mail").equals("")) {
			list.add("メールアドレスを入力して下さい。");
		}else{
			//メールアドレス長さチェック
			if(req.getParameter("mail").length() > 101) {
				list.add("メールアドレスが長すぎます。");
			}

			//メールアドレス形式チェック
			String[] mailCheck = req.getParameter("mail").split("@", 0);
			String mailInitial = req.getParameter("mail").substring(0, 1);

			if(!mailInitial.matches("^[a-zA-Z0-9]*$")) {
				list.add("メールアドレスの形式が誤っています。");
			}else if(!mailCheck[0].matches("^[a-zA-Z-0-9\\._\\-]*$")) {
				list.add("メールアドレスの形式が誤っています。");
			}else if(!mailCheck[1].matches("^[a-zA-Z0-9\\._\\-]*$")
					|| mailCheck[1].length() == 0
					|| !mailCheck[1].contains(".")) {
				list.add("メールアドレスの形式が誤っています。");
			}
		}

		//パスワード長さチェック
		if(req.getParameter("password1").length() > 31) {
			list.add("パスワードが長すぎます。");
		}

		//パスワード一致チェック
		if(!req.getParameter("password1").equals("") || !req.getParameter("password2").equals("")) {
			if(!req.getParameter("password1").equals(req.getParameter("password2"))) {
				list.add("パスワードとパスワード(確認)が一致していません。");
			}
		}
		//売上登録権限必須チェック
		if(req.getParameter("authority1").equals("")) {
			list.add("売上登録権限を入力して下さい。");
		}
		//売上登録権限値チェック
		if(!req.getParameter("authority1").equals("0") && !req.getParameter("authority1").equals("1")) {
			list.add("売上登録権限に正しい値を入力して下さい。");
		}

		//アカウント登録権限必須チェック
		if(req.getParameter("authority2").equals("")) {
			list.add("アカウント登録権限を入力して下さい。");
		}
		//アカウント登録権限値チェック
		if(!req.getParameter("authority2").equals("0") && !req.getParameter("authority2").equals("10")) {
			list.add("アカウント登録権限に正しい値を入力して下さい。");
		}


		return list;

	}

}
