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

@WebServlet("/S0023.html")
public class S0023Servlet extends HttpServlet {

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

		//担当リスト
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);
		//カテゴリーリスト
		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);
		Map<Integer, String> pickCategoryMap = ServletUtils.getPickCategoryMap(req);
		req.setAttribute("pickCategoryMap", pickCategoryMap);

		//フォワード
		getServletContext().getRequestDispatcher("/WEB-INF/S0023.jsp").forward(req, resp);

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

		//バリデーション
		List<String> errors = validate(req);
		if(errors.size() != 0) {
			session.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/S0023.jsp").forward(req, resp);

			return;
		}

		Sales s = new Sales(
				Integer.parseInt(req.getParameter("saleId")),
				LocalDate.parse(req.getParameter("saleDate"), DateTimeFormatter.ofPattern("yyyy/M/d")),
				req.getParameter("account"),
				req.getParameter("category"),
				req.getParameter("tradeName"),
				Integer.parseInt(req.getParameter("unitPrice")),
				Integer.parseInt(req.getParameter("saleNumber")),
				req.getParameter("note"),
				Integer.parseInt(req.getParameter("version"))
				);


		session.setAttribute("saleList", s);

		resp.sendRedirect("S0024.html");

	}

	private List<String> validate(HttpServletRequest req){

		List<String> errors = new ArrayList<>();

		//日付のチェック
		if(!req.getParameter("saleDate").equals("")) {
			//形式の判定
			try {
				LocalDate.parse(req.getParameter("saleDate"), DateTimeFormatter.ofPattern("uuuu/M/d")
						.withResolverStyle(ResolverStyle.STRICT));
			} catch (Exception p) {
				errors.add("販売日はYYYY/MM/DDの形式で入力して下さい。");
			}
		}else {
			//必須入力
			errors.add("販売日はYYYY/MM/DDの形式で入力して下さい。");
		}

		// 担当の必須入力
		if (req.getParameter("account").equals("")) {
			errors.add("担当が未選択です。");
		}
		else if (!ServletUtils.matchAccount(req.getParameter("account"))) {
			//アカウントテーブルのチェック
			errors.add("担当が存在しません。");
		}

		//カテゴリーの必須入力
		if (req.getParameter("category") == null) {
			errors.add("商品カテゴリーが未選択です。");
		}else if (ServletUtils.matchPickCategory(req.getParameter("category"))) {
			// 一致したらスルー
		}else if (!ServletUtils.matchCategory(req.getParameter("category"))) {
			//カテゴリーテーブルのチェック
			errors.add("商品が存在しません。");
		}

		//商品名の必須入力
		if (req.getParameter("tradeName").equals("")) {
			errors.add("商品名を入力して下さい。");
		}
		else if(req.getParameter("tradeName").length() > 100) {
			//商品名の長さチェック
			errors.add("商品名が長すぎます。");
		}

		//単価の必須入力
		if (req.getParameter("unitPrice").equals("")) {
			errors.add("単価を入力して下さい。");
		}else {
			if(req.getParameter("unitPrice").length() > 9) {
				//単価の長さチェック
				errors.add("単価が長すぎます。");
			}
			// 単価形式のチェック
			try {
				int a = Integer.parseInt(req.getParameter("unitPrice"));
				if (!req.getParameter("unitPrice").equals("") && a < 1) {
					errors.add("単価は整数かつ1以上で入力して下さい。");
				}

			}catch(Exception e) {
				errors.add("単価は整数かつ1以上で入力して下さい。");
			}
		}


		//個数の必須入力
		if (req.getParameter("saleNumber").equals("")) {
			errors.add("個数を入力して下さい。");
		}else {

			if(req.getParameter("saleNumber").length() > 9) {
				//長さチェック
				errors.add("個数が長すぎます。");
			}

			// 個数形式のチェック
			try {
				int a = Integer.parseInt(req.getParameter("saleNumber"));
				if (!req.getParameter("saleNumber").equals("") && a < 1) {
					errors.add("個数は整数かつ1以上で入力して下さい。");
				}

			}catch(Exception e) {
				errors.add("個数は整数かつ1以上で入力して下さい。");
			}
		}

		// 備考の長さチェック
		if(req.getParameter("note").length() > 400) {
			errors.add("備考が長すぎます。");
		}

		return errors;
	}




}
