<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<%-- headerのinclude --%>
		<jsp:include page="_header.jsp" />
		<title>アカウント編集|物品売上管理システム</title>
	</head>
	<body>

		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<div class="container">


		<%-- errorのinclude --%>
		<jsp:include page="_errors.jsp" />

			<div class="row col-md-offset-1">
				<h1>アカウント編集</h1>
			</div>

			<div class="row col-md-offset-1">
				<form class="form-horizontal" action="S0042.html?accountId=${HTMLUtils.escapeHTML(editAccount.accountId) }" method="post">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">氏名 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="name" id="name" placeholder="氏名"
							 value="${param.name != null ? HTMLUtils.escapeHTML(param.name) : HTMLUtils.escapeHTML(editAccount.name) }">
						<p class="help-block">例：山田太郎</p></div>
					</div>

					<div class="form-group">
						<label for="kana" class="col-sm-2 control-label">ふりがな <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="kana" id="kana" placeholder="ふりがな"
							 value="${param.kana != null ? HTMLUtils.escapeHTML(param.kana) : HTMLUtils.escapeHTML(editAccount.kana) }">
						<p class="help-block">例：やまだたろう</p></div>
					</div>

					<div class="form-group">
						<label for="mail" class="col-sm-2 control-label">
							メールアドレス <span class="badge">必須</span>
						</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="mail" id="mail"
							placeholder="メールアドレス"
							value="${param.mail != null ? HTMLUtils.escapeHTML(param.mail) : HTMLUtils.escapeHTML(editAccount.mail) }">
						</div>
					</div>

					<div class="form-group">
						<label for="pass" class="col-sm-2 control-label">パスワード</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" name="password1" id="pass"
							placeholder="パスワード" value="">
						</div>
					</div>

					<div class="form-group">
						<label for="pass2" class="col-sm-2 control-label">パスワード(確認)</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" name="password2"  id="pass2"
							placeholder="パスワード(確認)" value="">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">売上登録権限 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<c:if test="${param.authority1 != null}">
								<label class="radio-inline">
									<input type="radio" name="authority1" value="0"
									${param.authority1 == 0 ? 'checked' : ''}><span> 権限なし</span>
								</label>
								<label class="radio-inline">
									<input type="radio" name="authority1" value="1"
									${param.authority1 == 1 ? 'checked' : ''}><span> 権限あり</span>
								</label>
							</c:if>

							<c:if test="${param.authority1 == null}">
								<label class="radio-inline">
									<input type="radio" name="authority1" value="0"
									${editAccount.authority == 0 ? 'checked' : editAccount.authority == 10 ? 'checked'
									: ''}><span> 権限なし</span>
								</label>
								<label class="radio-inline">
									<input type="radio" name="authority1" value="1"
									${editAccount.authority == 1 ? 'checked' : editAccount.authority == 11 ? 'checked'
									: ''}><span> 権限あり</span>
								</label>
							</c:if>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">アカウント権限 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<c:if test="${param.authority2 != null}">
								<label class="radio-inline">
									<input type="radio" name="authority2" value="0"
									${param.authority2 == 0 ? 'checked' : ''}><span> 権限なし</span>
								</label>
								<label class="radio-inline">
									<input type="radio" name="authority2" value="10"
									${param.authority2 == 10 ? 'checked' : ''}><span> 権限あり</span>
								</label>
							</c:if>

							<c:if test="${param.authority2 == null}">
								<label class="radio-inline">
									<input type="radio" name="authority2" value="0"
									${editAccount.authority == 0 ? 'checked' : editAccount.authority == 1 ? 'checked'
									: ''}><span> 権限なし</span>
								</label>
								<label class="radio-inline">
									<input type="radio" name="authority2" value="10"
									${editAccount.authority == 10 ? 'checked' : editAccount.authority == 11 ? 'checked'
									: ''}><span> 権限あり</span>
								</label>
							</c:if>
						</div>
					</div>
					<input type="hidden" name="version" value="${param.version != null ? HTMLUtils.escapeHTML(param.version)
					 : HTMLUtils.escapeHTML(editAccount.version)}">

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 更 新</button>
							<a href="S0041.html" class="btn btn-default">キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>