<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>アカウント登録|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />
	<jsp:include page="_successes.jsp" />
	<jsp:include page="_errors.jsp" />

	<div class="container">

		<div class="row">
			<h1>アカウント登録</h1>
		</div>

		<div class="row">
			<form class="form-horizontal" action="#" method="post">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">氏名 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="name" placeholder="氏名"
							value="">
					</div>
				</div>

				<div class="form-group">
					<label for="mail" class="col-sm-2 control-label">メールアドレス <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="mail"
							placeholder="メールアドレス" value="">
					</div>
				</div>

				<div class="form-group">
					<label for="pass" class="col-sm-2 control-label">パスワード <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="pass"
							placeholder="パスワード" value="">
					</div>
				</div>

				<div class="form-group">
					<label for="pass2" class="col-sm-2 control-label">パスワード(確認)
						<span class="badge">必須</span>
					</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="pass2"
							placeholder="パスワード(確認)" value="">
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">売上登録権限 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<label class="radio-inline"> <input type="radio"
							name="salesRadio" value="権限なし"> 権限なし
						</label> <label class="radio-inline"> <input type="radio"
							name="salesRadio" value="権限あり"> 権限あり
						</label>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">アカウント権限 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<label class="radio-inline"> <input type="radio"
							name="acountRadio" value="権限なし"> 権限なし
						</label> <label class="radio-inline"> <input type="radio"
							name="acountRadio" value="権限あり"> 権限あり
						</label>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3">
						<a href="s0031.html" class="btn btn-primary"><span
							class="glyphicon glyphicon-ok" aria-hidden="true"></span> 登 録</a>
					</div>
				</div>

			</form>
		</div>

	</div>
	<!-- /container -->

	<jsp:include page="_footer.jsp" />
</body>
</html>