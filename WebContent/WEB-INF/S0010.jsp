<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.abc.asms.utils.*"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>売上登録|物品売上管理システム</title>
</head>
<body>
<jsp:include page="_navbar.jsp" />

<div class="container">

<jsp:include page="_successes.jsp" />
<jsp:include page="_errors.jsp" />

	<div class="row col-md-offset-1">
		<h1>売上登録</h1>
	</div>

	<div class="row col-md-offset-2">
		<form class="form-horizontal" action="S0010.html" method="post">
			<div class="form-group">
				<label for="saleDate" class="col-sm-2 control-label">販売日 <span
					class="badge">必須</span></label>
				<div class="col-sm-3">
				<input type="text" class="form-control" id="saleDate"
					value="${param.saleDate != null ? HTMLUtils.escapeHTML(param.saleDate) : sales.saleDate != null ? HTMLUtils.escapeHTML(HTMLUtils.formatLocalDate(sales.saleDate)) : HTMLUtils.escapeHTML(today)}"
					name="saleDate" placeholder="販売日(半角)"><p class="help-block">例：2018/01/01</p>
				</div>
			</div>

			<div class="form-group">
				<label for="person" class="col-sm-2 control-label">担当 <span
					class="badge">必須</span></label>
				<div class="col-sm-5">
					<select class="form-control" id="person" name="account">
						<option value="">選択してください</option>
						<c:forEach var="account" items="${accountMap}">
							<option value="${account.key}"
								${param.account == "" ? "" : param.account == account.key ? 'selected' : sales.account == account.key ? 'selected' : ''}>${HTMLUtils.escapeHTML(account.value)}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">商品カテゴリー
					<span class="badge">必須</span>
				</label>
				<div class="col-sm-6">
					<c:forEach var="category" items="${categoryMap}">
						<label> <input type="radio"
							name="category" value="${category.key}"
							${param.category == category.key ? 'checked' :sales.category == category.key ? 'checked' : ''}>
							<span> ${HTMLUtils.escapeHTML(category.value)}</span>
						</label>
					</c:forEach>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">商品名 <span
					class="badge">必須</span></label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="name"
						placeholder="商品名"
						value="${param.tradeName != null ? HTMLUtils.escapeHTML(param.tradeName) : HTMLUtils.escapeHTML(sales.tradeName)}"
						name="tradeName">
				</div>
			</div>

			<div class="form-group">
				<label for="price" class="col-sm-2 control-label">単価 <span
					class="badge">必須</span></label>
				<div class="col-sm-2">
					<input type="text" class="form-control text-right" id="price"
						placeholder="単価"
						value="${param.unitPrice != null ? HTMLUtils.escapeHTML(param.unitPrice) : HTMLUtils.escapeHTML(sales.unitPrice)}"
						name="unitPrice">
				</div>
			</div>

			<div class="form-group">
				<label for="count" class="col-sm-2 control-label">個数 <span
					class="badge">必須</span></label>
				<div class="col-sm-2">
					<input type="text" class="form-control text-right" id="count"
						placeholder="個数"
						value="${param.saleNumber != null ? HTMLUtils.escapeHTML(param.saleNumber) : HTMLUtils.escapeHTML(sales.saleNumber)}"
						name="saleNumber">
				</div>
			</div>

			<div class="form-group">
				<label for="note" class="col-sm-2 control-label">備考 </label>
				<div class="col-sm-6">
					<textarea class="form-control" id="note" placeholder="備考" rows="5"
						name="note">${param.note != null ? HTMLUtils.escapeHTML(param.note) : HTMLUtils.escapeHTML(sales.note)}</textarea>
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

<jsp:include page="_footer.jsp" />
</body>
</html>