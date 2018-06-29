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
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0025.html")
public class S0025Servlet extends HttpServlet {

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

		//直接アドレスを入力してきた際の対応(結合テスト修正箇所)
		if(session.getAttribute("saleList") == null) {
			List<String> errors = new ArrayList<>();
			errors.add("不正なアクセスです。");
			session.setAttribute("errors", errors);
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
		getServletContext().getRequestDispatcher("/WEB-INF/S0025.jsp").forward(req, resp);


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//セッションの読み込み
		HttpSession session = req.getSession();

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		//売上権限チェック
		if(!ServletUtils.checkSales(req, resp)) {
			return;
		}

		//直接アドレスを入力してきた際の対応(結合テスト修正箇所)
		if(session.getAttribute("saleList") == null) {
			List<String> errors = new ArrayList<>();
			errors.add("不正なアクセスです。");
			session.setAttribute("errors", errors);
			resp.sendRedirect("S0020.html");
			return;
		}

		req.setCharacterEncoding("utf-8");

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			con = DBUtils.getConnection();
			sql = "DELETE FROM sales WHERE sale_id = ? AND version = ?";

			//準備
			ps = con.prepareStatement(sql);

			//データをセット
			ps.setString(1, req.getParameter("saleId"));
			ps.setString(2, req.getParameter("version"));

			//実行
			int deleteRows = ps.executeUpdate();
			if (deleteRows == 0) {
				List<String> errors = new ArrayList<>();
				if(!ServletUtils.notFoundData(0, req.getParameter("saleId"))) {
					errors.add("No" + req.getParameter("saleId") + "の売上は既に削除されています。");
					session.setAttribute("errors", errors);
					resp.sendRedirect("S0020.html");
					return;
				}
				errors.add("No" + req.getParameter("saleId") + "の売上の削除に失敗しました。");
				session.setAttribute("errors", errors);
				resp.sendRedirect("S0021.html");
				return;
			}

			List<String> successes = new ArrayList<>();
			String success = "No" +  req.getParameter("saleId") + "の売上を削除しました。";

			successes.add(success);
			session.setAttribute("successes", successes);

			session.setAttribute("saleList", null);

			//検索条件削除(結合テスト修正箇所)
			session.setAttribute("ssf", null);

			resp.sendRedirect("S0020.html");

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
