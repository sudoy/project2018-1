package com.abc.asms.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtils {

	public static Connection getConnection() throws NamingException, SQLException {

		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) envContext.lookup("project1");

		return ds.getConnection();

	}

	public static void close(Connection con) throws SQLException {
		if (con != null) {
			con.close();
		}
	}

	public static void close(PreparedStatement ps) throws SQLException {
		if (ps != null) {
			ps.close();
		}
	}

	public static void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

}
