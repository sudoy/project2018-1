<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<%-- headerのinclude --%>
		<jsp:include page="_header.jsp" />
		<title>売上詳細削除確認|物品売上管理システム</title>

	</head>
	<body>

		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<div class="row col-md-offset-1">
				<h1>売上を削除してよろしいですか?</h1>
			</div>

			<div class="row col-md-offset-1">
				<form class="form-horizontal" action="S0025.html?saleId=${saleList.saleId}" method="POST">
					<div class="form-group">
						<label for="salesDate" class="col-sm-2 control-label">販売日</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="saleDate" id="salesDate"
							placeholder="販売日" value="${HTMLUtils.parseDate(saleList.saleDate) }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="person" class="col-sm-2 control-label">担当</label>
						<div class="col-sm-5">
							<select class="form-control" name="account" id="person" readonly>
								<option value="${saleList.account }" selected>${saleList.account }</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">商品カテゴリー</label>
						<div class="col-sm-5">

							<c:forEach var="category" items="${categoryMap}">
								<label class="radio-inline">
								<input type="radio" name="category" value="${category.key }"
								${saleList.category == category.value ? 'checked' : '' }
								readonly onclick="return false">
								${category.value}
								</label>
							</c:forEach>
						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">商品名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="tradeName" id="name"
							placeholder="商品名" value="${saleList.tradeName} " readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="price" class="col-sm-2 control-label">単価</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="unitPrice" id="price"
							placeholder="単価" value="<fmt:formatNumber value="${saleList.unitPrice}" />" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="count" class="col-sm-2 control-label">個数</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="saleNumber" id="count"
							placeholder="個数" value="<fmt:formatNumber value="${saleList.saleNumber}" />" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="total" class="col-sm-2 control-label">小計</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="total" id="total"
							placeholder="小計"
							value="<fmt:formatNumber value="${HTMLUtils.sumCalc(saleList.unitPrice,saleList.saleNumber)}" />"
							readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">備考</label>
						<div class="col-sm-5">
							<textarea class="form-control" name="note" id="note"
							placeholder="備考" rows="5" readonly>${saleList.note}</textarea>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit"  class="btn btn-danger">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> OK</button>
							<a href="S0022.html?saleId=${saleList.saleId}" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>