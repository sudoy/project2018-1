<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

		<div class="row">
			<h1>ダッシュボード</h1>
		</div>

		<div class="col-sm-3">
			<div class="panel panel-default">
				<div class="panel-heading">前月(5月)の売上合計</div>
				<div class="panel-body">1,000,000円</div>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="panel panel-default">
				<div class="panel-heading">今月(6月)の売上合計</div>
				<div class="panel-body">1,200,000円</div>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="panel panel-default">
				<div class="panel-heading">前月比</div>
				<div class="panel-body">
					<span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>120.00%
				</div>
			</div>
		</div>

		<div class="col-sm-9">
			<div class="panel panel-default">
				<div class="panel-heading">今月のイチローさんの売上</div>

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
						<c:set var="total" value="${total + sales.unitPrice * sales.saleNumber}"/>
							<tr>
								<th>${sales.saleId}</th>
								<td>${sales.saleDate}</td>
								<td>${sales.category}</td>
								<td>${sales.tradeName}</td>
								<td>${sales.unitPrice}</td>
								<td>${sales.saleNumber}</td>
								<td>${sales.unitPrice * sales.saleNumber}</td>
							</tr>
						</c:forEach>
					<tr>
						<td colspan="5"></td>
						<th>合計</th>
						<td>${total}</td>
					</tr>
				</table>
			</div>
		</div>

	</div>
	<!-- /container -->

	<jsp:include page="_footer.jsp" />
</body>
</html>