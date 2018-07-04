package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

@WebServlet("/S0024.html")
public class S0024Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		//売上権限チェック
		if(!ServletUtils.checkSales(req, resp)) {
			return;
		}
		if(session.getAttribute("saleList") == null) {
			session.setAttribute("errors", "不正なアクセスです。");
			resp.sendRedirect("S0020.html");
			return;
		}

		//カテゴリーリスト
		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);
		Map<Integer, String> pickCategoryMap = ServletUtils.getPickCategoryMap(req);
		req.setAttribute("pickCategoryMap", pickCategoryMap);

		//担当リスト
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		//フォワード
		getServletContext().getRequestDispatcher("/WEB-INF/S0024.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//セッションの読み込み
		HttpSession session = req.getSession();

		req.setCharacterEncoding("utf-8");

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		//売上権限チェック
		if(!ServletUtils.checkSales(req, resp)) {
			return;
		}

		//カテゴリーリスト
		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);
		Map<Integer, String> pickCategoryMap = ServletUtils.getPickCategoryMap(req);
		req.setAttribute("pickCategoryMap", pickCategoryMap);

		//担当リスト
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);


		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "UPDATE sales SET sale_date = ?, account_id = ?, category_id = ?, trade_name = ?,"
					+ " unit_price = ?, sale_number = ?, note = ?, version = ? + 1 WHERE sale_id = ? AND version = ?";

			//準備
			ps = con.prepareStatement(sql);

			//データをセット
			Sales versionId = (Sales)session.getAttribute("saleList");

			ps.setString(1, req.getParameter("saleDate"));
			ps.setString(2, req.getParameter("account"));
			ps.setString(3, req.getParameter("category"));
			ps.setString(4, req.getParameter("tradeName"));
			ps.setString(5, HTMLUtils.deleteComma(req.getParameter("unitPrice")));
			ps.setString(6, HTMLUtils.deleteComma(req.getParameter("saleNumber")));
			ps.setString(7, req.getParameter("note"));
			ps.setInt(8, versionId.getVersion());
			ps.setString(9, req.getParameter("saleId"));
			ps.setInt(10, versionId.getVersion());

			//実行

			int updateRows = ps.executeUpdate();

			if (updateRows == 0) {
				List<String> errors = new ArrayList<>();
				if(!ServletUtils.notFoundData(0, req.getParameter("saleId"))) {
					errors.add("No" + req.getParameter("saleId") + "の売上は既に削除されています。");
					session.setAttribute("errors", errors);
					resp.sendRedirect("S0020.html");
					return;
				}
				errors.add("No" + req.getParameter("saleId") + "の売上の更新に失敗しました。");
				session.setAttribute("errors", errors);
				resp.sendRedirect("S0021.html");
				return;
			}

			List<String> successes = new ArrayList<>();
			String success = "No" +  req.getParameter("saleId") + "の売上を更新しました。";

			successes.add(success);
			session.setAttribute("successes", successes);

			session.setAttribute("saleList", null);
			session.setAttribute("ssf", null);

			resp.sendRedirect("S0020.html");


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
