package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0043.html")
public class S0043Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		//アカウント権限チェック
		if(!ServletUtils.checkAccounts(req, resp)) {
			return;
		}

		//フォワード
		getServletContext().getRequestDispatcher("/WEB-INF/S0043.jsp").forward(req, resp);
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
			con = DBUtils.getConnection();

			int authority = Integer.parseInt(req.getParameter("authority1")) +
					Integer.parseInt(req.getParameter("authority2"));

			if(req.getParameter("password1").equals("")) {

				sql = "UPDATE accounts SET name = ?, mail = ?, "
						+ "authority = ? WHERE account_id = ?";

				//準備
				ps = con.prepareStatement(sql);

				//データをセット
				ps.setString(1, req.getParameter("name"));
				ps.setString(2, req.getParameter("mail"));
				ps.setLong(3, authority);
				ps.setString(4, req.getParameter("accountId"));

			}else {

				sql = "UPDATE accounts SET name = ?, mail = ?, password = MD5(?), "
						+ "authority = ? WHERE account_id = ?";

				//準備
				ps = con.prepareStatement(sql);

				//データをセット
				ps.setString(1, req.getParameter("name"));
				ps.setString(2, req.getParameter("mail"));
				ps.setString(3, req.getParameter("password1"));
				ps.setLong(4, authority);
				ps.setString(5, req.getParameter("accountId"));


			}

			//実行
			ps.executeUpdate();

			List<String> successes = new ArrayList<>();
			String success = "No" +  req.getParameter("accountId") + "のアカウントを更新しました。";

			successes.add(success);
			session.setAttribute("successes", successes);

			session.setAttribute("editAccount", null);

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
