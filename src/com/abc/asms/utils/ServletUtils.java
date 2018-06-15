package com.abc.asms.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.Accounts;

public class ServletUtils {


	public static Map<Integer, String> getCategoryMap(HttpServletRequest req){

		Map<Integer, String> categoryMap = new HashMap<Integer, String>();

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "select category_id, category_name, active_flg "
					+ "from categories "
					+ "group by category_id, category_name, active_flg "
					+ "order by category_id";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();


			while(rs.next()) {
				if(rs.getInt("active_flg") == 1) {
					categoryMap.put(rs.getInt("category_id"), rs.getString("category_name"));
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

		return categoryMap;
	}

	public static Map<Integer, String> getAccountMap(HttpServletRequest req){

		Map<Integer, String> accountMap = new HashMap<Integer, String>();

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "select account_id, name "
					+ "from accounts "
					+ "group by account_id, name "
					+ "order by account_id";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();


			while(rs.next()) {
				accountMap.put(rs.getInt("account_id"), rs.getString("name"));
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

		return accountMap;
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

	// アカウントテーブルのバリデーションチェック用、== falseならエラー表示
	public static boolean matchAccount(String account) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();
			sql = "SELECT name FROM accounts WHERE account_id = ? ORDER BY account_id";

			ps = con.prepareStatement(sql);
			ps.setString(1, account);

			rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	// カテゴリーテーブルのバリデーションチェック用、== falseならエラー表示
	public static boolean matchCategory(String category) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();
			sql = "SELECT category_name FROM categories WHERE active_flg = 1 AND category_id = ? ORDER BY category_id";

			ps = con.prepareStatement(sql);
			ps.setString(1, category);

			rs = ps.executeQuery();

			if(rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}


	// S0011専用
	public static String registerSId() {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String registerId = null;

		try {
			con = DBUtils.getConnection();

			sql = "SELECT sale_id FROM sales ORDER BY sale_id DESC LIMIT 1";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			rs.next();

			registerId = rs.getString("sale_id");

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
		return registerId;
	}

	// S0031専用
	public static String registerAId() {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String registerAId = null;

		try {
			con = DBUtils.getConnection();

			sql = "SELECT account_id FROM accounts ORDER BY account_id DESC LIMIT 1";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			rs.next();

			registerAId = rs.getString("account_id");

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
		return registerAId;
	}

	// メールアドレス重複チェック == trueで弾く
	public static boolean overlapMail(String mail) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "SELECT mail FROM accounts WHERE mail = ? ORDER BY mail";

			ps = con.prepareStatement(sql);
			ps.setString(1, mail);
			rs = ps.executeQuery();

			if(rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}


	// ログインチェック
	public static boolean checkLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();

		if(session.getAttribute("accounts") == null) {
			List<String> errors = new ArrayList<>();
			errors.add("ログインしてください。");
			session.setAttribute("errors", errors);
			resp.sendRedirect("C0010.html");
			return false;
		} else {
			return true;
		}
	}

	// 売上権限チェック
	public static boolean checkSales(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();

		Accounts authority = (Accounts)session.getAttribute("accounts");
		if(!(authority.getAuthority() == 1) && !(authority.getAuthority() == 11)) {
			List<String> errors = new ArrayList<>();
			errors.add("不正なアクセスです。");
			session.setAttribute("errors", errors);
			resp.sendRedirect("C0020.html");
			return false;
		} else {
			return true;
		}
	}

	// アカウント権限チェック
	public static boolean checkAccounts(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();

		Accounts authority = (Accounts)session.getAttribute("accounts");
		if(!(authority.getAuthority() == 10) && !(authority.getAuthority() == 11)) {
			List<String> errors = new ArrayList<>();
			errors.add("不正なアクセスです。");
			session.setAttribute("errors", errors);
			resp.sendRedirect("C0020.html");
			return false;
		} else {
			return true;
		}
	}
}
