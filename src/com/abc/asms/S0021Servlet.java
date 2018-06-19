package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.Sales;
import com.abc.asms.beans.SearchSaleForm;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0021.html")
public class S0021Servlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		req.setCharacterEncoding("utf-8");

		HttpSession session = req.getSession();

		if(session.getAttribute("ssf") == null) {
			List<String> errors = new ArrayList<>();
			errors.add("検索条件を入力してください。");
			session.setAttribute("errors", errors);
			resp.sendRedirect("S0020.html");
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Sales> salesList = new ArrayList<>();
		List<String> errors = new ArrayList<>();

		List<String> sqlParameter = new ArrayList<>();

		//検索条件の情報を取得
		SearchSaleForm ssf = (SearchSaleForm) session.getAttribute("ssf");

		//sql文の作成
		String sql = "select sale_id, sale_date, account_id, category_id, trade_name, unit_price, sale_number, note "
				+ "from sales where 0=0";

		if(!ssf.getStart().equals("")) {
			sql = sql.concat(" and sale_date >= ?");
			sqlParameter.add(ssf.getStart());
		}

		if(!ssf.getEnd().equals("")) {
			sql = sql.concat(" and sale_date <= ?");
			sqlParameter.add(ssf.getEnd());
		}

		if(!ssf.getAccount().equals("")) {
			sql = sql.concat(" and account_id = ?");
			sqlParameter.add(ssf.getAccount());
		}


		String[] categories = ssf.getCategory();
		if(categories != null) {
			sql = sql.concat(" and category_id in(");

			for(int i = 0; i < categories.length; i++) {
				if(i == 0) {
					sql = sql.concat("?");
					sqlParameter.add(categories[i]);
				}else {
					sql = sql.concat(",?");
					sqlParameter.add(categories[i]);
				}
			}

			sql = sql.concat(")");
		}

		if(!ssf.getTradeName().equals("")) {
			sql = sql.concat(" and trade_name like ?");
			sqlParameter.add("%" + ssf.getTradeName() + "%");
		}

		if(!ssf.getNote().equals("")) {
			sql = sql.concat(" and note like ?");
			sqlParameter.add("%" + ssf.getNote() + "%");
		}

		sql = sql.concat(" order by sale_id desc");

		try{
			//データベースからデータを取得
			con = DBUtils.getConnection();

			ps = con.prepareStatement(sql);
			int count = 1;
			for(String s : sqlParameter) {
					ps.setString(count++, s);
			}

			rs = ps.executeQuery();

			//データベースから取得したデータをbeansに格納
			while(rs.next()) {
				Sales s = new Sales(rs.getInt("sale_id"),
						LocalDate.parse(rs.getString("sale_date")),
						ServletUtils.getAccountName(rs.getInt("account_id")),
						ServletUtils.getCategoryName(rs.getInt("category_id")),
						rs.getString("trade_name"), rs.getInt("unit_price"),
						rs.getInt("sale_number"), rs.getString("note"));

				salesList.add(s);
			}

			//検索結果がなかった場合にエラーをS0020で出す。
			if(salesList.isEmpty()) {
				errors.add("検索結果がありません。");
				session.setAttribute("errors", errors);
				session.setAttribute("saleRemain", "on");
				resp.sendRedirect("S0020.html");
				return;
			}

			req.setAttribute("salesList", salesList);

		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			}catch(Exception e){}
		}

		getServletContext().getRequestDispatcher("/WEB-INF/S0021.jsp")
			.forward(req, resp);

	}
}
