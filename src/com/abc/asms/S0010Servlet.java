package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.DBUtils;

@WebServlet("/S0010.html")
public class S0010Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/WEB-INF/S0010.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		String  saleDate = req.getParameter("saleDate");
		String account = req.getParameter("account");
		String category = req.getParameter("category");
		String tradeName = req.getParameter("tradeName");
		String unitPrice = req.getParameter("unitPrice");
		String saleNumber = req.getParameter("saleNumber");
		String note = req.getParameter("note");

		//バリデーションチェック
		List<String> errors = validate(saleDate, account, category, tradeName, unitPrice, saleNumber);
		if (errors.size() > 0) {
			req.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/WEB-INF/S0010.jsp").forward(req, resp);
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

			ps.setString(1, saleDate);
			ps.setString(2, account);
			ps.setString(3, category);
			ps.setString(4, tradeName);
			ps.setString(5, unitPrice);
			ps.setString(6, saleNumber);

			// NULLの場合にどうすっぺ？もどうぞ
			ps.setString(7, note);

			ps.executeUpdate();
			List<String> successes = new ArrayList<>();

			// 登録確認画面に遷移しないといけないから、もしかしたらここの記述変わるかもしれんね
			successes.add("登録しました。");
			session.setAttribute("successes", successes);

			resp.sendRedirect("C0020.html");

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


	private List<String> validate(String saleDate, String account, String category, String tradeName, String unitPrice, String saleNumber) {
		List<String> errors = new ArrayList<>();

		//日付の必須入力
		if (saleDate.equals("")) {
			errors.add("日付は必須入力です。");
		}

		//
		if (account.equals("")) {
			errors.add("担当は必須選択です。");
		}

		//カテゴリーの必須入力
		if (category.equals("")) {
			errors.add("カテゴリーは必須選択です。");
		}

		//カテゴリーの必須入力
		if (tradeName.equals("")) {
			errors.add("商品名は必須入力です。");
		}

		//金額の必須入力
		if (unitPrice.equals("")) {
			errors.add("金額は必須入力です。");
		}

		//カテゴリーの必須入力
		if (saleNumber.equals("")) {
			errors.add("個数は必須入力です。");
		}

		return errors;

	}
}

