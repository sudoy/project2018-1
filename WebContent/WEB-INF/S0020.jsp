<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<jsp:include page="_header.jsp" />
		<title>売上検索条件入力|物品売上管理システム</title>
	</head>
	<body>

		<jsp:include page="_navbar.jsp" />

		<div class="container">

		<jsp:include page="_errors.jsp" />

				<h1>売上検索条件入力</h1>

			<div class="row">
				<form class="form-horizontal" action="S0020.html" method="post">
					<div class="form-group">
						<label for="salesDate" class="col-sm-2 control-label">販売日</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" id="salesDate" name="start" placeholder="販売日(検索開始日)" value="${param.start}">
						</div>

						<div class="form-control-static col-sm-1 text-center">～</div>

						<div class="col-sm-2">
							<input type="text" class="form-control" name="end" placeholder="販売日(検索終了日)" value="${param.end}">
						</div>
					</div>

					<div class="form-group">
						<label for="person" class="col-sm-2 control-label">担当</label>
						<div class="col-sm-5">
							<select class="form-control" name="account" id="person">
								<option value="">選択してください</option>
								<c:forEach var="account" items="${accountMap}">
									<option value="${account.key}" ${param.account == account.key ? 'selected' : ''}>${account.value}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="category" class="col-sm-2 control-label">商品カテゴリー</label>
						<div class="col-sm-5">
							<select class="form-control" name="category" id="category">
								<option value="">選択してください</option>
								<c:forEach var="category" items="${categoryMap}">
									<option value="${category.key}" ${param.category == category.key ? 'selected' : ''}>${category.value}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">商品名 <span class="badge">部分一致</span></label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="name" name="tradeName" placeholder="商品名" value="${param.tradeName}">
						</div>
					</div>

					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">備考 <span class="badge">部分一致</span></label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="note" name="note" placeholder="備考" value="${param.note}">
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> 検 索</button>
							<a href="S0020.html" class="btn btn-default"> クリア</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<jsp:include page="_footer.jsp" />

	</body>
</html>