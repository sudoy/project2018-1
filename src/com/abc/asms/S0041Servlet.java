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
import com.abc.asms.beans.SearchAccountForm;
import com.abc.asms.utils.DBUtils;
import com.abc.asms.utils.ServletUtils;

@WebServlet("/S0041.html")
public class S0041Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//ログインチェック
		if(!ServletUtils.checkLogin(req, resp)) {
			return;
		}

		req.setCharacterEncoding("utf-8");

		HttpSession session = req.getSession();

		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> errors = new ArrayList<>();

		List<Accounts> accountList = new ArrayList<>();
		List<String> sqlParameter = new ArrayList<>();

		//アカウント権限のチェックをセット
		Accounts accounts = (Accounts)session.getAttribute("accounts");
		req.setAttribute("check", accounts.getAuthority());

		//safに何も入っていない場合は全件表示
		if(session.getAttribute("saf") == null) {
			sql = "select account_id, name, mail, password, authority from accounts order by account_id";
		}else {

			//検索条件の情報を取得
			SearchAccountForm saf = (SearchAccountForm) session.getAttribute("saf");


			//sql文の作成
			sql = "select account_id, name, mail, password, authority from accounts where 0=0";

			if(!saf.getName().equals("")) {
				sql = sql.concat(" and name like ?");
				sqlParameter.add("%" + saf.getName() + "%");
			}
			if(!saf.getMail().equals("")) {
				sql = sql.concat(" and mail = ?");
				sqlParameter.add(saf.getMail());
			}


			String[] saleAuthority = saf.getSaleAuthority().split(",", 2);
			String[] accountAuthority = saf.getAccountAuthority().split(",", 2);
			List<String> authorities = new ArrayList<>();

			//
			for(String s : saleAuthority) {
				for(String a : accountAuthority) {
					String authority = a + s;
					authorities.add(authority);
				}
			}


			if(authorities != null) {
				sql = sql.concat(" and authority in(");
				for(int i = 0; i < authorities.size(); i++) {
					if(i == 0) {
						sql = sql.concat("?");
						sqlParameter.add(authorities.get(i));
					}else {
						sql = sql.concat(",?");
						sqlParameter.add(authorities.get(i));
					}
				}

				sql = sql.concat(")");
			}

			sql = sql.concat(" order by account_id");
		}

		try {
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
				Accounts a = new Accounts(rs.getInt("account_id"), rs.getString("name"),
						rs.getString("mail"), rs.getString("password"),
						rs.getInt("authority"));
				accountList.add(a);
			}

			//検索結果がなかった場合にエラーをS0020で出す。
			if(accountList.isEmpty()) {
				errors.add("検索結果はありません。");
				session.setAttribute("accountRemain", "on");
				session.setAttribute("errors", errors);
				resp.sendRedirect("S0040.html");
				return;
			}
			req.setAttribute("accountList", accountList);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			}catch(Exception e){}
		}

		getServletContext().getRequestDispatcher("/WEB-INF/S0041.jsp")
			.forward(req, resp);
	}

}
