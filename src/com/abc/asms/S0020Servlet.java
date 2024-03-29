package com.abc.asms;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.SearchSaleForm;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0020.html")
public class S0020Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		//エラーの際の入力保持
		if(session.getAttribute("ssf") != null && session.getAttribute("saleRemain") != null) {
			SearchSaleForm ssf = (SearchSaleForm) session.getAttribute("ssf");
			req.setAttribute("ssf", ssf);
			session.setAttribute("saleRemain", null);
		}
		session.setAttribute("ssf", null);

		//商品カテゴリーと担当をデータベースからとってくる。
		Map<Integer, String> categoryMap = ServletUtils.getAllCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);

		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		LocalDate today = LocalDate.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String stringToday = today.format(fmt);
		req.setAttribute("stringToday", stringToday);


		getServletContext().getRequestDispatcher("/WEB-INF/S0020.jsp").forward(req, resp);



	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		req.setCharacterEncoding("utf-8");

		//商品カテゴリーと担当をデータベースからとってくる。
		Map<Integer, String> categoryMap = ServletUtils.getAllCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);

		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		HttpSession session = req.getSession();
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String account = req.getParameter("account");
		String kana = req.getParameter("kana");
		String[] category = {"a"};
		if(req.getParameter("category") != null) {
			category = req.getParameterValues("category");
		}
		String tradeName = req.getParameter("tradeName");
		String note = req.getParameter("note");

		//入力結果を検索結果のページに送る。
		SearchSaleForm ssf = new SearchSaleForm(start, end, account, kana, category, tradeName, note);

		//入力内容のチェック
		List<String> errors = validate(req);

		if(errors.size() != 0) {
			session.setAttribute("errors", errors);
			req.setAttribute("ssf", ssf);

			getServletContext().getRequestDispatcher("/WEB-INF/S0020.jsp")
			.forward(req, resp);
			return;

		}
		session.setAttribute("ssf", ssf);

		resp.sendRedirect("S0021.html");

	}

	private List<String> validate(HttpServletRequest req){

		List<String> errors = new ArrayList<>();

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		LocalDate dateS = null;
		LocalDate dateE = null;

		//日付の入力チェック
		if(!start.equals("")) {
			try {
				dateS = LocalDate.parse(start, DateTimeFormatter.ofPattern("uuuu/M/d")
						.withResolverStyle(ResolverStyle.STRICT));
			} catch (Exception p) {
				errors.add("販売日（検索開始日）はYYYY/MM/DDの形式で入力して下さい。");
			}
		}
		if(!end.equals("")) {
			try {
				dateE = LocalDate.parse(end, DateTimeFormatter.ofPattern("uuuu/M/d")
						.withResolverStyle(ResolverStyle.STRICT));
			} catch (Exception p) {
				errors.add("販売日（検索終了日）はYYYY/MM/DDの形式で入力して下さい。");
			}
		}

		if(dateS != null && dateE != null && !dateS.equals(dateE)) {
			if(!dateS.isBefore(dateE)) {
				errors.add("販売日（検索開始日）が販売日（検索終了日）より後の日付になっています。");
			}
		}

		if(!req.getParameter("kana").matches("^[ぁ-ん]*$")) {
			errors.add("担当(ふりがな)には平仮名を入力してください。");
		}

		return errors;


	}

}
