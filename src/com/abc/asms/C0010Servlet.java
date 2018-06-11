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

@WebServlet("/C0010.html")
public class C0010Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/C0010.jsp")
			.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		req.setCharacterEncoding("utf-8");
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try{


			con = DBUtils.getConnection();

			sql = "select account_id, name, mail, password, authority from accounts where mail=? and password=?";

			ps = con.prepareStatement(sql);

			ps.setString(1, req.getParameter("mail"));
			ps.setString(2, req.getParameter("password"));

			rs = ps.executeQuery();

			if(!rs.next()) {

				session.setAttribute("errors", "メールアドレス、またはパスワードが間違っています。");
				getServletContext().getRequestDispatcher("/WEB-INF/C0010.jsp")
				.forward(req, resp);
				return;
			}
			Accounts account = new Accounts( rs.getInt("account_id"), rs.getString("name"),
					rs.getString("email"), rs.getString("password"), rs.getInt("authority"));

			session.setAttribute("account", account);
			resp.sendRedirect("C0020.html");
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}
		}

	}

	private static List<String> validate(HttpServletRequest req) {

		List<String> errors = new ArrayList<>();

		if(!req.getParameter("mail").equals("")) {
			errors.add("メールアドレスを入力してください。");
		}

		if(req.getParameter("mail").length() > 100) {
			errors.add("メールアドレスが長すぎます。");
		}

		String[] mailCheck = req.getParameter("mail").split("@", 0);
		String mailInitial = req.getParameter("mail").substring(0, 1);

		return null;
	}
}
