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

		<title>売上詳細表示|物品売上管理システム</title>
	</head>
	<body>

		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<div class="row col-md-offset-1">
				<h1>売上詳細表示</h1>
			</div>

			<div class="row col-md-offset-1">
				<form class="form-horizontal" action="#" method="post">

					<div class="form-group">
						<label class="col-sm-2 control-label">販売日</label>
						<div class="col-sm-2 form-control-static">${HTMLUtils.parseDate(saleList.saleDate)}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">担当</label>
						<div class="col-sm-5 form-control-static">${saleList.account}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">商品カテゴリー</label>
						<div class="col-sm-5 form-control-static">${saleList.category}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">商品名</label>
						<div class="col-sm-5 form-control-static">${saleList.tradeName}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">単価</label>
						<div class="col-sm-2 form-control-static text-right">
						<fmt:formatNumber value="${saleList.unitPrice}" /></div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">個数</label>
						<div class="col-sm-2 form-control-static text-right">
						<fmt:formatNumber value="${saleList.saleNumber}" /></div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">小計</label>
						<div class="col-sm-2  form-control-static text-right">
							<fmt:formatNumber value="${HTMLUtils.sumCalc(saleList.unitPrice,saleList.saleNumber)}" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">備考 </label>
						<div class="col-sm-5 form-control-static">${saleList.note}</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<c:if test="${accounts.authority == 1 || accounts.authority == 11 }">
								<a href="S0023.html?saleId=${saleList.saleId }" class="btn btn-primary">
									<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 編 集
								</a>
								<a href="S0025.html?saleId=${saleList.saleId }" class="btn btn-danger">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 削 除
								</a>
							</c:if>
							<a href="S0021.html" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>