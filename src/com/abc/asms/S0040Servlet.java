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
		SearchAccountForm saf = new SearchAccountForm(escapeHTML(req.getParameter("name")), escapeHTML(req.getParameter("mail")),
				escapeHTML(req.getParameter("saleAuthority")), escapeHTML(req.getParameter("accountAuthority")));

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

		if(escapeHTML(req.getParameter("name")).length() > 20) {
			errors.add("氏名の指定が長すぎます。");
		}
		if(escapeHTML(req.getParameter("mail")).length() > 100) {
			errors.add("メールアドレスの指定が長すぎます。");
		}

		if(!escapeHTML(req.getParameter("mail")).equals("")) {

			if(!escapeHTML(req.getParameter("mail")).contains("@")) {
				errors.add("メールアドレスの形式が誤っています。");
				return errors;
			}

			String[] mailCheck = req.getParameter("mail").split("@", 0);
			String mailInitial = req.getParameter("mail").substring(0, 1);

			if(!mailInitial.matches("^[a-zA-Z0-9]*$")) {
				errors.add("メールアドレスの形式が誤っています。");
			}else if(!mailCheck[0].matches("^[a-zA-Z-0-9\\._\\-]*$")) {
				errors.add("メールアドレスの形式が誤っています。");
			}else if(!mailCheck[1].matches("^[a-zA-Z0-9\\._\\-]*$")
					|| mailCheck[1].length() == 0
					|| !mailCheck[1].contains(".")) {
				errors.add("メールアドレスの形式が誤っています。");
			}
		}


		return errors;
	}

	public static String escapeHTML(String val) {
		if (val == null) {
			return "";
		}
		val = val.replaceAll("&", "&amp;");
		val = val.replaceAll("<", "&lt;");
		val = val.replaceAll(">", "&gt;");
		val = val.replaceAll("\"", "&quot;");
		val = val.replaceAll("'", "&apos;");
		return val;
	}


}
