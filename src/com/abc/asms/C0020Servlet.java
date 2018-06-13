package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.Sales;
import com.abc.asms.utils.DBUtils;

@WebServlet("/C0020.html")
public class C0020Servlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;


		// localDateから現在時刻を抽出するか否かの判定
		LocalDate ld = null;

		// checkに何もない場合現在日時抽出、ある場合はLocalDateに変換
		if(req.getParameter("back") != null) {
			ld = LocalDate.parse(req.getParameter("back") + "01日", DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
		} else {
			ld = LocalDate.now();
		};

		// 表示月とその月初末の変数宣言
		String today = null;
		LocalDate first = null;
		LocalDate last = null;

		// 前月と翌月のパラメータ取得

		if(req.getParameter("back") != null) {
			// 前月
			today = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.minusMonths(1));
			first = ld.withDayOfMonth(1).minusMonths(1);
			last = ld.withDayOfMonth(1).minusDays(1);
		} else {
			// 今月
			today = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld);
			first = ld.withDayOfMonth(1);
			last = ld.withDayOfMonth(1).plusMonths(1).minusDays(1);
		};

		try {
			con = DBUtils.getConnection();
			// SQL	現在、確認用でs.account_id7で検索条件絞り込んでます
			sql = "SELECT s.sale_id, s.sale_date, a.name, c.category_name, s.trade_name, s.unit_price, s.sale_number, s.note  FROM sales s " +
					"LEFT JOIN categories c ON s.category_id = c.category_id " +
					"LEFT JOIN accounts a ON s.account_id = a.account_id " +
					"WHERE s.sale_date BETWEEN ? AND ? AND s.account_id = 7 " +
					"ORDER BY s.sale_date";


			// SELECT命令の準備
			ps = con.prepareStatement(sql);

			// where句に代入
			ps.setString(1, first.toString());
			ps.setString(2, last.toString());

			// 実行
			rs = ps.executeQuery();

			List<Sales> list = new ArrayList<>();
			while(rs.next()) {
				Sales a = new Sales(
						rs.getInt("sale_id"),
						LocalDate.parse(rs.getString("sale_date"), DateTimeFormatter.ofPattern("yyyy/MM/dd")),
						rs.getString("name"),
						rs.getString("category_name"),
						rs.getString("trade_name"),
						rs.getInt("unit_price"),
						rs.getInt("sale_number"),
						rs.getString("note"));
				list.add(a);
			}
			// JavaBeansをJSPに渡す
			session.setAttribute("today", today);
			session.setAttribute("list", list);

			getServletContext().getRequestDispatcher("/WEB-INF/C0020.jsp").forward(req, resp);

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				DBUtils.close(con);
				DBUtils.close(rs);
				DBUtils.close(ps);
			} catch (Exception e) {

			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.sendRedirect("C0020.html");
	}
}
