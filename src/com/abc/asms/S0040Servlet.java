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


@WebServlet("/S0040.html")
public class S0040Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		if(session.getAttribute("saf") != null) {
			SearchAccountForm saf = (SearchAccountForm) session.getAttribute("saf");
			req.setAttribute("saf", saf);
			session.setAttribute("saf", null);
		}

		getServletContext().getRequestDispatcher("/WEB-INF/S0040.jsp")
			.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		List<String> errors = validate(req);

		SearchAccountForm saf = new SearchAccountForm(req.getParameter("name"), req.getParameter("mail"),
				req.getParameter("saleAuthority"), req.getParameter("accountAuthority"));

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
		if(req.getParameter("mail").length() > 100) {
			errors.add("メールアドレスが長すぎます。");
		}

		if(!req.getParameter("mail").equals("")) {

			if(!req.getParameter("mail").contains("@")) {
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

}
