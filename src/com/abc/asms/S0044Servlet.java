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

@WebServlet("/S0044.html")
public class S0044Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		//アカウント権限チェック
		if(!ServletUtils.checkAccounts(req, resp)) {
			return;
		}

		//直接アドレスを入力してきた際の対応
		if(req.getParameter("accountId") == null) {
			List<String> errors = new ArrayList<>();
			errors.add("不正なアクセスです。");
			session.setAttribute("errors", errors);
			resp.sendRedirect("S0040.html");
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
			sql = "SELECT account_id, name, kana, mail, password, authority " +
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
					rs.getInt("authority")
					);

			req.setAttribute("deleteAccount", a);

			//フォワード
			getServletContext().getRequestDispatcher("/WEB-INF/S0044.jsp").forward(req, resp);

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

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;


		try {
			//データベース接続
			con = DBUtils.getConnection();

			//GETパラメータを取得
			String id = req.getParameter("accountId");

			//SQL
			sql = "DELETE FROM accounts "
				+ "WHERE account_id = ?";

			//準備
			ps = con.prepareStatement(sql);

			//パラメータをセット
			ps.setString(1, id);

			//実行
			ps.executeUpdate();

			List<String> successes = new ArrayList<>();
			String success = "No" +  id + "のアカウントを削除しました。";

			successes.add(success);
			session.setAttribute("successes", successes);

			//検索条件削除
			session.setAttribute("saf", null);

			//アカウント検索結果に遷移
			resp.sendRedirect("S0041.html");


		}catch(Exception e){
			throw new ServletException(e);
		}finally{

			try{

				DBUtils.close(ps);
				DBUtils.close(con);
			}catch(Exception e){

			}
		}

	}
}
