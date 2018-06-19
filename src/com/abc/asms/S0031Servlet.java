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

import com.abc.asms.beans.Accounts;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0031.html")
public class S0031Servlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		if(!ServletUtils.checkAccounts(req, resp)) {
			return;
		}

		getServletContext().getRequestDispatcher("/WEB-INF/S0031.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		if(!ServletUtils.checkAccounts(req, resp)) {
			return;
		}

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();


		String name = req.getParameter("name");
		String mail = req.getParameter("mail");
		String password1 = req.getParameter("password1");
		int authority = 0;
		if(req.getParameter("authority1").equals("1") && req.getParameter("authority2").equals("1")) {
			// 全権限あり
			authority = 11;
		} else if(req.getParameter("authority1").equals("0") && req.getParameter("authority2").equals("1")) {
			// アカウントのみ
			authority = 10;
		} else if(req.getParameter("authority1").equals("1") && req.getParameter("authority2").equals("0")) {
			// 売上のみ
			authority = 1;
		} else {
			// 権限なし
			authority = 0;
		}

		if(req.getParameter("NG") != null) {

			Accounts entry = new Accounts(0,
					req.getParameter("name"),
					req.getParameter("mail"),
					req.getParameter("password1"),
					authority);

			session.setAttribute("entry", entry);
			resp.sendRedirect("S0030.html");
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try {
			con = DBUtils.getConnection();

			sql = "INSERT INTO accounts (name, mail, password, authority) " +
					"VALUES (?, ?, MD5(?), ?)";
			ps = con.prepareStatement(sql);

			ps.setString(1, name);
			ps.setString(2, mail);
			ps.setString(3, password1);
			ps.setInt(4, authority);

			ps.executeUpdate();
			List<String> successes = new ArrayList<>();
			successes.add("No" + ServletUtils.registerAId() + "のアカウントを登録しました。");
			session.setAttribute("successes", successes);

			resp.sendRedirect("S0030.html");

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
			} catch (Exception e) {
			}
		}
	}
}
