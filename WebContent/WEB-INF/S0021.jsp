<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<link href="css/searchdropdown.css" rel="stylesheet">
<title>売上検索結果表示|物品売上管理システム</title>
</head>
<body>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<jsp:include page="_successes.jsp" />
			<jsp:include page="_errors.jsp" />

			<div class="row">
				<h1 class="col-sm-12">売上検索結果表示</h1>
			</div>

			<div class="row">
			<div class="col-sm-12">
				<table class="table">
					<thead>
					<tr>
						<th class="text-center" id="crlsale" nowrap>操作</th>
						<th class="btn-default dropdown" nowrap>
							<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								販売日
								<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'sale_date')}" aria-hidden="true"></span>
							</div>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="S0021.html?sort=sale_date">昇順</a></li>
								<li><a href="S0021.html?sort=sale_date desc">降順</a></li>
							</ul>
						</th>
						<th class="btn-default dropdown" nowrap>
							<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								担当
								<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'kana')}" aria-hidden="true"></span>
							</div>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="S0021.html?sort=kana">昇順</a></li>
								<li><a href="S0021.html?sort=kana desc">降順</a></li>
							</ul>
						</th>
						<th class="btn-default dropdown" nowrap>
							<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								商品カテゴリー
								<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'category_id')}" aria-hidden="true"></span>
							</div>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="S0021.html?sort=category_id">昇順</a></li>
								<li><a href="S0021.html?sort=c.category_id desc">降順</a></li>
							</ul>
						</th>
						<th class="btn-default dropdown" nowrap>
							<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								商品名
								<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'trade_name')}" aria-hidden="true"></span>　　　　　　　　
							</div>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="S0021.html?sort=trade_name">昇順</a></li>
								<li><a href="S0021.html?sort=trade_name desc">降順</a></li>
							</ul>
						</th>
						<th class="btn-right btn-default dropdown" nowrap>
							<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								単価
								<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'unit_price')}" aria-hidden="true"></span>
							</div>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="S0021.html?sort=unit_price">昇順</a></li>
								<li><a href="S0021.html?sort=unit_price desc">降順</a></li>
							</ul>
						</th>
						<th class="right btn-default dropdown" nowrap>
							<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span>個数
								<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'sale_number')}" aria-hidden="true"></span></span>
							</div>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="S0021.html?sort=sale_number">昇順</a></li>
								<li><a href="S0021.html?sort=sale_number desc">降順</a></li>
							</ul>
						</th>
						<th class="btn-right btn-default dropdown" nowrap>
							<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								小計
								<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'sum')}" aria-hidden="true"></span>
							</div>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="S0021.html?sort=sum">昇順</a></li>
								<li><a href="S0021.html?sort=sum desc">降順</a></li>
							</ul>
						</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="sale" items="${salesList}">
					<tr>
						<td><a href="S0022.html?saleId=${HTMLUtils.escapeHTML(sale.saleId)}" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 詳 細</a></td>
						<td>${HTMLUtils.escapeHTML(HTMLUtils.formatLocalDate(sale.saleDate))}</td>
						<td>${HTMLUtils.escapeHTML(sale.account)}</td>
						<td>${HTMLUtils.escapeHTML(sale.category)}</td>
						<td>${HTMLUtils.escapeHTML(sale.tradeName)}</td>
						<td class="text-right"><fmt:formatNumber value="${HTMLUtils.escapeHTML(sale.unitPrice)}" /></td>
						<td class="text-right"><fmt:formatNumber value="${HTMLUtils.escapeHTML(sale.saleNumber)}" /></td>
						<td class="text-right"><fmt:formatNumber value="${HTMLUtils.calcSum(sale.unitPrice, sale.saleNumber)}" /></td>
					</tr>
					</c:forEach>
					</tbody>
				</table>

				<c:if test="${pageDisplay != 0}">
					<div style="text-align:center;">
						<nav aria-label="Page navigation ">
							<ul class="pagination  pagination-lg">
								<c:if test="${param.pageNumber != null ? param.pageNumber != 0 : false}">
								<li>
									<a href="S0021.html?sort=${param.sort}&pageNumber=0" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>
								</c:if>
								<c:forEach var="page" begin="0" end="${pageDisplay}">
									<li class="${HTMLUtils.currentPage(param.pageNumber, page)}"><a href="S0021.html?sort=${param.sort}&pageNumber=${page}">${page + 1}</a></li>
								</c:forEach>
								<c:if test="${param.pageNumber != pageDisplay}">
								<li>
									<a href="S0021.html?sort=${param.sort}&pageNumber=${pageDisplay}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
								</c:if>
							</ul>
						</nav>
					</div>
				</c:if>
			</div>
			</div>

		</div><!-- /container -->

		<jsp:include page="_footer.jsp" />

</body>
</html>