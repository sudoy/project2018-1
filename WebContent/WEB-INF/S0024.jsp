<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

			<div class="row">
				<h1>売上を編集してよろしいですか?</h1>
			</div>

			<div class="row">
				<form class="form-horizontal" action="S0024.html?sale_id=${param.sale_id}" method="post">
					<div class="form-group">
						<label for="salesDate" class="col-sm-2 control-label">販売日</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="sale_date" id="salesDate" placeholder="販売日" value="${list.saleDate}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="person" class="col-sm-2 control-label">担当</label>
						<div class="col-sm-5">
							<select class="form-control" name="account" id="person" readonly>
								<option value="" disabled>選択してください</option>
								<c:forEach var="account" items="${accountList}">
									<c:if test="${list.staffName eq account || param.staff_name eq account}">
										<option value="${account}" selected>${account}</option>
									</c:if>
								 	<c:if test="${list.staffName ne account && param.staff_name ne account}">
										<option value="${account}" disabled>${account}</option>
									</c:if>

								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="category" class="col-sm-2 control-label">商品カテゴリー</label>
						<div class="col-sm-5">
							<select class="form-control" name="category" id="category" readonly>
								<option value="" disabled>選択してください</option>
								<c:forEach var="category" items="${categoryList}">
									<c:if test="${list.categoryName eq category || param.category_name eq category}">
										<option value="${category}" selected>${category}</option>
									</c:if>
								 	<c:if test="${list.categoryName ne category && param.category_name ne category}">
										<option value="${category}" disabled>${category}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">商品名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="trade_name" id="name" placeholder="商品名" value="${list.tradeName}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="price" class="col-sm-2 control-label">単価</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right"name="unit_price" id="price" placeholder="単価" value="${list.unitPrice}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="count" class="col-sm-2 control-label">個数</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="sale_number" id="count" placeholder="個数" value="${list.saleNumber}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="total" class="col-sm-2 control-label">小計</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="total" id="total" placeholder="小計" value="${list.total}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">備考</label>
						<div class="col-sm-5">
							<textarea class="form-control" name="note" id="note" placeholder="備考" rows="5" readonly>${list.note}</textarea>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary" name="submit" value="OK"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> OK</button>
							<a href="S0023.html?sale_id=${param.sale_id }" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>