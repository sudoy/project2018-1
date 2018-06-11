package com.abc.asms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/S0021.html")
public class S0021Servlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

//		Connection con = null;
//		PreparedStatement ps = null;
//		String sql = null;
//		ResultSet rs = null;
//		List<Sales> salesList = new ArrayList<>();
//
//		try{
//
//			con = DBUtils.getConnection();
//
//			sql = "select * from sales where 0=0";
//
//			if(!req.getParameter("start").equals("")) {
//				sql = sql.concat(" and sale_date >= '" + req.getParameter("start") + "'");
//			}
//			if(!req.getParameter("end").equals("")) {
//				sql = sql.concat(" and sale_date <= '" + req.getParameter("end") + "'");
//			}
//
//			if(!req.getParameter("account").equals("")) {
//				sql = sql.concat(" and account_id = '" + ServletUtils.pairAccount(req.getParameter("account")) + "'");
//			}
//
//			if(!req.getParameter("category").equals("")) {
//				sql = sql.concat(" and category_id = '" + ServletUtils.pairCategory(req.getParameter("category")) + "'");
//			}
//
//			if(!req.getParameter("tradeName").equals("")) {
//				sql = sql.concat(" and trade_name like '%" + req.getParameter("tradeName") + "%'");
//			}
//			if(!req.getParameter("note").equals("")) {
//				sql = sql.concat(" and note like '%" + req.getParameter("note") + "%'");
//			}
//
//			sql = sql.concat(" order by sale_id desc");
//
//			System.out.println(sql);
//			ps = con.prepareStatement(sql);
//
//			rs = ps.executeQuery();
//
//			while(rs.next()) {
//				Sales s = new Sales(rs.getInt("sale_id"), rs.getDate("sale_date"),
//						ServletUtils.parseAccountName(rs.getInt("account_id")),
//						ServletUtils.parseCategoryName(rs.getInt("category_id")),
//						rs.getString("trade_name"), rs.getInt("unit_price"),
//						rs.getInt("sale_number"), rs.getString("note"));
//
//				salesList.add(s);
//			}
//
//			req.setAttribute("salesList", salesList);
//
//		}catch(Exception e){
//			throw new ServletException(e);
//		}finally{
//			try{
//				DBUtils.close(con);
//				DBUtils.close(ps);
//				DBUtils.close(rs);
//			}catch(Exception e){}
//		}
//
		getServletContext().getRequestDispatcher("/WEB-INF/S0021.jsp")
			.forward(req, resp);

	}
}
