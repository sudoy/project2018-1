package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.utils.DBUtils;


@WebServlet("/S0045.html")
public class S0045Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/S0045.jsp")
			.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sessionE = req.getSession();

		req.setCharacterEncoding("utf-8");
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;


		List<String> errors = validate(req);

		//successメッセージ格納用のリスト
		List<String> successes = new ArrayList<>();

		if(errors.size() != 0) {
			sessionE.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/S0045.jsp")
				.forward(req, resp);
			return;
		}

		//メールアドレスがデータベース内にあるかを確認
		try{

			con = DBUtils.getConnection();

			sql = "select mail from accounts where mail=?";

			ps = con.prepareStatement(sql);

			ps.setString(1, req.getParameter("mail"));

			rs = ps.executeQuery();

			if(!rs.next()) {
				errors.add("メールアドレスを正しく入力してください。");
				sessionE.setAttribute("errors", errors);
				getServletContext().getRequestDispatcher("/WEB-INF/S0045.jsp")
					.forward(req, resp);
				return;
			}
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}
		}

		try {
			// GmailのSMTPを使用する
			Properties property = new Properties();
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.smtp.auth", "true");
			property.put("mail.smtp.starttls.enable", "true");
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.smtp.port", "587");
			property.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(property, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("sie.tsd2018@gmail.com", "!sie.tsd2018");
				}
			});

			// toアドレス
			InternetAddress toAddress = new InternetAddress(req.getParameter("mail"));
			// fromアドレス
			InternetAddress fromAddress = new InternetAddress("sie.tsd2018@gmail.com", "物品売上管理システム");

			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);
			mimeMessage.setFrom(fromAddress);
			mimeMessage.setSubject("【物品売上管理システム】パスワード再設定", "ISO-2022-JP");
			mimeMessage.setText("パスワードの再設定を行います。\n"
					+ "以下のURLより新パスワードの入力・変更を行ってください。\n"
					+ "http://localhost:8080/project1/S0046.html?user="
					+ req.getParameter("mail"), "ISO-2022-JP");

			Transport.send(mimeMessage);

		} catch (Exception e) {
			errors.add("予期しないエラーが発生しました。");
			sessionE.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/WEB-INF/S0045.jsp")
			.forward(req, resp);
			return;
		}
		successes.add("パスワード再設定メールを送信しました。");
		sessionE.setAttribute("successes", successes);
		resp.sendRedirect("S0045.html");
	}


	private static List<String> validate(HttpServletRequest req) {

		List<String> errors = new ArrayList<>();

		if(req.getParameter("mail").equals("")) {
			errors.add("メールアドレスを入力してください。");
			return errors;
		}

		if(req.getParameter("mail").length() > 100) {
			errors.add("メールアドレスが長すぎます。");
		}

		//メールアドレスの形式チェック
		String mail = req.getParameter("mail");
		if(!mail.matches("^[a-zA-Z0-9][a-zA-Z0-9\\._\\-]*@[a-zA-Z0-9\\._\\-]{1}[a-zA-Z0-9\\._\\-]*$")) {
			errors.add("メールアドレスを正しく入力して下さい。");
		}else if(!mail.substring(mail.indexOf("@")).contains(".")) {
			errors.add("メールアドレスを正しく入力して下さい。");
		}


		return errors;
	}
}
