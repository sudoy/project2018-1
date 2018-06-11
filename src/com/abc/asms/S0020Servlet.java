package com.abc.asms;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abs.asms.utils.ServletUtils;

@WebServlet("/S0020.html")
public class S0020Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		List<String> categoryList = ServletUtils.categoryList(req);
		req.setAttribute("categoryList", categoryList);

		List<String> accountList = ServletUtils.accountList(req);
		req.setAttribute("accountList", accountList);
		getServletContext().getRequestDispatcher("/WEB-INF/S0020.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		List<String> categoryList = ServletUtils.categoryList(req);
		req.setAttribute("categoryList", categoryList);

		List<String> accountList = ServletUtils.accountList(req);
		req.setAttribute("accountList", accountList);

		HttpSession session = req.getSession();
		List<String> errors = validate(req);

		if(errors.size() != 0) {
			session.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/S0020.jsp")
			.forward(req, resp);
			return;

		}


		getServletContext().getRequestDispatcher("/S0021.html").forward(req, resp);
	}

	private List<String> validate(HttpServletRequest req){

		List<String> list = new ArrayList<>();
		if(!req.getParameter("start").equals("")) {
			//形式の判定
			try {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				df.setLenient(false);
			    String s1 = req.getParameter("start");
			    String s2 = df.format(df.parse(s1));

			    if(s1.equals(s2)) {

			    }else {
			    	list.add("販売日（検索開始日）を正しく入力してください。");
			    }

			}catch(ParseException p) {

				list.add("販売日（検索開始日）を正しく入力してください。");

			}
		}
		if(!req.getParameter("end").equals("")) {
			//形式の判定
			try {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				df.setLenient(false);
			    String s1 = req.getParameter("end");
			    String s2 = df.format(df.parse(s1));

			    if(s1.equals(s2)) {

			    }else {
			    	list.add("販売日（検索終了日）を正しく入力してください。");
			    }

			}catch(ParseException p) {

				list.add("販売日（検索終了日）を正しく入力してください。");

			}
		}

		if(!req.getParameter("end").equals("") && !req.getParameter("start").equals("")) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
			fmt.setLenient(false);
			try {
				Date fs = fmt.parse(req.getParameter("start"));
				Date fe = fmt.parse(req.getParameter("end"));
				if(!fs.before(fe)) {
					list.add("販売日（検索開始日）が販売日（検索終了日）より後の日付になっています。");
				}
			} catch (Exception e1) {

			}
		}

		return list;


	}

}
