<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<%-- headerのinclude --%>
		<jsp:include page="_header.jsp" />
		<title>売上詳細編集確認|物品売上管理システム</title>

	</head>
	<body>

		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<div class="row col-md-offset-1">
				<h1>売上を編集してよろしいですか?</h1>
			</div>

			<div class="row col-md-offset-1">
				<form class="form-horizontal" action="S0024.html?saleId=${HTMLUtils.escapeHTML(saleList.saleId)}" method="post">
					<div class="form-group">
						<label for="salesDate" class="col-sm-2 control-label">販売日</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="saleDate" id="salesDate"
							placeholder="販売日" value="${HTMLUtils.formatLocalDate(saleList.saleDate)}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="person" class="col-sm-2 control-label">担当</label>
						<div class="col-sm-5">
							<select class="form-control" name="account" id="person">
								<c:forEach var="account" items="${accountMap}">
									<c:if test="${saleList.account == account.key }">
										<option value="${account.key}" selected>${HTMLUtils.escapeHTML(account.value)}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">商品カテゴリー</label>
						<div class="col-sm-5">

							<c:forEach var="category" items="${categoryMap}">
								<label>
									<input type="radio" name="category" value="${category.key}"
									${saleList.category == category.key ? 'checked' : '' }
									onclick="return false">
									${HTMLUtils.escapeHTML(category.value)}
								</label>
							</c:forEach>
							<c:forEach var="pickCategory" items="${pickCategoryMap}">
								<label>
									<input type="radio" name="category" value="${pickCategory.key}"
									${saleList.category == pickCategory.key ? 'checked' : '' }
									onclick="return false">
									${HTMLUtils.escapeHTML(pickCategory.value)}
								</label>
							</c:forEach>
						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">商品名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="tradeName" id="name" placeholder="商品名"
							 value="${HTMLUtils.escapeHTML(saleList.tradeName)}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="price" class="col-sm-2 control-label">単価</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right"name="unitPrice" id="price"
							 placeholder="単価"
							 value="<fmt:formatNumber value="${HTMLUtils.escapeHTML(saleList.unitPrice)}" />" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="count" class="col-sm-2 control-label">個数</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="saleNumber" id="count"
							 placeholder="個数"
							 value="<fmt:formatNumber value="${HTMLUtils.escapeHTML(saleList.saleNumber)}" />" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="total" class="col-sm-2 control-label">小計</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="total" id="total"
							placeholder="小計"
							value="<fmt:formatNumber value="${HTMLUtils.escapeHTML(HTMLUtils.calcSum(saleList.unitPrice,saleList.saleNumber))}" />"
							readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">備考</label>
						<div class="col-sm-5">
							<textarea class="form-control" name="note" id="note" placeholder="備考"
							rows="5" readonly>${HTMLUtils.escapeHTML(saleList.note)}</textarea>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> OK</button>
							<a href="S0023.html?saleId=${HTMLUtils.escapeHTML(saleList.saleId)}" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>
