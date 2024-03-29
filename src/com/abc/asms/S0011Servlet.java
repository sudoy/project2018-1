package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.HTMLUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0011.html")
public class S0011Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (!ServletUtils.checkLogin(req, resp)) {
			return;
		}
		if (!ServletUtils.checkSales(req, resp)) {
			return;
		}
		req.setCharacterEncoding("utf-8");

		HttpSession session = req.getSession();
		session.setAttribute("saleRemain", "on");
		if(session.getAttribute("sales") == null) {
			session.setAttribute("errors", "不正なアクセスです。");
			resp.sendRedirect("S0010.html");
			return;
		}

		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		getServletContext().getRequestDispatcher("/WEB-INF/S0011.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (!ServletUtils.checkLogin(req, resp)) {
			return;
		}
		if (!ServletUtils.checkSales(req, resp)) {
			return;
		}

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		String saleDate = req.getParameter("saleDate");
		String account = req.getParameter("account");
		String category = req.getParameter("category");
		String tradeName = req.getParameter("tradeName");
		String unitPrice = req.getParameter("unitPrice");
		String saleNumber = req.getParameter("saleNumber");
		String note = req.getParameter("note");

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try {
			con = DBUtils.getConnection();

			sql = "INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number, note) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);

			ps.setString(1, saleDate);
			ps.setString(2, account);
			ps.setString(3, category);
			ps.setString(4, tradeName);
			ps.setString(5, HTMLUtils.deleteComma(unitPrice));
			ps.setString(6, HTMLUtils.deleteComma(saleNumber));
			ps.setString(7, note);

			ps.executeUpdate();
			List<String> successes = new ArrayList<>();
			successes.add("No" + ServletUtils.registerSaleId() + "の売上を登録しました。");
			session.setAttribute("successes", successes);
			session.setAttribute("sales", null);
			resp.sendRedirect("S0010.html");

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (Exception e) {
			}
		}
	}


}
