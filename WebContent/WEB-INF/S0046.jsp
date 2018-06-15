<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<%-- headerのinclude --%>
		<jsp:include page="_header.jsp" />

		<link href="css/signin.css" rel="stylesheet">

		<title>新パスワード入力|物品売上管理システム</title>

	</head>
	<body>

		<%-- errorのinclude --%>
		<jsp:include page="_errors.jsp" />

		<div class="container">



			<form class="form-signin" action="S0046.html?user=${user}" method="POST">
				<h2 class="form-signin-heading">物品売上管理システム</h2>
				<h3>新パスワード入力</h3>

				<label for="inputPassword" class="sr-only">Password</label>
					<input type="password" name="password1" id="inputPassword"
					class="form-control" placeholder="新パスワード" value="">
				<label for="Password" class="sr-only">Password</label>
					<input type="password" name="password2" id="Password"
					class="form-control" placeholder="新パスワード確認" value="">

				<button type="submit" class="btn btn-lg btn-primary btn-block">変更</button>

			</form>

		</div>


		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>