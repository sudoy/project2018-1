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

import com.abc.asms.beans.Accounts;
import com.abc.asms.beans.Sales;
import com.abc.asms.utils.HTMLUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0023.html")
public class S0023Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//セッションの読み込み
		HttpSession session = req.getSession();

		req.setCharacterEncoding("utf-8");

		//権限チェック
		List<String> check = checkAuthority(req);
		if(check.size() != 0) {
			session.setAttribute("check", check);

			resp.sendRedirect("C0020.html");
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


		//権限チェック
		List<String> check = checkAuthority(req);
		if(check.size() != 0) {
			session.setAttribute("check", check);

			resp.sendRedirect("C0020.html");
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
			req.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/S0023.jsp").forward(req, resp);

			return;
		}

		LocalDate saleDate = LocalDate.parse(HTMLUtils.dateFormat(req.getParameter("saleDate")));

		Sales s = new Sales(
				Integer.parseInt(req.getParameter("sale_id")),
				saleDate,
				req.getParameter("account"),
				req.getParameter("category"),
				req.getParameter("tradeName"),
				Integer.parseInt(req.getParameter("unitPrice")),
				Integer.parseInt(req.getParameter("saleNumber")),
				req.getParameter("note")
				);

		session.setAttribute("saleList", s);

		resp.sendRedirect("S0024.html");

	}

	private List<String> checkAuthority(HttpServletRequest req){
		List<String> list = new ArrayList<>();

		//セッションの読み込み
		HttpSession session = req.getSession();

		Accounts a = (Accounts)session.getAttribute("accounts");
		int authority = a.getAuthority();

		if(!(authority == 0 || authority == 10)) {
			list.add("不正なアクセスです");
		}

		return list;
	}

	private List<String> validate(HttpServletRequest req){

		List<String> list = new ArrayList<>();

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
			    	list.add("販売日を正しく入力してください。");
			    }

			}catch(ParseException p) {
				list.add("販売日を正しく入力してください。");
			}
		}else {
			//必須入力
			list.add("販売日を入力して下さい。");
		}

		// 担当の必須入力
		if (req.getParameter("account").equals("")) {
			list.add("担当が未選択です。");
		}
//		else if (ServletUtils.matchAccount(req.getParameter("account")) == false) {
//			//アカウントテーブルのチェック
//			list.add("アカウントテーブルに存在しません。");
//		}

		//カテゴリーの必須入力
		if (req.getParameter("category").equals("")) {
			list.add("商品カテゴリーが未選択です。");
		}
//		else if (ServletUtils.matchCategory(req.getParameter("category")) == false) {
//			//カテゴリーテーブルのチェック
//			list.add("商品カテゴリーテーブルに存在しません。");
//		}

		//商品名の必須入力
		if (req.getParameter("tradeName").equals("")) {
			list.add("商品名を入力して下さい。");
		}
		else if(req.getParameter("tradeName").length() > 100) {
			//商品名の長さチェック
			list.add("商品名が長すぎます。");
		}

		//単価の必須入力
		if (req.getParameter("unitPrice").equals("")) {
			list.add("単価を入力して下さい。");
		}else if(req.getParameter("unitPrice").length() > 9) {
			//単価の長さチェック
			list.add("単価が長すぎます。");
		}
		// 単価形式のチェック
		try {
			int a = Integer.parseInt(req.getParameter("unitPrice"));
		}catch(Exception e) {
			list.add("単価を正しく入力して下さい。");
		}
		if (!req.getParameter("unitPrice").equals("") && Integer.parseInt(req.getParameter("unitPrice")) < 1) {
			list.add("単価を正しく入力して下さい。");
		}

		//個数の必須入力
		if (req.getParameter("saleNumber").equals("")) {
			list.add("個数を入力して下さい。");
		}
		if(req.getParameter("saleNumber").length() > 9) {
			//長さチェック
			list.add("個数が長すぎます。");
		}
		// 個数形式のチェック
		try {
			int a = Integer.parseInt(req.getParameter("saleNumber"));
		}catch(Exception e) {
			list.add("個数を正しく入力して下さい。");
		}

		if (!req.getParameter("saleNumber").equals("") && Integer.parseInt(req.getParameter("saleNumber")) < 1) {
			list.add("個数を正しく入力して下さい。");
		}

		// 備考の長さチェック
		if(req.getParameter("note").length() > 400) {
			list.add("備考が長すぎます。");
		}

		return list;
	}

}
