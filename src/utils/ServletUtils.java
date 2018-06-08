package utils;

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

			ps.setString(1, req.getParameter("c.category_name"));

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
}
