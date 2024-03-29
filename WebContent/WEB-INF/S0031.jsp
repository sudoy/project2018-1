<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.abc.asms.utils.*"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>アカウント登録確認|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />

	<div class="container">

		<div class="row col-md-offset-1">
			<h1>アカウントを登録してよろしいですか?</h1>
		</div>

		<div class="row col-md-offset-1">
			<form class="form-horizontal" action="S0031.html" method="post">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">氏名 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="name" placeholder="氏名"
							value="${HTMLUtils.escapeHTML(entry.name)}" name="name" readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="kana" class="col-sm-2 control-label">ふりがな <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="kana" placeholder="ふりがな"
							value="${HTMLUtils.escapeHTML(entry.kana)}" name="kana" readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="mail" class="col-sm-2 control-label">メールアドレス <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="mail"
							placeholder="メールアドレス" value="${HTMLUtils.escapeHTML(entry.mail)}" name="mail" readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="password1" class="col-sm-2 control-label">パスワード
						<span class="badge">必須</span>
					</label>
					<div class="col-sm-5">
						<input type="password" class="form-control" id="password1"
							placeholder="パスワード" value="${HTMLUtils.escapeHTML(entry.password)}" name="password1"
							readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="password2" class="col-sm-2 control-label">パスワード(確認)
						<span class="badge">必須</span>
					</label>
					<div class="col-sm-5">
						<input type="password" class="form-control" id="password2"
							placeholder="パスワード(確認)" value="${HTMLUtils.escapeHTML(entry.password)}" readonly>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">売上登録権限 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<label class="radio-inline"> <input type="radio"
							name="authority1" value="0"
							${entry.authority == 0 || entry.authority == 10 ? 'checked' : ''}
							onclick="return false"><span> 権限なし</span>
						</label> <label class="radio-inline"> <input type="radio"
							name="authority1" value="1"
							${entry.authority == 1 || entry.authority == 11 ? 'checked' : ''}
							onclick="return false"><span> 権限あり</span>
						</label>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">アカウント権限 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<label class="radio-inline"> <input type="radio"
							name="authority2" value="0"
							${entry.authority == 0 || entry.authority == 1 ? 'checked' : ''}
							onclick="return false"><span> 権限なし</span>
						</label> <label class="radio-inline"> <input type="radio"
							name="authority2" value="10"
							${entry.authority == 10 || entry.authority == 11 ? 'checked' : ''}
							onclick="return false"><span> 権限あり</span>
						</label>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3">
						<button type="submit" name="OK" class="btn btn-primary"
							value="O K">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> O
							K
						</button>
						<a href="S0030.html" class="btn btn-default">キャンセル</a>
					</div>
				</div>

			</form>
		</div>

	</div>
	<!-- /container -->

	<jsp:include page="_footer.jsp" />
</body>
</html>