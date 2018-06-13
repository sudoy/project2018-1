package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.abc.asms.beans.SearchForm;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0021.html")
public class S0021Servlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		HttpSession session = req.getSession();

		//ログインチェック
//		if(session.getAttribute("accounts") == null) {
//			List<String> errors = new ArrayList<>();
//			errors.add("ログインしてください。");
//			session.setAttribute("errors", errors);
//			resp.sendRedirect("C0010.html");
//			return;
//		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Sales> salesList = new ArrayList<>();
		List<String> errors = new ArrayList<>();

		SearchForm searchForm = (SearchForm) session.getAttribute("searchForm");
		String sql = searchForm.getSql();
		Map<String, String> sqlParameter = searchForm.getSqlParameter();

		try{

			con = DBUtils.getConnection();

			ps = con.prepareStatement(sql);
			int count = 1;
			for(Map.Entry<String, String> entry : sqlParameter.entrySet()) {
				if(entry.getKey().equals("tradeName") || entry.getKey().equals("note")) {
					ps.setString(count, "%" + entry.getValue() + "%");
					count++;
				}else {
					ps.setString(count, entry.getValue());
					count++;
				}
			}

			rs = ps.executeQuery();

			while(rs.next()) {
				Sales s = new Sales(rs.getInt("sale_id"),
						LocalDate.parse(rs.getString("sale_date")),
						ServletUtils.parseAccountName(rs.getInt("account_id")),
						ServletUtils.parseCategoryName(rs.getInt("category_id")),
						rs.getString("trade_name"), rs.getInt("unit_price"),
						rs.getInt("sale_number"), rs.getString("note"));

				salesList.add(s);
			}

			if(salesList.isEmpty()) {
				errors.add("検索結果がありません。");
				session.setAttribute("errors", errors);
				resp.sendRedirect("S0020.html");
				return;
			}

			req.setAttribute("salesList", salesList);

		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}
		}

		getServletContext().getRequestDispatcher("/WEB-INF/S0021.jsp")
			.forward(req, resp);

	}
}
