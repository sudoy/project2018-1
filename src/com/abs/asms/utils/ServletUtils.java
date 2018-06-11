package com.abs.asms.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

	public static List<String> categoryList(HttpServletRequest req){

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;
		List<String> categoryList = new ArrayList<>();

		try {
			con = DBUtils.getConnection();

			sql = "select c.category_name, c.active_flg " +
					"from sales s " +
					"join categories c " +
					"on s.category_id = c.category_id " +
					"group by c.category_name " +
					"order by s.sale_id";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();


			while(rs.next()) {
				if(rs.getInt("c.active_flg") == 1) {
					categoryList.add(rs.getString("c.category_name"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return categoryList;
	}

	public static List<String> accountList(HttpServletRequest req){

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;
		List<String> accountList = new ArrayList<>();

		try {
			con = DBUtils.getConnection();

			sql = "select a.name " +
					"from sales s " +
					"join accounts a " +
					"on s.account_id = a.account_id " +
					"group by a.name " +
					"order by s.sale_id";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();


			while(rs.next()) {
				accountList.add(rs.getString("a.name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return accountList;
	}

	public static String pairAccount(String account) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String accountId = null;

		if(account.equals("")) {
			return null;
		}

		try {
			con = DBUtils.getConnection();

			sql = "select account_id, name from accounts where name=? order by account_id";

			ps = con.prepareStatement(sql);

			ps.setString(1, account);

			rs = ps.executeQuery();

			rs.next();

			accountId = rs.getString("account_id");


		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return accountId;
	}

	public static String pairCategory(String category) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String categoryId = null;

		if(category.equals("")) {
			return null;
		}

		try {
			con = DBUtils.getConnection();

			sql = "select category_id, category_name from categories where category_name=? order by category_id";

			ps = con.prepareStatement(sql);

			ps.setString(1, category);

			rs = ps.executeQuery();

			rs.next();

			categoryId = rs.getString("category_id");


		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return categoryId;
	}

	public static String parseAccountName(int accountId) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String name = null;

		if(accountId == 0) {
			return null;
		}

		try {
			con = DBUtils.getConnection();

			sql = "select account_id, name from accounts where account_id=? order by account_id";

			ps = con.prepareStatement(sql);

			ps.setString(1, String.valueOf(accountId));

			rs = ps.executeQuery();

			rs.next();

			name = rs.getString("name");


		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return name;

	}

	public static String parseCategoryName(int categoryId) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String name = null;

		if(categoryId == 0) {
			return null;
		}

		try {
			con = DBUtils.getConnection();

			sql = "select category_id, category_name from categories where category_id=? order by category_id";

			ps = con.prepareStatement(sql);

			ps.setString(1, String.valueOf(categoryId));

			rs = ps.executeQuery();

			rs.next();

			name = rs.getString("category_name");


		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return name;

	}
}
