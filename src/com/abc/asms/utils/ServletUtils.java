package com.abc.asms.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.beans.Accounts;
import com.abc.asms.beans.Sales;

public class ServletUtils {

	public static Map<Integer, String> getCategoryMap(HttpServletRequest req) {

		Map<Integer, String> categoryMap = new HashMap<Integer, String>();

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "select category_id, category_name "
					+ "from categories "
					+ "where active_flg = 1 "
					+ "order by category_id";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				categoryMap.put(rs.getInt("category_id"), rs.getString("category_name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return categoryMap;
	}

	public static Map<Integer, String> getAllCategoryMap(HttpServletRequest req) {

		Map<Integer, String> categoryMap = new HashMap<Integer, String>();

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "select category_id, category_name "
					+ "from categories "
					+ "order by category_id";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				categoryMap.put(rs.getInt("category_id"), rs.getString("category_name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return categoryMap;
	}

	public static Map<Integer, String> getPickCategoryMap(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Map<Integer, String> pickCategoryMap = new HashMap<Integer, String>();

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "select category_id, category_name "
					+ "from categories "
					+ "where category_id = ? AND active_flg = 0";

			ps = con.prepareStatement(sql);
			Sales categoryId = (Sales)session.getAttribute("saleList");
			ps.setString(1, categoryId.getCategory());
			rs = ps.executeQuery();

			rs.next();
			pickCategoryMap.put(rs.getInt("category_id"), rs.getString("category_name"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pickCategoryMap;
	}

	public static Map<Integer, String> getAccountMap(HttpServletRequest req) {

		Map<Integer, String> accountMap = new LinkedHashMap<Integer, String>();

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "select account_id, name "
					+ "from accounts "
					+ "order by kana";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				accountMap.put(rs.getInt("account_id"), rs.getString("name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return accountMap;
	}

	public static String getAccountName(int accountId) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String name = null;

		if (accountId == 0) {
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
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return name;
	}

	public static String getCategoryName(int categoryId) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String name = null;

		if (categoryId == 0) {
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
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return name;
	}

	// C0020用 Getパラメータの有無確認
	public static String checkParameter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (req.getParameter("b") != null) {
			return req.getParameter("b");
		} else if (req.getParameter("n") != null) {
			return req.getParameter("n");
		} else if (req.getParameter("by") != null) {
			return req.getParameter("by");
		} else if (req.getParameter("ny") != null) {
			return req.getParameter("ny");
		}
		return null;
	}

	// C0020用	全体の今月売上合計
		public static long getTotalOfThisMonthForAll(LocalDate first, LocalDate last) {
			Connection con = null;
			PreparedStatement ps = null;
			String sql = null;
			ResultSet rs = null;

			long total = 0;

			try {
				con = DBUtils.getConnection();

				sql = "SELECT unit_price, sale_number  FROM sales " +
						"WHERE sale_date BETWEEN ? AND ? " +
						"ORDER BY sale_date";

				ps = con.prepareStatement(sql);
				ps.setString(1, first.toString());
				ps.setString(2, last.toString());
				rs = ps.executeQuery();

				while (rs.next()) {
					total += rs.getLong("unit_price") * rs.getLong("sale_number");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DBUtils.close(rs);
					DBUtils.close(ps);
					DBUtils.close(con);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return total;
		}

	// C0020用	全体の前月売上合計
	public static long getTotalOfLastMonthForAll(LocalDate first, LocalDate last) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		long total = 0;

		try {
			con = DBUtils.getConnection();

			sql = "SELECT unit_price, sale_number  FROM sales " +
					"WHERE sale_date BETWEEN ? AND ? " +
					"ORDER BY sale_date";

			ps = con.prepareStatement(sql);
			ps.setString(1, first.withDayOfMonth(1).minusMonths(1).toString());
			ps.setString(2, last.withDayOfMonth(1).minusDays(1).toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				total += rs.getLong("unit_price") * rs.getLong("sale_number");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return total;
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
			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
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

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// カテゴリーテーブルのバリデーションチェック用、特殊確認
		public static boolean matchPickCategory(String category) {
			Connection con = null;
			PreparedStatement ps = null;
			String sql = null;
			ResultSet rs = null;

			try {
				con = DBUtils.getConnection();

				sql = "SELECT category_name FROM categories WHERE active_flg = 0 AND category_id = ? ORDER BY category_id";

				ps = con.prepareStatement(sql);
				ps.setString(1, category);
				rs = ps.executeQuery();

				return rs.next();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DBUtils.close(rs);
					DBUtils.close(ps);
					DBUtils.close(con);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return false;
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

			return rs.next();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// S0011専用
	public static String registerSaleId() {
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
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return registerId;
	}

	// S0031専用
	public static String registerAccountId() {
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
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return registerAId;
	}

	// ログインチェック
	public static boolean checkLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();

		if (session.getAttribute("accounts") == null) {
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

		Accounts authority = (Accounts) session.getAttribute("accounts");
		if (!(authority.getAuthority() == 1) && !(authority.getAuthority() == 11)) {
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

		Accounts authority = (Accounts) session.getAttribute("accounts");
		if (!(authority.getAuthority() == 10) && !(authority.getAuthority() == 11)) {
			List<String> errors = new ArrayList<>();
			errors.add("不正なアクセスです。");
			session.setAttribute("errors", errors);
			resp.sendRedirect("C0020.html");
			return false;
		} else {
			return true;
		}
	}

	// C0010でメールアドレスが登録されているかチェック
	public static boolean checkMail(String account) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "SELECT name FROM accounts WHERE mail = ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, account);
			rs = ps.executeQuery();
			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// C0010でパスワードのチェック
	public static boolean checkPassword(String account) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			sql = "SELECT name FROM accounts WHERE password = MD5(?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, account);
			rs = ps.executeQuery();
			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean notFoundData(int divideNumber, String id) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DBUtils.getConnection();

			if(divideNumber == 0) {
				sql = "SELECT sale_id FROM sales WHERE sale_id = ?";
			} else if(divideNumber == 1){
				sql = "SELECT account_id FROM accounts WHERE account_id = ?";
			}

			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	//追加仕様No01用
	public static List<Long> getTotalOfThisYear(int year) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		List<Long> total = new ArrayList<>();

		try {
			con = DBUtils.getConnection();

			sql = "SELECT MONTH(sale_date), SUM(unit_price * sale_number) AS sum " +
					"FROM sales " +
					"WHERE sale_date LIKE ? " +
					"GROUP BY MONTH(sale_date)" +
					"ORDER BY MONTH(sale_date)" ;

			ps = con.prepareStatement(sql);

			ps.setString(1, year + "%");

			rs = ps.executeQuery();

			boolean finish = rs.next();
			LocalDate today = LocalDate.now();


			for(int i = 1; i < 13; i++) {


				if(finish == true && i == rs.getInt("MONTH(sale_date)")) {
					total.add(rs.getLong("sum") / 10000);
					if(finish) {
						finish = rs.next();
					}
				}else {

					if(today.getYear() > year) {
						total.add(Long.parseLong("0"));
					}else if(today.getYear() >= year && today.getMonthValue() >= i) {
						total.add(Long.parseLong("0"));
					}else {
						total.add(null);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return total;
	}

}
