<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.abc.asms.utils.*"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>アカウント登録|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />

	<div class="container">

	<jsp:include page="_successes.jsp" />
	<jsp:include page="_errors.jsp" />

		<div class="row col-md-offset-1">
			<h1>アカウント登録</h1>
		</div>

		<div class="row col-md-offset-1">
			<form class="form-horizontal" action="S0030.html" method="post">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">氏名 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="name" placeholder="氏名"
							value="${param.name != null ? HTMLUtils.escapeHTML(param.name) : HTMLUtils.escapeHTML(entry.name)}" name="name">
					</div>
				</div>

				<div class="form-group">
					<label for="mail" class="col-sm-2 control-label">メールアドレス <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="mail"
							placeholder="メールアドレス" value="${param.mail != null ? HTMLUtils.escapeHTML(param.mail) : HTMLUtils.escapeHTML(entry.mail)}"name="mail">
					</div>
				</div>

				<div class="form-group">
					<label for="password1" class="col-sm-2 control-label">パスワード <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="password" class="form-control" id="password1"
							placeholder="パスワード" value="" name="password1">
					</div>
				</div>

				<div class="form-group">
					<label for="password2" class="col-sm-2 control-label">パスワード(確認)
						<span class="badge">必須</span>
					</label>
					<div class="col-sm-5">
						<input type="password" class="form-control" id="password2"
							placeholder="パスワード(確認)" value="" name="password2">
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">売上登録権限 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<label class="radio-inline"> <input type="radio"
							name="authority1" value="0" ${param.authority1 == 0 ? 'checked' : entry.authority == 0 || entry.authority == 10 ? 'checked' : ''}> 権限なし
						</label> <label class="radio-inline"> <input type="radio"
							name="authority1" value="1" ${param.authority1 == 1 ? 'checked' : entry.authority == 1 || entry.authority == 11 ? 'checked' : ''}> 権限あり
						</label>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">アカウント権限 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<label class="radio-inline"> <input type="radio"
							name="authority2" value="0" ${param.authority2 == 0 ? 'checked' : entry.authority == 0 || entry.authority == 1 ? 'checked' : ''}> 権限なし
						</label> <label class="radio-inline"> <input type="radio"
							name="authority2" value="10" ${param.authority2 == 10 ? 'checked' : entry.authority == 10 || entry.authority == 11 ? 'checked' : ''}> 権限あり
						</label>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3">
						<button type="submit" class="btn btn-primary" value="登 録">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 登
							録
						</button>
					</div>
				</div>

			</form>
		</div>

	</div>
	<!-- /container -->

	<jsp:include page="_footer.jsp" />
</body>
</html>