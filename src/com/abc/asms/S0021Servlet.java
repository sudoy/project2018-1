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

		//1ページの表示件数
		int parPage = 10;

		//検索条件の情報を取得
		SearchSaleForm ssf = (SearchSaleForm) session.getAttribute("ssf");

		//sql文の作成
		String sql = "select s.sale_id, s.sale_date, a.name, c.category_id, c.category_name, s.trade_name, s.unit_price, s.sale_number, s.note, "
				+ "s.unit_price * s.sale_number as sum from sales s join accounts a on s.account_id = a.account_id join categories c on s.category_id = c.category_id where 0=0";

		//件数取得用sql文
		String recordCounter = "select count(s.sale_id) as record from sales s join accounts a on s.account_id = a.account_id join categories c on s.category_id = c.category_id where 0=0";

		if(!ssf.getStart().equals("")) {
			sql = sql.concat(" and s.sale_date >= ?");
			recordCounter = recordCounter.concat(" and s.sale_date >= ?");
			sqlParameter.add(ssf.getStart());
		}

		if(!ssf.getEnd().equals("")) {
			sql = sql.concat(" and s.sale_date <= ?");
			recordCounter = recordCounter.concat(" and s.sale_date <= ?");
			sqlParameter.add(ssf.getEnd());
		}

		if(!ssf.getAccount().equals("")) {
			sql = sql.concat(" and a.account_id = ?");
			recordCounter = recordCounter.concat(" and a.account_id = ?");
			sqlParameter.add(ssf.getAccount());
		}

		if(!ssf.getKana().equals("")) {
			sql = sql.concat(" and a.kana like ?");
			recordCounter = recordCounter.concat(" and a.kana like ?");
			sqlParameter.add("%" + ssf.getKana() + "%");
		}

		String[] categories = ssf.getCategory();
		if(categories != null) {
			sql = sql.concat(" and s.category_id in(");
			recordCounter = recordCounter.concat(" and s.category_id in(");

			for(int i = 0; i < categories.length; i++) {
				if(i == 0) {
					sql = sql.concat("?");
					recordCounter = recordCounter.concat("?");
					sqlParameter.add(categories[i]);
				}else {
					sql = sql.concat(",?");
					recordCounter = recordCounter.concat(",?");
					sqlParameter.add(categories[i]);
				}
			}

			sql = sql.concat(")");
			recordCounter = recordCounter.concat(")");
		}

		if(!ssf.getTradeName().equals("")) {
			sql = sql.concat(" and s.trade_name like ?");
			recordCounter = recordCounter.concat(" and s.trade_name like ?");
			sqlParameter.add("%" + ssf.getTradeName() + "%");
		}

		if(!ssf.getNote().equals("")) {
			sql = sql.concat(" and s.note like ?");
			recordCounter = recordCounter.concat(" and s.note like ?");
			sqlParameter.add("%" + ssf.getNote() + "%");
		}

		if(req.getParameter("sort") == null || req.getParameter("sort").equals("")) {
			sql = sql.concat(" order by s.sale_date desc, a.kana, s.unit_price * s.sale_number, sale_id");
		}else {
			sql = sql.concat(" order by " + req.getParameter("sort"));
		}

		if(req.getParameter("pageNumber") != null) {
			sql= sql.concat(" limit " + (parPage * Integer.parseInt(req.getParameter("pageNumber"))) + ", " + parPage);
		}else {
			sql= sql.concat(" limit 0, " + parPage);

		}

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
				Sales s = new Sales(rs.getInt("s.sale_id"),
						LocalDate.parse(rs.getString("s.sale_date")),
						rs.getString("a.name"),
						rs.getString("c.category_name"),
						rs.getString("s.trade_name"), rs.getInt("s.unit_price"),
						rs.getInt("s.sale_number"), rs.getString("s.note"));
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

			DBUtils.close(rs);
			DBUtils.close(ps);

			ps = con.prepareStatement(recordCounter);
			count = 1;
			for(String s : sqlParameter) {
				ps.setString(count++, s);
			}
			rs = ps.executeQuery();

			rs.next();

			int countRecord = rs.getInt("record");

			int pageDisplay = countRecord / parPage;
			if(countRecord % parPage == 0) {
				pageDisplay -= 1;
			}

			req.setAttribute("pageDisplay", pageDisplay);

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
