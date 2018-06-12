<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp" />
<title>売上検索結果表示|物品売上管理システム</title>
</head>
<body>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<jsp:include page="_errors.jsp" />

			<div class="row">
				<h1>売上検索結果表示</h1>
			</div>

			<div class="row">

				<table class="table">
					<tr>
						<th class="col-sm-1">操作</th>
						<th>No</th>
						<th>販売日</th>
						<th>担当</th>
						<th class="col-sm-2">商品カテゴリー</th>
						<th class="col-sm-4">商品名</th>
						<th>単価</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<c:forEach var="sale" items="${salesList}">
					<tr>
						<td><a href="S0022.html?sale_id=${sale.saleId}" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 詳 細</a></td>
						<td class="text-right">${sale.saleId}</td>
						<td>${HTMLUtils.parseDate(sale.saleDate)}</td>
						<td>${sale.account}</td>
						<td>${sale.category}</td>
						<td>${sale.tradeName}</td>
						<td class="text-right"><fmt:formatNumber value="${sale.unitPrice}" pattern="#,##0" /></td>
						<td class="text-right"><fmt:formatNumber value="${sale.saleNumber}" pattern="#,##0" /></td>
						<td class="text-right"><fmt:formatNumber value="${HTMLUtils.sumCalc(sale.unitPrice, sale.saleNumber)}" pattern="#,##0" /></td>
					</tr>
					</c:forEach>
				</table>

			</div>

		</div><!-- /container -->

		<jsp:include page="_footer.jsp" />

		<% session.setAttribute("salesList", null); %>

</body>
</html>