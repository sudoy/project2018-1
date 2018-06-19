package com.abc.asms;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.abc.asms.utils.HTMLUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0023.html")
public class S0023Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		//売上権限チェック
		if(!ServletUtils.checkSales(req, resp)) {
			return;
		}

		//担当リスト
		Map<Integer, String> accountMap = ServletUtils.getAccountMap(req);
		req.setAttribute("accountMap", accountMap);

		//カテゴリーリスト
		Map<Integer, String> categoryMap = ServletUtils.getCategoryMap(req);
		req.setAttribute("categoryMap", categoryMap);

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

		LocalDate saleDate = LocalDate.parse(HTMLUtils.replaceSaleDate(req.getParameter("saleDate")));

		Sales s = new Sales(
				Integer.parseInt(req.getParameter("saleId")),
				saleDate,
				ServletUtils.escapeHTML(req.getParameter("account")),
				ServletUtils.escapeHTML(req.getParameter("category")),
				ServletUtils.escapeHTML(req.getParameter("tradeName")),
				Integer.parseInt(req.getParameter("unitPrice")),
				Integer.parseInt(req.getParameter("saleNumber")),
				ServletUtils.escapeHTML(req.getParameter("note"))
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
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				df.setLenient(false);
			    String s1 = req.getParameter("saleDate");
			    String s2 = df.format(df.parse(s1));

			    if(s1.equals(s2)) {

			    }else {
			    	errors.add("販売日を正しく入力してください。");
			    }

			}catch(ParseException p) {
				errors.add("販売日を正しく入力してください。");
			}
		}else {
			//必須入力
			errors.add("販売日を入力して下さい。");
		}

		// 担当の必須入力
		if (req.getParameter("account").equals("")) {
			errors.add("担当が未選択です。");
		}
		else if (ServletUtils.matchAccount(req.getParameter("account")) == false) {
			//アカウントテーブルのチェック
			errors.add("アカウントテーブルに存在しません。");
		}

		//カテゴリーの必須入力
		if (req.getParameter("category").equals("")) {
			errors.add("商品カテゴリーが未選択です。");
		}
		else if (ServletUtils.matchCategory(req.getParameter("category")) == false) {
			//カテゴリーテーブルのチェック
			errors.add("商品カテゴリーテーブルに存在しません。");
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
		}else if(req.getParameter("unitPrice").length() > 9) {
			//単価の長さチェック
			errors.add("単価が長すぎます。");
		}
		// 単価形式のチェック
		try {
			int a = Integer.parseInt(req.getParameter("unitPrice"));
			if (!req.getParameter("unitPrice").equals("") && a < 1) {
				errors.add("単価を正しく入力して下さい。");
			}

		}catch(Exception e) {
			errors.add("単価を正しく入力して下さい。");
		}

		//個数の必須入力
		if (req.getParameter("saleNumber").equals("")) {
			errors.add("個数を入力して下さい。");
		}
		if(req.getParameter("saleNumber").length() > 9) {
			//長さチェック
			errors.add("個数が長すぎます。");
		}

		// 個数形式のチェック
		try {
			int a = Integer.parseInt(req.getParameter("saleNumber"));
			if (!req.getParameter("saleNumber").equals("") && a < 1) {
				errors.add("個数を正しく入力して下さい。");
			}

		}catch(Exception e) {
			errors.add("個数を正しく入力して下さい。");
		}


		// 備考の長さチェック
		if(req.getParameter("note").length() > 400) {
			errors.add("備考が長すぎます。");
		}

		return errors;
	}




}
