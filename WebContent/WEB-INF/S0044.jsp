<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<%-- headerのinclude --%>
		<jsp:include page="_header.jsp" />
		<title>アカウント削除確認|物品売上管理システム</title>


	</head>
	<body>

		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<div class="row col-md-offset-1">
				<h1>アカウントを削除してよろしいですか?</h1>
			</div>

			<div class="row col-md-offset-1">
				<form class="form-horizontal" action="S0044.html?accountId=${HTMLUtils.escapeHTML(deleteAccount.accountId) }" method="post">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">氏名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="name" id="name" placeholder="氏名"
							value="${HTMLUtils.escapeHTML(deleteAccount.name) }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="kana" class="col-sm-2 control-label">ふりがな</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="kana" id="kana" placeholder="ふりがな"
							value="${HTMLUtils.escapeHTML(deleteAccount.kana) }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="mail" class="col-sm-2 control-label">メールアドレス</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="mail" id="mail" placeholder="メールアドレス"
							value="${HTMLUtils.escapeHTML(deleteAccount.mail) }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="pass" class="col-sm-2 control-label">パスワード</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" name="password1" id="pass" placeholder="パスワード"
							value="" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="pass2" class="col-sm-2 control-label">パスワード(確認)</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" name="password2" id="pass2"
							placeholder="パスワード(確認)"
							value="" readonly>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">売上登録権限</label>
						<div class="col-sm-5" >
							<label class="radio-inline">
								<input type="radio" name="authority1" value="0"
								${deleteAccount.authority == 0 ? 'checked'
								: deleteAccount.authority == 10 ? 'checked' : ''}
								onclick="return false"><span> 権限なし</span>
							</label>
							<label class="radio-inline">
								<input type="radio" name="authority1" value="1"
								${deleteAccount.authority == 1 ? 'checked'
								: deleteAccount.authority == 11 ? 'checked' : ''}
								onclick="return false"><span> 権限あり</span>
							</label>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">アカウント権限</label>
						<div class="col-sm-5">
							<label class="radio-inline">
								<input type="radio" name="authority2" value="0"
								${deleteAccount.authority == 0 ? 'checked'
								: deleteAccount.authority == 1 ? 'checked' : ''}
								onclick="return false"><span> 権限なし</span>
							</label>
							<label class="radio-inline">
								<input type="radio" name="authority2" value="10"
								${deleteAccount.authority == 10 ? 'checked'
								: deleteAccount.authority == 11 ? 'checked' : ''}
								onclick="return false"><span> 権限あり</span>
							</label>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-danger">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> OK</button>
							<a href="S0041.html" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>