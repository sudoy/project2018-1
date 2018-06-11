<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>売上登録|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />
	<jsp:include page="_successes.jsp" />
	<jsp:include page="_errors.jsp" />

	<div class="container">

		<div class="row">
			<h1>売上登録</h1>
		</div>

		<div class="row">
			<form class="form-horizontal" action="S0010.html" method="post">
				<div class="form-group">
					<label for="saleDate" class="col-sm-2 control-label">販売日 <span
						class="badge">必須</span></label>
					<div class="col-sm-2">
						<input type="text" class="form-control" id="saleDate"
							 value="${param.saleDate == null ? today : param.saleDate}" name="saleDate">
					</div>
				</div>

				<div class="form-group">
					<label for="person" class="col-sm-2 control-label">担当 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<select class="form-control" id="person" name="account">
							<option value="">選択してください</option>
							<c:forEach var="account" items="${accountList}">
								<option value="${account}"
									${param.account == account ? 'selected' : ''}>${account}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="category" class="col-sm-2 control-label">商品カテゴリー
						<span class="badge">必須</span>
					</label>
					<div class="col-sm-5">
						<select class="form-control" name="category" id="category">
							<option value="">選択してください</option>
							<c:forEach var="category" items="${categoryList}">
								<option value="${category}"
									${param.category == category ? 'selected' : ''}>${category}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">商品名 <span
						class="badge">必須</span></label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="name"
							placeholder="商品名" value="${param.tradeName}" name="tradeName">
					</div>
				</div>

				<div class="form-group">
					<label for="price" class="col-sm-2 control-label">単価 <span
						class="badge">必須</span></label>
					<div class="col-sm-2">
						<input type="text" class="form-control text-right" id="price"
							placeholder="単価" value="${param.unitPrice}" name="unitPrice">
					</div>
				</div>

				<div class="form-group">
					<label for="count" class="col-sm-2 control-label">個数 <span
						class="badge">必須</span></label>
					<div class="col-sm-2">
						<input type="text" class="form-control text-right" id="count"
							placeholder="個数" value="${param.saleNumber}" name="saleNumber">
					</div>
				</div>

				<div class="form-group">
					<label for="note" class="col-sm-2 control-label">備考 </label>
					<div class="col-sm-5">
						<textarea class="form-control" id="note" placeholder="備考" rows="5"
							name="note">${param.note}</textarea>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3">
						<input type="submit" class="btn btn-primary" value="登 録">
						<!-- <button class="btn btn-primary"><i class="glyphicon glyphicon-pencil"></i>登 録</button> -->
					</div>
				</div>

			</form>
		</div>

	</div>

	<jsp:include page="_footer.jsp" />
</body>
</html>