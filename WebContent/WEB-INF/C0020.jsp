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
	<div class="container">
		<jsp:include page="_successes.jsp" />
		<jsp:include page="_errors.jsp" />
		<div class="row">
			<h1 class="col-sm-12 text-center">ダッシュボード</h1>
		</div>

		<div class="row">
			<div class="col-sm-4">

				<ul class="pagination">
					<li class="page-item"><a class="page-link"
						href="C0020.html?by=${HTMLUtils.escapeHTML(date)}"><span
							class="glyphicon glyphicon-chevron-left"></span><span
							class="glyphicon glyphicon-chevron-left"></span> 前年</a></li>
					<li class="page-item"><a class="page-link"
						href="C0020.html?b=${HTMLUtils.escapeHTML(date)}"><span
							class="glyphicon glyphicon-chevron-left"></span> 前月</a></li>
				</ul>

			</div>

			<div class="col-sm-4 text-center">
				<h2 class="font-weight-bold">
					<strong>${HTMLUtils.escapeHTML(HTMLUtils.formatCenterMonth(date))}</strong>
				</h2>
			</div>

			<div class="col-sm-4 text-right">
				<ul class="pagination">
					<li class="page-item"><a class="page-link"
						href="C0020.html?n=${HTMLUtils.escapeHTML(date)}">翌月 <span
							class="glyphicon glyphicon-chevron-right"></span></a></li>
					<li class="page-item"><a class="page-link"
						href="C0020.html?ny=${HTMLUtils.escapeHTML(date)}">翌年 <span
							class="glyphicon glyphicon-chevron-right"></span><span
							class="glyphicon glyphicon-chevron-right"></span></a></li>
				</ul>

			</div>

			<div class="col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading">前月(${HTMLUtils.escapeHTML(HTMLUtils.formatMonth(lastDate))})の売上合計</div>
					<div class="panel-body text-center">
						<span class="h4"><fmt:formatNumber
								value="${HTMLUtils.escapeHTML(lastMonth)}" /> 円</span>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading">今月(${HTMLUtils.escapeHTML(HTMLUtils.formatMonth(date))})の売上合計</div>
					<div class="panel-body text-center">
						<span class="h4"><fmt:formatNumber
								value="${HTMLUtils.escapeHTML(thisMonth)}" /> 円</span>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading">前月比</div>
					<div class="panel-body text-center">
						<c:if
							test="${lastMonth != 0 && HTMLUtils.calcRatio(thisMonth, lastMonth) <= 5}">
							<span class="h4"> <fmt:formatNumber
									value="${HTMLUtils.calcRatio(thisMonth, lastMonth)}"
									pattern="#0.00%" /></span>
							( <c:if test="${HTMLUtils.judgeRatio(thisMonth, lastMonth)}">
								<span class="text-success">+ <fmt:formatNumber
										value="${HTMLUtils.calcFluctuationRatio(thisMonth, lastMonth)}"
										pattern="#0.00%" /></span>
							</c:if>
							<c:if
								test="${HTMLUtils.judgeRatio(thisMonth, lastMonth) == false}">
								<span class="text-danger"><fmt:formatNumber
										value="${HTMLUtils.calcFluctuationRatio(thisMonth, lastMonth)}"
										pattern="#0.00%" /></span>
							</c:if> )
						</c:if>
						<c:if test="${lastMonth == 0}">
							<span class="h4"> ‐ </span>
						</c:if>
						<c:if
							test="${lastMonth != 0 && HTMLUtils.calcRatio(thisMonth, lastMonth) > 5}">
							<span class="h4"> ∞ </span>
						</c:if>
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div id="cont"></div>
			</div>

			<div class="col-sm-12">
				<div class="panel panel-default table-responsive">
					<div class="panel-heading">
						<h4>
							今月の <strong>${HTMLUtils.escapeHTML(accounts.name)}</strong> さんの売上
						</h4>
					</div>

					<table class="table">
						<tr>
							<th class="col-sm-1 text-center">販売日</th>
							<th class="col-sm-2">商品カテゴリー</th>
							<th class="col-sm-6">商品名</th>
							<th class="col-sm-1 text-right">単価</th>
							<th class="col-sm-1 text-right">個数</th>
							<th class="col-sm-1 text-right">小計</th>
						</tr>
						<c:forEach var="sales" items="${list}">
							<tr>
								<td>${HTMLUtils.escapeHTML(HTMLUtils.formatLocalDate(sales.saleDate))}</td>
								<td>${HTMLUtils.escapeHTML(sales.category)}</td>
								<td>${HTMLUtils.escapeHTML(sales.tradeName)}</td>
								<td class="text-right"><fmt:formatNumber
										value="${HTMLUtils.escapeHTML(sales.unitPrice)}" /></td>
								<td class="text-right"><fmt:formatNumber
										value="${HTMLUtils.escapeHTML(sales.saleNumber)}" /></td>
								<td class="text-right"><fmt:formatNumber
										value="${HTMLUtils.escapeHTML(HTMLUtils.calcSum(sales.unitPrice, sales.saleNumber))}" /></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="4"></td>
							<th>合計</th>
							<td class="text-right"><fmt:formatNumber
									value="${HTMLUtils.escapeHTML(myTotal)}" /></td>
						</tr>
					</table>
				</div>
			</div>
		</div>

	</div>
	<!-- /container -->

	<jsp:include page="_footer.jsp" />
	<script src=""></script>
	<script src="js/highchart.js"></script>
	<script src="js/exporting.js"></script>

	<script>
	Highcharts.chart('cont', {
		chart: {
			type: 'line'
		},

		exporting : {
			enabled: false
		},
		credits  : {
			enabled: false,
		},

		title: {
			text: '月別売上合計推移'
		},

		xAxis: {
			categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']

		},

		yAxis: {

			title: {
				text: ''
			},
			labels:{
				formatter: function() {
				return Highcharts.numberFormat(this.value, 0, ',', ',') +'万円' },
			}

		},

		tooltip: {
			valueSuffix: '万円'
		},

		plotOptions: {
			line: {
				dataLabels: {
					enabled: true,
					formatter: function() {
						return Highcharts.numberFormat(this.y, 0, '', ',')
					}
				},
				enableMouseTracking: true
			}
		},

		series: [{
			name: ${year} - 1 + '年',
			data: ${beforeTotal},

			}, {
			name: ${year} + '年',
			data: ${total},
			dataLabels: {
				y: 25,
			}
		}]
	});
	</script>

</body>
</html>