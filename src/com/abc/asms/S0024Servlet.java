package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.beans.SaleList;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0024.html")
public class S0024Servlet extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		//カテゴリーリスト
		List<String> categoryList = ServletUtils.categoryList(req);
		req.setAttribute("categoryList", categoryList);

		//担当リスト
		List<String> accountList = ServletUtils.accountList(req);
		req.setAttribute("accountList", accountList);

		if(req.getParameter("submit").equals("")) {
			int total = Integer.parseInt(req.getParameter("unit_price"))
					* Integer.parseInt(req.getParameter("sale_number"));

			SaleList s = new SaleList(
					Date.valueOf(req.getParameter("sale_date")),
					req.getParameter("account"),
					req.getParameter("category"),
					req.getParameter("trade_name"),
					Integer.parseInt(req.getParameter("unit_price")),
					Integer.parseInt(req.getParameter("sale_number")),
					total,
					req.getParameter("note")
					);

			req.setAttribute("list", s);

			//フォワード
			getServletContext().getRequestDispatcher("/WEB-INF/S0024.jsp").forward(req, resp);

		}else {

			//アップデート
			req.setCharacterEncoding("utf-8");

			Connection con = null;
			PreparedStatement ps = null;
			String sql = null;


			try {
				con = DBUtils.getConnection();

				sql = "UPDATE sales SET sale_date = ?, account_id = ?, category_id = ? "
						+ " trade_name = ?, unit_price = ?, sale_number = ?, note = ? WHERE sale_id = ?";

				//準備
				ps = con.prepareStatement(sql);

				//データをセット
				ps.setString(1, req.getParameter("sale_date"));

				ps.setString(2, req.getParameter("account"));
				ps.setString(3, req.getParameter("category"));

				ps.setString(4, req.getParameter("trade_name"));
				ps.setString(5, req.getParameter("unit_price"));
				ps.setString(6, req.getParameter("sale_number"));
				ps.setString(7, req.getParameter("note"));
				ps.setString(8, req.getParameter("sale_id"));

				System.out.println(ps);


				//実行
				ps.executeUpdate();

				//フォワード
				getServletContext().getRequestDispatcher("/WEB-INF/S0020.jsp").forward(req, resp);


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







	}

}
