package com.abc.asms;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0010.html")
public class S0010Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		LocalDate ld = LocalDate.now();

		// 表示月とその月初末の変数宣言
		String today = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(ld);
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

		List<String> categoryList = ServletUtils.categoryList(req);
		req.setAttribute("categoryList", categoryList);
		List<String> accountList = ServletUtils.accountList(req);
		req.setAttribute("accountList", accountList);

		//バリデーションチェック
		List<String> errors = validate(req);
		if (errors.size() != 0) {
			req.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/WEB-INF/S0010.jsp").forward(req, resp);
			return;
		}

		getServletContext().getRequestDispatcher("/WEB-INF/S0011.jsp").forward(req, resp);
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
		if (req.getParameter("category").equals("") || req.getParameter("category") == null) {
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
		}
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

		//個数の必須入力
		if (req.getParameter("saleNumber").equals("") || req.getParameter("saleNumber") == null) {
			errors.add("個数を入力して下さい。");
		}
		if(req.getParameter("saleNumber").length() > 9) {
			errors.add("個数が長すぎます。");
		}
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

		// 備考の長さチェック
		if(req.getParameter("note").length() > 400) {
			errors.add("備考が長すぎます。");
		}

		return errors;

	}

}

