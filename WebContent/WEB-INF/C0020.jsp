<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.abc.asms.utils.*"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>ダッシュボード|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />
	<jsp:include page="_successes.jsp" />
	<jsp:include page="_errors.jsp" />

	<div class="container">

<div class="container col-sm-offset-1">
		<div class="row">
			<h1 class="col-sm-6 col-sm-offset-3">ダッシュボード</h1>
		</div>

	<div class="row">
		<div class="col-sm-3">

				<ul class="pagination">
					<li class="page-item"><a class="page-link"
						href="C0020.html?before=${date}"><span
							class="glyphicon glyphicon-chevron-left"></span><span
							class="glyphicon glyphicon-chevron-left"></span> 前年</a></li>
					<li class="page-item"><a class="page-link"
						href="C0020.html?back=${date}"><span
							class="glyphicon glyphicon-chevron-left"></span> 前月</a></li>
				</ul>

		</div>

		<div class="col-sm-4">
			<h2 class="font-weight-bold">${date}</h2>
		</div>

		<div class="col-sm-3">

			<ul class="pagination">
				<li class="page-item"><a class="page-link"
					href="C0020.html?next=${date}">翌月 <span
						class="glyphicon glyphicon-chevron-right"></span></a></li>
				<li class="page-item"><a class="page-link"
					href="C0020.html?next=${date}">翌年 <span
						class="glyphicon glyphicon-chevron-right"></span><span
						class="glyphicon glyphicon-chevron-right"></span></a></li>
			</ul>

		</div>

		<div class="col-sm-3">
			<div class="panel panel-default">
				<div class="panel-heading">前月(${HTMLUtils.parseMonth(lastday)})の売上合計</div>
				<div class="panel-body">
					<fmt:formatNumber value="${lastMonth}" />
					円
				</div>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="panel panel-default">
				<div class="panel-heading">今月(${HTMLUtils.parseMonth(date)})の売上合計</div>
				<div class="panel-body">
					<fmt:formatNumber value="${toMonth}" />
					円
				</div>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="panel panel-default">
				<div class="panel-heading">前月比</div>
				<div class="panel-body">
					<span
						class="${HTMLUtils.judgeRatio(toMonth, lastMonth) ? 'text-success' : 'text-danger'}">
						<c:choose>
							<c:when test="${HTMLUtils.judgeRatio(toMonth, lastMonth)}">
								<span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
							</c:when>
							<c:otherwise>
								<span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
							</c:otherwise>
						</c:choose> <fmt:formatNumber
							value="${HTMLUtils.ratioCalc(toMonth, lastMonth)}"
							pattern="##0.00%" />
					</span>
				</div>
			</div>
		</div>

		<div class="col-sm-9">
			<div class="panel panel-default">
				<div class="panel-heading">今月の${accounts.name}さんの売上</div>

				<table class="table">
					<tr>
						<th>No</th>
						<th>販売日</th>
						<th>商品カテゴリー</th>
						<th>商品名</th>
						<th>単価</th>
						<th>個数</th>
						<th>小計</th>
					</tr>
					<c:forEach var="sales" items="${list}">
						<tr>
							<th>${sales.saleId}</th>
							<td>${HTMLUtils.parseDate(sales.saleDate)}</td>
							<td>${sales.category}</td>
							<td>${sales.tradeName}</td>
							<td><fmt:formatNumber value="${sales.unitPrice}" /></td>
							<td><fmt:formatNumber value="${sales.saleNumber}" /></td>
							<td><fmt:formatNumber value="${HTMLUtils.sumCalc(sales.unitPrice, sales.saleNumber)}" /></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="5"></td>
						<th>合計</th>
						<td><fmt:formatNumber value="${toMonth}" /></td>
					</tr>
				</table>
			</div>
		</div>
		</div>
	</div>

	</div>
	<!-- /container -->

	<jsp:include page="_footer.jsp" />
</body>
</html>