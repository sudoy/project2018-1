package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.SaleList;
import utils.DBUtils;
import utils.ServletUtils;

@WebServlet("/S0023.html")
public class S0023Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

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
			sql = "select s.sale_date, a.name, c.category_name, s.trade_name, s.unit_price, s.sale_number, s.unit_price * s.sale_number as total, s.note " +
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

			SaleList s = new SaleList(
					rs.getDate("sale_date"),
					rs.getString("name"),
					rs.getString("category_name"),
					rs.getString("trade_name"),
					rs.getInt("unit_price"),
					rs.getInt("sale_number"),
					rs.getInt("total"),
					rs.getString("note")
					);
			req.setAttribute("list", s);

			//カテゴリーリスト
			List<String> categoryList = ServletUtils.categoryList(req);
			req.setAttribute("categoryList", categoryList);

			//担当リスト
			List<String> accountList = ServletUtils.accountList(req);
			req.setAttribute("accountList", accountList);

			//フォワード
			getServletContext().getRequestDispatcher("/WEB-INF/S0023.jsp").forward(req, resp);

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
}
