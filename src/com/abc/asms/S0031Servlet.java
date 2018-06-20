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

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		session.setAttribute("accountRemain", "on");

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
		int authority = Integer.parseInt(req.getParameter("authority1")) +
				Integer.parseInt(req.getParameter("authority2"));

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
			successes.add("No" + ServletUtils.registerAccountId() + "のアカウントを登録しました。");
			session.setAttribute("successes", successes);
			session.setAttribute("entry", null);
			resp.sendRedirect("S0030.html");

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (Exception e) {
			}
		}
	}
}
