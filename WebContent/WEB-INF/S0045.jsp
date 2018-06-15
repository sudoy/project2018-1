<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<jsp:include page="_header.jsp" />
		<link href="css/signin.css" rel="stylesheet">
		<title>パスワード再設定|物品売上管理システム</title>
	</head>
	<body>

		<div class="container">

			<jsp:include page="_successes.jsp" />
			<jsp:include page="_errors.jsp" />

			<form class="form-signin" action="S0045.html" method="post">
				<h2 class="form-signin-heading">物品売上管理システム</h2>
				<h3>パスワード再設定</h3>

				<label for="inputEmail" class="sr-only">Email address</label>
				<input type="email" id="inputEmail" name="mail" class="form-control" placeholder="メールアドレス" value="${param.mail}">

				<button type="submit" class="btn btn-lg btn-primary btn-block">メール送信</button>

			</form>

		</div>

		<jsp:include page="_footer.jsp" />
	</body>
</html>