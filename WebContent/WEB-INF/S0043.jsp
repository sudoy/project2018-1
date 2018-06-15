<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="ja">
	<head>

		<%-- headerのinclude --%>
		<jsp:include page="_header.jsp" />
		<title>アカウント編集確認|物品売上管理システム</title>

	</head>
	<body>

		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<div class="row">
				<h1>アカウントを編集してよろしいですか?</h1>
			</div>

			<div class="row">
				<form class="form-horizontal" action="S0043.html?accountId=${editAccount.accountId }" method="POST">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">氏名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="name" id="name" placeholder="氏名"
							value="${editAccount.name }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="mail" class="col-sm-2 control-label">メールアドレス</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="mail" id="mail" placeholder="メールアドレス"
							 value="${editAccount.mail }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="pass" class="col-sm-2 control-label">パスワード</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="password1" id="pass" placeholder="パスワード"
							 value="${editAccount.password }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="pass2" class="col-sm-2 control-label">パスワード(確認)</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="password2" id="pass2" placeholder="パスワード(確認)"
							 value="${editAccount.password }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">売上登録権限</label>
						<div class="col-sm-5" >
							<label class="radio-inline">
								<input type="radio" name="authority1" value="0"
								${editAccount.authority == 0 ? 'checked' : editAccount.authority == 10 ? 'checked'
								: ''}
								readonly onclick="return false"> 権限なし
							</label>
							<label class="radio-inline">
								<input type="radio" name="authority1" value="1"
								${editAccount.authority == 1 ? 'checked' : editAccount.authority == 11 ? 'checked'
								: ''}
								readonly onclick="return false"> 権限あり
							</label>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">アカウント権限</label>
						<div class="col-sm-5">
							<label class="radio-inline">
								<input type="radio" name="authority2" value="0"
								${editAccount.authority == 0 ? 'checked' : editAccount.authority == 1 ? 'checked'
								: ''}
								 readonly onclick="return false"> 権限なし
							</label>
							<label class="radio-inline">
								<input type="radio" name="authority2" value="10"
								${editAccount.authority == 10 ? 'checked' : editAccount.authority == 11 ? 'checked'
								: ''}
								 readonly onclick="return false"> 権限あり
							</label>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> OK</button>
							<a href="S0042.html?accountId=${editAccount.accountId }" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>