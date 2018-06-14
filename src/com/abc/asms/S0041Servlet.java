package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.Accounts;
import com.abc.asms.beans.SearchForm;
import com.abc.asms.utils.DBUtils;

@WebServlet("/S0041.html")
public class S0041Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		HttpSession session = req.getSession();

		Accounts accounts = (Accounts)session.getAttribute("accounts");

		req.setAttribute("check", accounts.getAuthority());

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> errors = new ArrayList<>();

		SearchForm searchForm = (SearchForm) session.getAttribute("searchForm");
		String sql = searchForm.getSql();
		List<String> sqlParameter = searchForm.getSqlParameter();
		List<Accounts> accountList = new ArrayList<>();

		try {

			con = DBUtils.getConnection();

			ps = con.prepareStatement(sql);
			int count = 1;
			for(String p : sqlParameter) {
				ps.setString(count++, p);
			}
			rs = ps.executeQuery();

			while(rs.next()) {
				Accounts a = new Accounts(rs.getInt("account_id"), rs.getString("name"),
						rs.getString("mail"), rs.getString("password"),
						rs.getInt("authority"));
				accountList.add(a);
			}

			if(accountList.isEmpty()) {
				errors.add("検索結果はありません。");
				session.setAttribute("errors", errors);
				resp.sendRedirect("S0040.html");
				return;
			}

			req.setAttribute("accountList", accountList);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally{
			try{
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}
		}

		getServletContext().getRequestDispatcher("/WEB-INF/S0041.jsp")
			.forward(req, resp);
	}
}
