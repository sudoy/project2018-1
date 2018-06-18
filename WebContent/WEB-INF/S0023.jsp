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

		<div class="container">
		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<%-- errorのinclude --%>
		<jsp:include page="_errors.jsp" />

			<div class="row col-md-offset-1">
				<h1>売上詳細編集</h1>
			</div>

			<div class="row col-md-offset-1">
				<form class="form-horizontal" action="S0023.html?saleId=${saleList.saleId}" method="POST">
					<div class="form-group">
						<label for="salesDate" class="col-sm-2 control-label">販売日
							<span class="badge">必須</span>
						</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="saleDate" id="saleDate" placeholder="販売日"
							 value="${param.saleDate != null ? param.saleDate : HTMLUtils.parseDate(saleList.saleDate)}">
						</div>
					</div>

					<div class="form-group">
						<label for="person" class="col-sm-2 control-label">担当 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<select class="form-control" name="account" id="person">

								<c:if test="${param.account != null}">
									<c:forEach var="account" items="${accountMap}">
										<option value="${account.key}"
										${param.account == account.key ? 'selected' :'' }>${account.value}</option>
									</c:forEach>
								</c:if>

								<c:if test="${param.account == null }">
									<c:forEach var="account" items="${accountMap}">
										<option value="${account.key}"
										${saleList.account == account.value ? 'selected' :  '' }>${account.value}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="category" class="col-sm-2 control-label">
							商品カテゴリー <span class="badge">必須</span>
						</label>
						<div class="col-sm-5">

							<c:if test="${param.category != null}">
								<c:forEach var="category" items="${categoryMap}">
									<label class="radio-inline">
										<input type="radio" name="category" value="${category.key}"
										${param.category == category.key ? 'checked' :  '' }>${category.value}
									</label>
								</c:forEach>
							</c:if>

							<c:if test="${param.category == null }">
								<c:forEach var="category" items="${categoryMap}">
									<label class="radio-inline">
										<input type="radio" name="category" value="${category.key}"
										${saleList.category == category.value ? 'checked' :  '' }>${category.value}
									</label>
								</c:forEach>
							</c:if>

						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">商品名 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="tradeName" id="name" placeholder="商品名"
							value="${param.tradeName != null ? param.tradeName : saleList.tradeName }">
						</div>
					</div>

					<div class="form-group">
						<label for="price" class="col-sm-2 control-label">単価 <span class="badge">必須</span></label>
						<div class="col-sm-2">
							<input type="text" class="form-control  text-right" name="unitPrice"
							id="price" placeholder="単価"
							value="${param.unitPrice != null ? param.unitPrice : saleList.unitPrice }">
						</div>
					</div>

					<div class="form-group">
						<label for="count" class="col-sm-2 control-label">個数 <span class="badge">必須</span></label>
						<div class="col-sm-2">
							<input type="text" class="form-control  text-right" name="saleNumber"
							id="count" placeholder="個数"
							value="${param.saleNumber != null ? param.saleNumber : saleList.saleNumber }">
						</div>
					</div>

					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">備考 </label>
						<div class="col-sm-5">
							<textarea class="form-control" name="note" id="note"
							placeholder="備考" rows="5">${param.note != null ? param.note : saleList.note }</textarea>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>更 新</button>
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