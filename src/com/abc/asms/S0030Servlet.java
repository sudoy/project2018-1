package com.abc.asms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.Accounts;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0030.html")
public class S0030Servlet extends HttpServlet {
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

		if(session.getAttribute("entry") != null && session.getAttribute("accountRemain") != null) {
			Accounts entry = (Accounts) session.getAttribute("entry");
			req.setAttribute("entry", entry);
			session.setAttribute("accountRemain", null);
		}
		session.setAttribute("entry", null);

		getServletContext().getRequestDispatcher("/WEB-INF/S0030.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}
		if(!ServletUtils.checkAccounts(req, resp)) {
			return;
		}

		HttpSession session = req.getSession();
		req.setCharacterEncoding("utf-8");

		//バリデーションチェック
		List<String> errors = validate(req);
		if (errors.size() != 0) {
			session.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/WEB-INF/S0030.jsp").forward(req, resp);
			return;
		}

		int authority = Integer.parseInt(req.getParameter("authority1")) +
				Integer.parseInt(req.getParameter("authority2"));

		Accounts entry = new Accounts(0,
				req.getParameter("name"),
				req.getParameter("kana"),
				req.getParameter("mail"),
				req.getParameter("password1"),
				authority);

		session.setAttribute("entry", entry);
		resp.sendRedirect("S0031.html");
	}


	private static List<String> validate(HttpServletRequest req) {

		List<String> errors = new ArrayList<>();

		// 氏名の必須入力
		if (req.getParameter("name").equals("") || req.getParameter("name") == null) {
			errors.add("氏名を入力して下さい。");
		} else if(req.getParameter("name").length() > 20) {
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

		// パスワードの必須入力
		if(req.getParameter("password1").equals("") || req.getParameter("password1") == null) {
			errors.add("パスワードを入力して下さい。");
		} else if(req.getParameter("password1").length() > 30) {
			errors.add("パスワードが長すぎます。");
		}
		// パスワード（確認）の必須入力
		if(req.getParameter("password2").equals("") || req.getParameter("password2") == null) {
			errors.add("パスワード（確認）を入力して下さい。");
		} else if(!req.getParameter("password1").equals(req.getParameter("password2"))) {
			errors.add("パスワードとパスワード（確認）が一致していません。");
		}

		// 売上登録権限の必須入力
		if(req.getParameter("authority1") == null) {
			errors.add("売上登録権限を入力して下さい。");
		} else if(!req.getParameter("authority1").equals("0") && !req.getParameter("authority1").equals("1")) {
			errors.add("売上登録権限に正しい値を入力して下さい。");
		}

		// アカウント登録権限の必須入力
		if(req.getParameter("authority2") == null) {
			errors.add("アカウント登録権限を入力して下さい。");
		} else if(!req.getParameter("authority2").equals("0") && !req.getParameter("authority2").equals("10")) {
			errors.add("アカウント登録権限に正しい値を入力して下さい。");
		}

		//アドレスチェック
			String mail = req.getParameter("mail");
			if(mail.equals("") || mail == null) {
				errors.add("メールアドレスを入力して下さい。");

			}else if(!mail.equals("")){

				if(mail.length() > 100) {
					errors.add("メールアドレスが長すぎます。");
				}

				//メールアドレスの形式チェック

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
			}


		// メール重複チェック
		if(ServletUtils.overlapMail(req.getParameter("mail"))) {
			errors.add("メールアドレスが既に登録されています。");
		}

		return errors;
	}
}
