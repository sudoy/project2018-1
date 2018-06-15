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

import com.abc.asms.beans.Accounts;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.HTMLUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0024.html")
public class S0024Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//セッションの読み込み
		HttpSession session = req.getSession();

		//権限チェック
		List<String> check = checkAuthority(req);
		if(check.size() != 0) {
			session.setAttribute("errors", check);

			resp.sendRedirect("C0020.html");
		}

		//カテゴリーリスト
		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);

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

		//権限チェック
		List<String> check = checkAuthority(req);
		if(check.size() != 0) {
			session.setAttribute("errors", check);

			resp.sendRedirect("C0020.html");
		}

		//カテゴリーリスト
		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);

		//担当リスト
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);


		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try {
			con = DBUtils.getConnection();

			sql = "UPDATE sales SET sale_date = ?, account_id = ?, category_id = ?, trade_name = ?,"
					+ " unit_price = ?, sale_number = ?, note = ? WHERE sale_id = ?";

			//準備
			ps = con.prepareStatement(sql);

			//データをセット
			ps.setString(1, req.getParameter("saleDate"));

			ps.setString(2, req.getParameter("account"));
			ps.setString(3, req.getParameter("category"));

			ps.setString(4, req.getParameter("tradeName"));
			ps.setString(5, HTMLUtils.deleteComma(req.getParameter("unitPrice")));
			ps.setString(6, HTMLUtils.deleteComma(req.getParameter("saleNumber")));
			ps.setString(7, req.getParameter("note"));
			ps.setString(8, req.getParameter("saleId"));

			//実行
			ps.executeUpdate();

			List<String> successes = new ArrayList<>();
			String success = "No" +  req.getParameter("saleId") + "の売上を更新しました。";

			successes.add(success);
			session.setAttribute("successes", successes);

			session.setAttribute("saleList", null);

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

		if(!(authority == 1 || authority == 11)) {
			list.add("不正なアクセスです");
		}

		return list;
	}

}
