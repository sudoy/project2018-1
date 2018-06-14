package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
import com.abc.asms.utils.HTMLUtils;

@WebServlet("/S0025.html")
public class S0025Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//セッションの読み込み
		HttpSession session = req.getSession();


		req.setCharacterEncoding("utf-8");

		//権限チェック
		List<String> check = checkAuthority(req);
		if(check.size() != 0) {
			session.setAttribute("check", check);

			resp.sendRedirect("C0020.html");
		}

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;


		try {
			//データベース接続
			con = DBUtils.getConnection();

			//GETパラメータを取得
			String id = req.getParameter("sale_id");

			//SQL
			//totalいらない
			sql = "select s.sale_id, s.sale_date, a.name, c.category_name, " +
					"s.trade_name, s.unit_price, s.sale_number, s.note " +
					"FROM sales s " +
					"JOIN accounts a ON s.account_id = a.account_id " +
					"JOIN categories c ON s.category_id = c.category_id " +
					"WHERE sale_id = ?";

			//準備
			ps = con.prepareStatement(sql);

			//パラメータをセット
			ps.setString(1, id);

			//実行
			rs = ps.executeQuery();

			rs.next();

			LocalDate saleDate = LocalDate.parse(rs.getString("sale_date"));

			Sales s = new Sales(
					rs.getInt("sale_id"),
					saleDate,
					rs.getString("name"),
					rs.getString("category_name"),
					rs.getString("trade_name"),
					rs.getInt(HTMLUtils.deleteComma("unit_price")),
					rs.getInt(HTMLUtils.deleteComma("sale_number")),
					rs.getString("note")
					);
			req.setAttribute("list", s);

			//フォワード
			getServletContext().getRequestDispatcher("/WEB-INF/S0025.jsp").forward(req, resp);

		}catch(Exception e){
			throw new ServletException(e);
		}finally{

			try{
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			}catch(Exception e){

			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//セッションの読み込み
		HttpSession session = req.getSession();

		//権限チェック
		List<String> check = checkAuthority(req);
		if(check.size() != 0) {
			session.setAttribute("check", check);

			resp.sendRedirect("C0020.html");
		}

		req.setCharacterEncoding("utf-8");

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try {
			con = DBUtils.getConnection();

			sql = "DELETE FROM sales WHERE sale_id = ?";

			//準備
			ps = con.prepareStatement(sql);

			//データをセット
			ps.setString(1, req.getParameter("sale_id"));

			//実行
			ps.executeUpdate();

			List<String> successes = new ArrayList<>();
			String success = "No" +  req.getParameter("sale_id") + "の売上を削除しました。";

			successes.add(success);
			session.setAttribute("successes", successes);

			resp.sendRedirect("S0021.html");

		}catch(Exception e){
			throw new ServletException(e);

		}finally{

			try{
				DBUtils.close(ps);
				DBUtils.close(con);

			}catch(Exception e){

			}
		}

	}

	private List<String> checkAuthority(HttpServletRequest req){
		List<String> list = new ArrayList<>();

		//セッションの読み込み
		HttpSession session = req.getSession();

		Accounts a = (Accounts)session.getAttribute("accounts");
		int authority = a.getAuthority();

		if(authority == 1 || authority == 11) {
			list.add("不正なアクセスです");
		}

		return list;
	}
}
