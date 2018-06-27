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

import com.abc.asms.beans.SearchAccountForm;
import com.abc.asms.utils.ServletUtils;


@WebServlet("/S0040.html")
public class S0040Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		//エラーの際の入力保持
		if(session.getAttribute("saf") != null && session.getAttribute("accountRemain") != null) {
			SearchAccountForm saf = (SearchAccountForm) session.getAttribute("saf");
			req.setAttribute("saf", saf);
			session.setAttribute("accountRemain", null);
		}
		session.setAttribute("saf", null);


		getServletContext().getRequestDispatcher("/WEB-INF/S0040.jsp")
			.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		//入力結果を検索結果のページに送る。
		SearchAccountForm saf = new SearchAccountForm(req.getParameter("name"), req.getParameter("kana"),
				req.getParameter("mail"),req.getParameter("saleAuthority"), req.getParameter("accountAuthority"));

		//入力内容のチェック
		List<String> errors = validate(req);

		if(errors.size() != 0) {
			session.setAttribute("errors", errors);
			req.setAttribute("saf", saf);

			getServletContext().getRequestDispatcher("/WEB-INF/S0040.jsp")
			.forward(req, resp);
			return;

		}


		session.setAttribute("saf", saf);
		resp.sendRedirect("S0041.html");

	}

	private static List<String> validate(HttpServletRequest req) {

		List<String> errors = new ArrayList<>();

		if(req.getParameter("name").length() > 20) {
			errors.add("氏名の指定が長すぎます。");
		}
		if(req.getParameter("kana").length() > 50) {
			errors.add("ふりがなが長すぎます。");
		}
		if(!req.getParameter("kana").matches("^[ぁ-ん]*$")) {
			errors.add("ふりがなには平仮名を入力してください。");
		}

		if(req.getParameter("mail").length() > 100) {
			errors.add("メールアドレスが長すぎます。");
		}

		if(!req.getParameter("mail").equals("")) {

			//メールアドレスの形式チェック
			String mail = req.getParameter("mail");
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

		return errors;
	}



}
