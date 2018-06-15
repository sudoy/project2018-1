package com.abc.asms;

import java.io.IOException;
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


@WebServlet("/C0045.html")
public class C0045Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/C0045.jsp")
			.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sessionE = req.getSession();

		req.setCharacterEncoding("utf-8");

		List<String> errors = validate(req);
		List<String> successes = new ArrayList<>();

		if(errors.size() != 0) {
			sessionE.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/C0045.jsp")
				.forward(req, resp);
			return;
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
					+ "Http://localhost:8080/project1/C0046.html?user="
					+ req.getParameter("mail"), "ISO-2022-JP");

			Transport.send(mimeMessage);

			System.out.println("メールを送信しました。");
		} catch (Exception e) {
			errors.add("予期しないエラーが発生しました。");
			sessionE.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/WEB-INF/C0045.jsp")
			.forward(req, resp);
			return;
		}
		successes.add("パスワード再設定メールを送信しました。");
		sessionE.setAttribute("successes", successes);
		resp.sendRedirect("C0045.html");
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

		if(!req.getParameter("mail").contains("@")) {
			errors.add("メールアドレスを正しく入力してください。");
			return errors;
		}


		String[] mailCheck = req.getParameter("mail").split("@", 0);
		String mailInitial = req.getParameter("mail").substring(0, 1);

		if(!mailInitial.matches("^[a-zA-Z0-9]*$")) {
			errors.add("メールアドレスを正しく入力してください。");
		}else if(!mailCheck[0].matches("^[a-zA-Z-0-9\\._\\-]*$")) {
			errors.add("メールアドレスを正しく入力してください。");
		}else if(!mailCheck[1].matches("^[a-zA-Z0-9\\._\\-]*$")
				|| mailCheck[1].length() == 0
				|| !mailCheck[1].contains(".")) {
			errors.add("メールアドレスを正しく入力してください。");
		}


		return errors;
	}
}
