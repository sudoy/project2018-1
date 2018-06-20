<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<link href="css/signin.css" rel="stylesheet">
<title>ログイン|物品売上管理システム</title>
</head>
<body>
	<div class="container">

	<jsp:include page="_successes.jsp" />
	<jsp:include page="_errors.jsp" />


		<form class="form-signin" action="C0010.html" method="POST">
			<h2 class="form-signin-heading">物品売上管理システム</h2>

			<label for="inputEmail" class="sr-only">Email address</label>
			<input type="text" name="mail" id="inputEmail" class="form-control" placeholder="メールアドレス" value="${HTMLUtils.escapeHTML(param.mail)}">
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="inputPassword" name="password" class="form-control" placeholder="パスワード" value="">

			<button type="submit" class="btn btn-lg btn-primary btn-block">ログイン</button>
			<a href="S0045.html">パスワードを忘れた人はこちら</a>
		</form>

	</div>

	<jsp:include page="_footer.jsp" />

</body>
</html>