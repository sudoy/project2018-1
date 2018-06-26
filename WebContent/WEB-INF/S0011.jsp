<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.abc.asms.utils.*"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>売上登録確認|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />

	<div class="container">

		<div class="row col-md-offset-1">
			<h1>売上を登録してよろしいですか?</h1>
		</div>

		<div class="row col-md-offset-1">
			<form class="form-horizontal" action="S0011.html" method="post">
				<div class="form-group">
					<label for="saleDate" class="col-sm-2 control-label">販売日 </label>
					<div class="col-sm-2">
						<input type="text" class="form-control" id="saleDate"
							value="${HTMLUtils.escapeHTML(HTMLUtils.formatLocalDate(sales.saleDate))}" name="saleDate"
							readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="person" class="col-sm-2 control-label">担当 </label>
					<div class="col-sm-5">

							<c:forEach var="account" items="${accountMap}">
								<c:if test="${sales.account == account.key}">
									<input type="text" class="form-control" value="${HTMLUtils.escapeHTML(account.value)}" readonly>
									<input type="hidden" name="account" value="${account.key}" >
								</c:if>
							</c:forEach>

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">商品カテゴリー
					</label>
					<div class="col-sm-5">
						<c:forEach var="category" items="${categoryMap}">
							<label> <input type="radio"
								name="category" value="${category.key}"
								${sales.category == category.key ? 'checked' : ''}
								onclick="return false">
								<span> ${HTMLUtils.escapeHTML(category.value)}</span>
							</label>
						</c:forEach>
					</div>
				</div>

				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">商品名 </label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="name"
							placeholder="商品名" value="${HTMLUtils.escapeHTML(sales.tradeName)}" name="tradeName"
							readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="price" class="col-sm-2 control-label">単価 </label>
					<div class="col-sm-2">
						<input type="text" class="form-control text-right" id="price"
							placeholder="単価"
							value="<fmt:formatNumber value="${HTMLUtils.escapeHTML(sales.unitPrice)}" />"
							name="unitPrice" readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="count" class="col-sm-2 control-label">個数 </label>
					<div class="col-sm-2">
						<input type="text" class="form-control text-right" id="count"
							placeholder="個数"
							value="<fmt:formatNumber value="${HTMLUtils.escapeHTML(sales.saleNumber)}" />"
							name="saleNumber" readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="count" class="col-sm-2 control-label">小計 </label>
					<div class="col-sm-2">
						<input type="text" class="form-control text-right" id="total"
							placeholder="小計"
							value="<fmt:formatNumber value="${HTMLUtils.escapeHTML(HTMLUtils.calcSum(sales.unitPrice, sales.saleNumber))}" />"
							readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="note" class="col-sm-2 control-label">備考 </label>
					<div class="col-sm-5">
						<textarea class="form-control" id="note" placeholder="備考" rows="5"
							name="note" readonly>${HTMLUtils.escapeHTML(sales.note)}</textarea>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3">
						<button type="submit" name="OK" class="btn btn-primary"
							value="O K">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> O
							K
						</button>
						<a href="S0010.html" class="btn btn-default">キャンセル</a>
					</div>
				</div>

			</form>
		</div>

	</div>
	<!-- /container -->

	<jsp:include page="_footer.jsp" />
</body>
</html>