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

import com.abc.asms.beans.Accounts;
import com.abc.asms.beans.Sales;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/C0020.html")
public class C0020Servlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		HttpSession session = req.getSession();
		req.setCharacterEncoding("utf-8");

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		// localDateから現在時刻を抽出するか否かの判定
		LocalDate ld = null;

		// どの変数を見るか選択
		String check= ServletUtils.checkParameter(req, resp);

		// checkに何もない場合現在日時抽出、ある場合はLocalDateに変換
		if(check != null) {
			ld = LocalDate.parse(check + "01日", DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
		} else {
			ld = LocalDate.now().withDayOfMonth(1);
		};

		// 表示月とその月初末の変数宣言
		String date = null;
		String lastday = null;
		LocalDate first = null;
		LocalDate last = null;

		// 前月と翌月のパラメータ取得
		if(req.getParameter("b") != null) {
			// 前月
			lastday = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.minusMonths(2));
			date = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.minusMonths(1));
			first = ld.minusMonths(1);
			last = ld.minusDays(1);
		} else if(req.getParameter("n") != null) {
			// 翌月
			lastday = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld);
			date = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.plusMonths(1));
			first = ld.plusMonths(1);
			last = ld.plusMonths(2).minusDays(1);
		} else if(req.getParameter("by") != null) {
			// 前年
			lastday = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.minusYears(1).minusMonths(1));
			date = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.minusYears(1));
			first = ld.minusYears(1);
			last = ld.plusMonths(1).minusDays(1).minusYears(1);
		} else if(req.getParameter("ny") != null) {
			// 翌年
			lastday = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.plusYears(1).minusMonths(1));
			date = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.plusYears(1));
			first = ld.plusYears(1);
			last = ld.plusMonths(1).minusDays(1).plusYears(1);
		} else {
			// 今月
			lastday = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld.minusMonths(1));
			date = DateTimeFormatter.ofPattern("yyyy年MM月").format(ld);
			first = ld;
			last = ld.plusMonths(1).minusDays(1);
		};

		// 今月売上合計と前月売上合計,初期値0
		int thisMonth = 0;
		int lastMonth = 0;

		try {
			con = DBUtils.getConnection();

			sql = "SELECT s.sale_id, s.sale_date, c.category_name, s.trade_name, s.unit_price, s.sale_number  FROM sales s " +
					"LEFT JOIN categories c ON s.category_id = c.category_id " +
					"LEFT JOIN accounts a ON s.account_id = a.account_id " +
					"WHERE s.sale_date BETWEEN ? AND ? AND s.account_id = ? " +
					"ORDER BY s.sale_date";


			// SELECT命令の準備
			ps = con.prepareStatement(sql);

			// where句に代入
			ps.setString(1, first.toString());
			ps.setString(2, last.toString());
			// ログインIDを取得
			Accounts accountId = (Accounts)session.getAttribute("accounts");
			ps.setInt(3, accountId.getAccountId());

			// 実行
			rs = ps.executeQuery();

			List<Sales> list = new ArrayList<>();
			while(rs.next()) {
				Sales a = new Sales(
						rs.getInt("sale_id"),
						LocalDate.parse(rs.getString("sale_date")),
						rs.getString("category_name"),
						rs.getString("trade_name"),
						rs.getInt("unit_price"),
						rs.getInt("sale_number"));
				list.add(a);
				// 今月売上合計の計算
				thisMonth += rs.getInt("unit_price") * rs.getInt("sale_number");
			}

			// 前月売上合計の計算
			lastMonth = ServletUtils.getTotalOfLastMonth(first, last, accountId.getAccountId());

			// JavaBeansをJSPに渡す
			req.setAttribute("date", date);
			req.setAttribute("lastday", lastday);
			req.setAttribute("thisMonth", thisMonth);
			req.setAttribute("lastMonth", lastMonth);
			req.setAttribute("list", list);

			getServletContext().getRequestDispatcher("/WEB-INF/C0020.jsp").forward(req, resp);

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (Exception e) {

			}
		}
	}

}
