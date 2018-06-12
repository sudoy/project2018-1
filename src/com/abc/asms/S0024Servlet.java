package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.asms.beans.SaleList;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.HTMLUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0024.html")
public class S0024Servlet extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");


		//カテゴリーリスト
		List<String> categoryList = ServletUtils.categoryList(req);
		req.setAttribute("categoryList", categoryList);

		//担当リスト
		List<String> accountList = ServletUtils.accountList(req);
		req.setAttribute("accountList", accountList);

		//バリデーション
		List<String> errors = validate(req);
		if(errors.size() != 0) {
			req.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/S0023.jsp").forward(req, resp);
			return;
		}

		if(req.getParameter("submit").equals("")) {


			LocalDate strDate = LocalDate.parse(HTMLUtils.dateFormat(req.getParameter("sale_date")));
//
			SaleList s = new SaleList(
					Date.valueOf(strDate),
					req.getParameter("account"),
					req.getParameter("category"),
					req.getParameter("trade_name"),
					Integer.parseInt(req.getParameter("unit_price")),
					Integer.parseInt(req.getParameter("sale_number")),
					req.getParameter("note")
					);

			req.setAttribute("list", s);

			//フォワード
			getServletContext().getRequestDispatcher("/WEB-INF/S0024.jsp").forward(req, resp);

		}else {

			//アップデート
			req.setCharacterEncoding("utf-8");

			Connection con = null;
			PreparedStatement ps = null;
			String sql = null;


			try {
				con = DBUtils.getConnection();

				sql = "UPDATE sales SET sale_date = ?, account_id = ?, category_id = ?, trade_name = ?, unit_price = ?, sale_number = ?, note = ? WHERE sale_id = ?";

				//準備
				ps = con.prepareStatement(sql);

				//データをセット
				ps.setString(1, req.getParameter("sale_date"));
				ps.setString(2, ServletUtils.pairAccount(req.getParameter("account")));
				ps.setString(3, ServletUtils.pairCategory(req.getParameter("category")));
				ps.setString(4, req.getParameter("trade_name"));
				ps.setString(5, req.getParameter("unit_price"));
				ps.setString(6, req.getParameter("sale_number"));
				ps.setString(7, req.getParameter("note"));
				ps.setString(8, req.getParameter("sale_id"));

				//実行
				ps.executeUpdate();

				List<String> successes = new ArrayList<>();
				String success = "No" +  req.getParameter("sale_id") + "の売上を更新しました。";

				successes.add(success);
				req.setAttribute("successes", successes);

				//フォワード
				getServletContext().getRequestDispatcher("/WEB-INF/S0021.jsp").forward(req, resp);


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

	private List<String> validate(HttpServletRequest req){

		List<String> list = new ArrayList<>();

		//日付のチェック
		if(!req.getParameter("sale_date").equals("")) {
			//形式の判定
			try {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				df.setLenient(false);
			    String s1 = req.getParameter("sale_date");
			    String s2 = df.format(df.parse(s1));

			    if(s1.equals(s2)) {

			    }else {
			    	list.add("販売日を正しく入力してください。");
			    }

			}catch(ParseException p) {
				//必須入力
				list.add("販売日を正しく入力してください。");

			}
		}else {
			list.add("販売日を入力して下さい。");
		}

		// 担当の必須入力
		if (req.getParameter("account").equals("")) {
			list.add("担当が未選択です。");
		}else if (ServletUtils.matchAccount(req.getParameter("account")) == false) {
			list.add("アカウントテーブルに存在しません。");
		}

		//カテゴリーの必須入力
		if (req.getParameter("category").equals("")) {
			list.add("商品カテゴリーが未選択です。");
		}else if (ServletUtils.matchCategory(req.getParameter("category")) == false) {
			list.add("商品カテゴリーテーブルに存在しません。");
		}

		//商品名の必須入力
		if (req.getParameter("trade_name").equals("")) {
			list.add("商品名を入力して下さい。");
		} else if(req.getParameter("trade_name").length() > 100) {
			//長さチェック
			list.add("商品名が長すぎます。");
		}

		//単価の必須入力
		if (req.getParameter("unit_price").equals("")) {
			list.add("単価を入力して下さい。");
		}
		else if(req.getParameter("unit_price").length() > 9) {
			//長さチェック
			list.add("単価が長すぎます。");
		}
		// 単価形式のチェック
		if (!req.getParameter("unit_price").equals("") && Integer.parseInt(req.getParameter("unit_price")) < 1) {
			list.add("単価を正しく入力して下さい。");
		}

		//個数の必須入力
		if (req.getParameter("sale_number").equals("")) {
			list.add("個数を入力して下さい。");
		}
		if(req.getParameter("sale_number").length() > 9) {
			//長さチェック
			list.add("個数が長すぎます。");
		}
		// 個数形式のチェック
		if (!req.getParameter("sale_number").equals("") && Integer.parseInt(req.getParameter("sale_number")) < 1) {
			list.add("個数を正しく入力して下さい。");
		}

		// 備考の長さチェック
		if(req.getParameter("note").length() > 400) {
			list.add("備考が長すぎます。");
		}

		//アカウントテーブル存在チェック
		//商品カテゴリー存在チェック


		return list;
	}


}
