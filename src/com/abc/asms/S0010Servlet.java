package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

import utils.DBUtils;
import utils.ServletUtils;

@WebServlet("/S0010.html")
public class S0010Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		LocalDate ld = LocalDate.now();

		// 表示月とその月初末の変数宣言
		String today = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(ld);;
		req.setAttribute("today", today);

		List<String> categoryList = ServletUtils.categoryList(req);
		req.setAttribute("categoryList", categoryList);
		List<String> accountList = ServletUtils.accountList(req);
		req.setAttribute("accountList", accountList);

		getServletContext().getRequestDispatcher("/WEB-INF/S0010.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		List<String> categoryList = ServletUtils.categoryList(req);
		req.setAttribute("categoryList", categoryList);
		List<String> accountList = ServletUtils.accountList(req);
		req.setAttribute("accountList", accountList);

		String saleDate = req.getParameter("saleDate");
		String account = req.getParameter("account");
		String category = req.getParameter("category");
		String tradeName = req.getParameter("tradeName");
		String unitPrice = req.getParameter("unitPrice");
		String saleNumber = req.getParameter("saleNumber");
		String note = req.getParameter("note");

		System.out.println("saleDate:" + saleDate + ",　account:" + account + ",　category:" + category +
				",　tradeName:" + tradeName + ",　unitPrice:" + unitPrice +
				 ",　saleNumber:" + saleNumber + ",　note:" + note);

		//バリデーションチェック
		List<String> errors = validate(saleDate, account, category, tradeName, unitPrice, saleNumber, note);
		if (errors.size() > 0) {
			req.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/WEB-INF/S0010.jsp").forward(req, resp);
			return;
		}

		System.out.println(account + ":" + ServletUtils.parseAccountName());
		System.out.println(category + ":" + ServletUtils.pairCategory(category));

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


	private List<String> validate(String saleDate, String account, String category, String tradeName, String unitPrice, String saleNumber, String note) {
		List<String> errors = new ArrayList<>();

		// 販売日の必須入力
		if (saleDate.equals("") || saleDate == null) {
			errors.add("販売日を入力して下さい。");
			errors.add("販売日を正しく入力して下さい。");
		}

		// 担当の必須入力
		if (account.equals("") || account == null) {
			errors.add("担当が未選択です。");
		}

		//カテゴリーの必須入力
		if (category.equals("") || category == null) {
			errors.add("商品カテゴリーが未選択です。");
		}

		//商品名の必須入力
		if (tradeName.equals("") || tradeName == null) {
			errors.add("商品名を入力して下さい。");
		} else if(tradeName.length() > 100) {
			errors.add("商品名が長すぎます。");
		}

		//単価の必須入力
		if (unitPrice.equals("") || unitPrice == null) {
			errors.add("単価を入力して下さい。");
		}
		else if(unitPrice.length() > 9) {
			errors.add("単価が長すぎます。");
		}
		// 単価形式のチェック
		if (!unitPrice.equals("") && Integer.parseInt(unitPrice) < 1) {
			errors.add("単価を正しく入力して下さい。");
		}

		//個数の必須入力
		if (saleNumber.equals("") || saleNumber == null) {
			errors.add("個数を入力して下さい。");
		}
		if(saleNumber.length() > 9) {
			errors.add("個数が長すぎます。");
		}
		// 個数形式のチェック
		if (!saleNumber.equals("") && Integer.parseInt(saleNumber) < 1) {
			errors.add("個数を正しく入力して下さい。");
		}

		// 備考の長さチェック
		if(note.length() > 400) {
			errors.add("備考が長すぎます。");
		}

		//errors.add("アカウントテーブルに存在しません。");
		//errors.add("商品カテゴリーテーブルに存在しません。");

		return errors;

	}

}

