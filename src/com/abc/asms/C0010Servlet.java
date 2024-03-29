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

@WebServlet("/C0010.html")
public class C0010Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//No.5修正
		HttpSession session = req.getSession();
		if(session.getAttribute("accounts") != null) {
			resp.sendRedirect("C0020.html");
			return;
		}

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

		List<String> errors = validate(req);

		if(errors.size() != 0) {
			session.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/C0010.jsp")
			.forward(req, resp);
			return;

		}

		try{

			con = DBUtils.getConnection();

			sql = "select account_id, name, mail, password, authority from accounts where mail=? and password=MD5(?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, req.getParameter("mail"));
			ps.setString(2, req.getParameter("password"));

			rs = ps.executeQuery();

			if(!rs.next()) {
				errors.add("メールアドレス、パスワードを正しく入力して下さい。");
				session.setAttribute("errors", errors);
				getServletContext().getRequestDispatcher("/WEB-INF/C0010.jsp")
				.forward(req, resp);
				return;
			}
			Accounts accounts = new Accounts( rs.getInt("account_id"), rs.getString("name"),
					rs.getString("mail"), rs.getString("password"), rs.getInt("authority"));


			session.setAttribute("accounts", accounts);
			resp.sendRedirect("C0020.html");
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			}catch(Exception e){}
		}

	}

	private static List<String> validate(HttpServletRequest req) {

		List<String> errors = new ArrayList<>();

		//アドレスチェック
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
					errors.add("@以降には.を入れて下さい。");
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
		}

		//アカウント存在チェック
		if(errors.size() == 0) {
			if(ServletUtils.checkMail(mail) == false) {
				errors.add("このメールアドレスは登録されていません。");
			}
		}

		//パスワードのバリデーション
		if(req.getParameter("password").equals("")) {
			errors.add("パスワードが未入力です。");
		}else {
			if(req.getParameter("password").length() > 30) {
				errors.add("パスワードが長すぎます。");
			}
			//パスワードチェック
			if(ServletUtils.checkPassword(req.getParameter("password")) == false) {
				errors.add("パスワードが間違っています。");
			}
		}



		return errors;
	}
}
