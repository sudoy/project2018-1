package com.abc.asms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.asms.utils.DBUtils;

@WebServlet("/S0046.html")
public class S0046Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		req.setCharacterEncoding("utf-8");

		req.setAttribute("User", req.getParameter("User"));

		//メールアドレス存在チェック
		if(req.getParameter("User").equals("")) {
			List<String> errors = new ArrayList<>();
			errors.add("メールアドレスが存在していません。");
			session.setAttribute("errors", errors);
		}

		//フォワード
		getServletContext().getRequestDispatcher("/WEB-INF/S0046.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		req.setCharacterEncoding("utf-8");

		//バリデーション
		List<String> errors = validate(req);
		if(errors.size() != 0) {
			session.setAttribute("errors", errors);
			req.setAttribute("User", req.getParameter("User"));

			getServletContext().getRequestDispatcher("/WEB-INF/S0046.jsp").forward(req, resp);

			return;
		}

		//処理
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try {
			con = DBUtils.getConnection();

			sql = "UPDATE accounts SET password = MD5(?) WHERE mail = ?";

			//準備
			ps = con.prepareStatement(sql);

			//データをセット
			ps.setString(1, req.getParameter("password1"));
			ps.setString(2, req.getParameter("User"));

			System.out.println(ps);

			//実行
			ps.executeUpdate();

			//成功
			List<String> successes = new ArrayList<>();
			String success = "パスワードを再設定しました。";

			successes.add(success);
			session.setAttribute("successes", successes);

			resp.sendRedirect("C0010.html");

		}catch(Exception e){
			throw new ServletException(e);

		}finally{

			try{
				DBUtils.close(ps);
				DBUtils.close(con);

			}catch(Exception e){

			}
		}

	}

	private List<String> validate(HttpServletRequest req){
		List<String> list = new ArrayList<>();

		//メールアドレス存在チェック
		if(req.getParameter("User").equals("")) {
			list.add("メールアドレスが存在していません。");
		}


		//新パスワード必須入力チェック
		if(req.getParameter("password1").equals("")) {
			list.add("パスワードを入力して下さい。");
		}

		//新パスワード長さチェック
		if(req.getParameter("password1").length() > 30) {
			list.add("パスワードが長すぎます。");
		}

		//新パスワード確認必須入力チェック
		if(req.getParameter("password2").equals("")) {
			list.add("確認用パスワードを入力して下さい。");
		}

		//新パスワード一致チェック
		if(!req.getParameter("password1").equals("") || !req.getParameter("password2").equals("")) {
			if(!req.getParameter("password1").equals(req.getParameter("password2"))) {
				list.add("新パスワードと新パスワード(確認)が一致していません。");
			}
		}


		return list;
	}
}
