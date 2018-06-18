package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

		if (req.getParameter("NG") != null) {

			Sales sales = new Sales(0,
					LocalDate.parse(escapeHTML(req.getParameter("saleDate")), DateTimeFormatter.ofPattern("yyyy/MM/dd")),
					escapeHTML(req.getParameter("account")),
					escapeHTML(req.getParameter("category")),
					escapeHTML(req.getParameter("tradeName")),
					Integer.parseInt(HTMLUtils.deleteComma(req.getParameter("unitPrice"))),
					Integer.parseInt(HTMLUtils.deleteComma(req.getParameter("saleNumber"))),
					escapeHTML(req.getParameter("note")));

			session.setAttribute("sales", sales);
			resp.sendRedirect("S0010.html");
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try {
			con = DBUtils.getConnection();

			sql = "INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number, note) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);

			ps.setString(1, escapeHTML(saleDate));
			ps.setString(2, escapeHTML(account));
			ps.setString(3, escapeHTML(category));
			ps.setString(4, escapeHTML(tradeName));
			ps.setString(5, HTMLUtils.deleteComma(unitPrice));
			ps.setString(6, HTMLUtils.deleteComma(saleNumber));
			ps.setString(7, escapeHTML(note));

			ps.executeUpdate();
			List<String> successes = new ArrayList<>();
			successes.add("No" + ServletUtils.registerSId() + "の売上を登録しました。");
			session.setAttribute("successes", successes);

			resp.sendRedirect("S0010.html");

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
			}
		}
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
