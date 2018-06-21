<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<jsp:include page="_header.jsp" />
		<title>アカウント検索|物品売上管理システム</title>
	</head>
	<body>

		<jsp:include page="_navbar.jsp" />

		<div class="container">

		<jsp:include page="_errors.jsp" />

			<div class="row col-md-offset-1">
				<h1>アカウント検索条件入力</h1>
			</div>

			<div class="row col-md-offset-1">
				<form class="form-horizontal" action="S0040.html" method="post">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">氏名 <span class="badge">部分一致</span></label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="name" name="name" placeholder="氏名" value="${HTMLUtils.escapeHTML(saf.name)}">
						</div>
					</div>

					<div class="form-group">
						<label for="mail" class="col-sm-2 control-label">メールアドレス</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="mail" name="mail" placeholder="メールアドレス" value="${HTMLUtils.escapeHTML(saf.mail)}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">売上登録権限</label>
						<div class="col-sm-5">
							<label class="radio-inline">
								<input type="radio" name="saleAuthority" value="0,1" ${saf.saleAuthority == "0,1" || saf.saleAuthority == null ? "checked" : ""}> 全て
							</label>
							<label class="radio-inline">
								<input type="radio" name="saleAuthority" value="0" ${saf.saleAuthority == "0" ? "checked" : ""}> 権限なし
							</label>
							<label class="radio-inline">
								<input type="radio" name="saleAuthority" value="1" ${saf.saleAuthority == "1" ? "checked" : ""}> 権限あり
							</label>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">アカウント権限</label>
						<div class="col-sm-5">
							<label class="radio-inline">
								<input type="radio" name="accountAuthority" value="0,1" ${saf.accountAuthority == "0,1" || saf.accountAuthority == null ? "checked" : ""}> 全て
							</label>
							<label class="radio-inline">
								<input type="radio" name="accountAuthority" value="0" ${saf.accountAuthority == "0" ? "checked" : ""}> 権限なし
							</label>
							<label class="radio-inline">
								<input type="radio" name="accountAuthority" value="1" ${saf.accountAuthority == "1" ? "checked" : ""}> 権限あり
							</label>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> 検 索</button>
							<a href="S0040.html" class="btn btn-default"> クリア</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<jsp:include page="_footer.jsp" />

	</body>
</html>