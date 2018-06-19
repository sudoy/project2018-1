package com.abc.asms;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
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
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0010.html")
public class S0010Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		if(!ServletUtils.checkSales(req, resp)) {
			return;
		}

		LocalDate ld = LocalDate.now();

		// 本日表示用
		String today = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(ld);
		req.setAttribute("today", today);

		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		getServletContext().getRequestDispatcher("/WEB-INF/S0010.jsp").forward(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		if(!ServletUtils.checkSales(req, resp)) {
			return;
		}

		HttpSession session = req.getSession();

		req.setCharacterEncoding("utf-8");

		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		//バリデーションチェック
		List<String> errors = validate(req);
		if (errors.size() != 0) {
			session.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/WEB-INF/S0010.jsp").forward(req, resp);
			return;
		}

		Sales sales = new Sales(0,
				LocalDate.parse(req.getParameter("saleDate"), DateTimeFormatter.ofPattern("yyyy/MM/dd")),
				req.getParameter("account"),
				req.getParameter("category"),
				req.getParameter("tradeName"),
				Integer.parseInt(req.getParameter("unitPrice")),
				Integer.parseInt(req.getParameter("saleNumber")),
				req.getParameter("note"));

		session.setAttribute("sales", sales);
		resp.sendRedirect("S0011.html");
	}


	private List<String> validate(HttpServletRequest req) {
		List<String> errors = new ArrayList<>();

		// 販売日の必須入力
		if (req.getParameter("saleDate").equals("") || req.getParameter("saleDate") == null) {
			errors.add("販売日を入力して下さい。");
		}
		if(!req.getParameter("saleDate").equals("")) {
			try {
				LocalDate.parse(req.getParameter("saleDate"), DateTimeFormatter.ofPattern("uuuu/M/d")
						.withResolverStyle(ResolverStyle.STRICT));
			} catch (Exception p) {
				errors.add("販売日を正しく入力して下さい。");
			}
		}

		// 担当の必須入力
		if (req.getParameter("account").equals("") || req.getParameter("account") == null) {
			errors.add("担当が未選択です。");
		} else if (ServletUtils.matchAccount(req.getParameter("account")) == false) {
			errors.add("アカウントテーブルに存在しません。");
		}

		//カテゴリーの必須入力
		if (req.getParameter("category") == null) {
			errors.add("商品カテゴリーが未選択です。");
		} else if (ServletUtils.matchCategory(req.getParameter("category")) == false) {
			errors.add("商品カテゴリーテーブルに存在しません。");
		}

		//商品名の必須入力
		if (req.getParameter("tradeName").equals("") || req.getParameter("tradeName") == null) {
			errors.add("商品名を入力して下さい。");
		} else if(req.getParameter("tradeName").length() > 100) {
			errors.add("商品名が長すぎます。");
		}

		//単価の必須入力
		if (req.getParameter("unitPrice").equals("") || req.getParameter("unitPrice") == null) {
			errors.add("単価を入力して下さい。");
		}
		else if(req.getParameter("unitPrice").length() > 9) {
			errors.add("単価が長すぎます。");
		} else {
			// 単価形式のチェック
			int check1 = 0;
			try {
				check1 = Integer.parseInt(req.getParameter("unitPrice"));
				if (!req.getParameter("unitPrice").equals("") && check1 < 1) {
					errors.add("単価を正しく入力して下さい。");
				}
			} catch(Exception e) {
				errors.add("単価を正しく入力して下さい。");
			}
		}

		//個数の必須入力
		if (req.getParameter("saleNumber").equals("") || req.getParameter("saleNumber") == null) {
			errors.add("個数を入力して下さい。");
		}else if(req.getParameter("saleNumber").length() > 9) {
			errors.add("個数が長すぎます。");
		} else {
			// 個数形式のチェック
			int check2 = 0;
			try {
				check2 = Integer.parseInt(req.getParameter("saleNumber"));
				if (!req.getParameter("saleNumber").equals("") && check2 < 1) {
					errors.add("個数を正しく入力して下さい。");
				}
			} catch(Exception e) {
				errors.add("個数を正しく入力して下さい。");
			}
		}

		// 備考の長さチェック
		if(req.getParameter("note").length() > 400) {
			errors.add("備考が長すぎます。");
		}

		return errors;

	}


}

