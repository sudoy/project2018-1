<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<%-- headerのinclude --%>
		<jsp:include page="_header.jsp" />
		<title>売上詳細編集|物品売上管理システム</title>

	</head>
	<body>

		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<%-- errorのinclude --%>
		<jsp:include page="_errors.jsp" />

		<div class="container">

			<div class="row">
				<h1>売上詳細編集</h1>
			</div>

			<div class="row">
				<form class="form-horizontal" action="S0024.html?sale_id=${param.sale_id }" method="POST">
					<div class="form-group">
						<label for="salesDate" class="col-sm-2 control-label">販売日 <span class="badge">必須</span></label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="sale_date" id="salesDate" placeholder="販売日"
							 value="${param.sale_date != null ? HTMLUtils.parseDate(param.saleDate) : HTMLUtils.parseDate(list.saleDate)}">
						</div>
					</div>

					<div class="form-group">
						<label for="person" class="col-sm-2 control-label">担当 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<select class="form-control" name="account" id="person">
								<option value="">選択してください</option>

								<c:forEach var="account" items="${accountList}">
									<c:if test="${list.staffName eq account || param.account eq account}">
										<option value="${account}" selected>${account}</option>
									</c:if>
								 	<c:if test="${list.staffName ne account && param.account ne account}">
										<option value="${account}">${account}</option>
									</c:if>

								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="category" class="col-sm-2 control-label">商品カテゴリー <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<select class="form-control" name="category" id="category">
								<option value="">選択してください</option>
								<c:forEach var="category" items="${categoryList}">
									<c:if test="${list.categoryName eq category || param.category eq category}">
										<option value="${category}" selected>${category}</option>
									</c:if>
								 	<c:if test="${list.categoryName ne category && param.category ne category}">
										<option value="${category}">${category}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">商品名 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="trade_name" id="name" placeholder="商品名"
							value="${param.trade_name != null ? param.trade_name : list.tradeName }">
						</div>
					</div>

					<div class="form-group">
						<label for="price" class="col-sm-2 control-label">単価 <span class="badge">必須</span></label>
						<div class="col-sm-2">
							<input type="text" class="form-control  text-right" name="unit_price" id="price" placeholder="単価"
							value="${param.unit_price != null ? param.unit_price : list.unitPrice }">
						</div>
					</div>

					<div class="form-group">
						<label for="count" class="col-sm-2 control-label">個数 <span class="badge">必須</span></label>
						<div class="col-sm-2">
							<input type="text" class="form-control  text-right" name="sale_number" id="count" placeholder="個数"
							value="${param.sale_number != null ? param.sale_number : list.saleNumber }">
						</div>
					</div>

					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">備考 </label>
						<div class="col-sm-5">
							<textarea class="form-control" name="note" id="note" placeholder="備考" rows="5">${param.note != null ? param.note : list.note }
							</textarea>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary" name="submit" value=""><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 更 新</button>
							<a href="S0022.html?sale_id=${param.sale_id }" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>