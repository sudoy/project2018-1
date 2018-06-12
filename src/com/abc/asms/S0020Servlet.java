package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import com.abc.asms.beans.Sales;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0020.html")
public class S0020Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);

		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);
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

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;
		List<Sales> salesList = new ArrayList<>();

		try{

			con = DBUtils.getConnection();

			sql = "select * from sales where 0=0";

			if(!req.getParameter("start").equals("")) {
				sql = sql.concat(" and sale_date >= '" + req.getParameter("start") + "'");
			}
			if(!req.getParameter("end").equals("")) {
				sql = sql.concat(" and sale_date <= '" + req.getParameter("end") + "'");
			}

			if(!req.getParameter("account").equals("")) {
				sql = sql.concat(" and account_id = '" + ServletUtils.pairAccount(req.getParameter("account")) + "'");
			}

			if(!req.getParameter("category").equals("")) {
				sql = sql.concat(" and category_id = '" + req.getParameter("category") + "'");
			}

			if(!req.getParameter("tradeName").equals("")) {
				sql = sql.concat(" and trade_name like '%" + req.getParameter("tradeName") + "%'");
			}
			if(!req.getParameter("note").equals("")) {
				sql = sql.concat(" and note like '%" + req.getParameter("note") + "%'");
			}

			sql = sql.concat(" order by sale_id desc");

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			if(!rs.next()) {
				errors.add("検索結果がありません。");
				session.setAttribute("errors", errors);
				getServletContext().getRequestDispatcher("/WEB-INF/S0020.jsp").forward(req, resp);
			}else {
				Sales s = new Sales(rs.getInt("sale_id"), rs.getDate("sale_date"),
						ServletUtils.parseAccountName(rs.getInt("account_id")),
						ServletUtils.parseCategoryName(rs.getInt("category_id")),
						rs.getString("trade_name"), rs.getInt("unit_price"),
						rs.getInt("sale_number"), rs.getString("note"));

				salesList.add(s);

				while(rs.next()) {

					s = new Sales(rs.getInt("sale_id"), rs.getDate("sale_date"),
							ServletUtils.parseAccountName(rs.getInt("account_id")),
							ServletUtils.parseCategoryName(rs.getInt("category_id")),
							rs.getString("trade_name"), rs.getInt("unit_price"),
							rs.getInt("sale_number"), rs.getString("note"));

					salesList.add(s);
				}
				session.setAttribute("salesList", salesList);
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

		resp.sendRedirect("S0021.html");

	}

	private List<String> validate(HttpServletRequest req){

		List<String> list = new ArrayList<>();

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		LocalDate dateS = null;
		LocalDate dateE = null;

		if(!start.equals("")) {
			try {
				dateS = LocalDate.parse(start, DateTimeFormatter.ofPattern("uuuu/M/d")
						.withResolverStyle(ResolverStyle.STRICT));
			} catch (Exception p) {
				list.add("販売日（検索開始日）を正しく入力して下さい。");
			}
		}
		if(!end.equals("")) {
			try {
				dateE = LocalDate.parse(end, DateTimeFormatter.ofPattern("uuuu/M/d")
						.withResolverStyle(ResolverStyle.STRICT));
			} catch (Exception p) {
				list.add("販売日（検索終了日）を正しく入力して下さい。");
			}
		}

		if(dateS != null && dateE != null) {
			if(!dateS.isBefore(dateE)) {
				list.add("販売日（検索開始日）が販売日（検索終了日）より後の日付になっています。");
			}
		}


		return list;


	}

}
