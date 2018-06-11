<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp" />
<link href="css/signin.css" rel="stylesheet">
<title>ログイン|物品売上管理システム</title>
</head>
<body>
	<div class="container">

	<jsp:include page="_errors.jsp" />


		<form class="form-signin" action="C0010.html" method="POST">
			<h2 class="form-signin-heading">物品売上管理システム</h2>

			<label for="inputEmail" class="sr-only">Email address</label>
			<input type="text" name="mail" id="inputEmail" class="form-control" placeholder="メールアドレス" value="${param.mail}" required="" autofocus="">
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="inputPassword" name="password" class="form-control" placeholder="パスワード" value="" required="">

			<button type="submit" class="btn btn-lg btn-primary btn-block">ログイン</button>
			<a href="c0045.html">パスワードを忘れた人はこちら</a>
		</form>

	</div>

	<jsp:include page="_footer.jsp" />

</body>
</html>