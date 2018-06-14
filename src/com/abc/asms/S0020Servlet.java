package com.abc.asms;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.SearchForm;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0020.html")
public class S0020Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		//ログインチェック
//		if(session.getAttribute("accounts") == null) {
//			List<String> errors = new ArrayList<>();
//			errors.add("ログインしてください。");
//			session.setAttribute("errors", errors);
//			resp.sendRedirect("C0010.html");
//			return;
//		}

		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
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

		req.setCharacterEncoding("utf-8");

		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);

		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		HttpSession session = req.getSession();
		List<String> errors = validate(req);

		if(errors.size() != 0) {
			session.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/S0020.jsp")
			.forward(req, resp);
			return;

		}

			Map<String, String> sqlParameter = new LinkedHashMap<>();

			String sql = "select sale_id, sale_date, account_id, category_id, trade_name, unit_price, sale_number, note "
					+ "from sales where 0=0";

			if(!req.getParameter("start").equals("")) {
				sql = sql.concat(" and sale_date >= ?");
				sqlParameter.put("start", req.getParameter("start"));
			}

			if(!req.getParameter("end").equals("")) {
				sql = sql.concat(" and sale_date <= ?");
				sqlParameter.put("end", req.getParameter("end"));
			}

			if(!req.getParameter("account").equals("")) {
				sql = sql.concat(" and account_id = ?");
				sqlParameter.put("account", req.getParameter("account"));
			}
			String[] categories = req.getParameterValues("category");
			if(categories != null) {
				sql = sql.concat(" and category_id in(");

				for(int i = 0; i < categories.length; i++) {
					if(i == 0) {
						sql = sql.concat("?");
						sqlParameter.put("category" + i, categories[i]);
					}else {
						sql = sql.concat(",?");
						sqlParameter.put("category" + i, categories[i]);
					}
				}

				sql = sql.concat(")");
			}

			if(!req.getParameter("tradeName").equals("")) {
				sql = sql.concat(" and trade_name like ?");
				sqlParameter.put("tradeName", req.getParameter("tradeName"));
			}

			if(!req.getParameter("note").equals("")) {
				sql = sql.concat(" and note like ?");
				sqlParameter.put("note", req.getParameter("note"));
			}

			sql = sql.concat(" order by sale_id desc");


			SearchForm searchForm = new SearchForm(sql, sqlParameter);

			session.setAttribute("searchForm", searchForm);


		resp.sendRedirect("S0021.html");

	}

	private List<String> validate(HttpServletRequest req){

		List<String> errors = new ArrayList<>();

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		LocalDate dateS = null;
		LocalDate dateE = null;

		if(!start.equals("")) {
			try {
				dateS = LocalDate.parse(start, DateTimeFormatter.ofPattern("uuuu/M/d")
						.withResolverStyle(ResolverStyle.STRICT));
			} catch (Exception p) {
				errors.add("販売日（検索開始日）を正しく入力して下さい。");
			}
		}
		if(!end.equals("")) {
			try {
				dateE = LocalDate.parse(end, DateTimeFormatter.ofPattern("uuuu/M/d")
						.withResolverStyle(ResolverStyle.STRICT));
			} catch (Exception p) {
				errors.add("販売日（検索終了日）を正しく入力して下さい。");
			}
		}

		if(dateS != null && dateE != null && !dateS.equals(dateE)) {
			if(!dateS.isBefore(dateE)) {
				errors.add("販売日（検索開始日）が販売日（検索終了日）より後の日付になっています。");
			}
		}


		return errors;


	}

}
