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

		<div class="container">

		<%-- errorのinclude --%>
		<jsp:include page="_errors.jsp" />

			<div class="row col-md-offset-1">
				<h1>売上詳細編集</h1>
			</div>

			<div class="row col-md-offset-1">
				<form class="form-horizontal" action="S0023.html?saleId=${HTMLUtils.escapeHTML(saleList.saleId)}" method="POST">
					<div class="form-group">
						<label for="salesDate" class="col-sm-2 control-label">販売日
							<span class="badge">必須</span>
						</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="saleDate" id="saleDate" placeholder="販売日"
							 value="${param.saleDate != null ? HTMLUtils.escapeHTML(param.saleDate)
							 : HTMLUtils.escapeHTML(HTMLUtils.formatLocalDate(saleList.saleDate))}">
						</div>
					</div>

					<div class="form-group">
						<label for="person" class="col-sm-2 control-label">担当 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<select class="form-control" name="account" id="person">
								<option value="">選択してください</option>
								<c:if test="${param.account != null}">
									<c:forEach var="account" items="${accountMap}">
										<option value="${account.key}"
										${param.account == account.key ? 'selected' :'' }>
										${HTMLUtils.escapeHTML(account.value)}</option>
									</c:forEach>
								</c:if>

								<c:if test="${param.account == null }">
									<c:forEach var="account" items="${accountMap}">
										<option value="${account.key}"
										${saleList.account == account.key ? 'selected' :  '' }>
										${HTMLUtils.escapeHTML(account.value)}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">
							商品カテゴリー <span class="badge">必須</span>
						</label>
						<div class="col-sm-5">

							<c:if test="${param.category != null}">
								<c:forEach var="category" items="${categoryMap}">
									<label class="radio-inline">
										<input type="radio" name="category" value="${category.key}"
										${param.category == category.key ? 'checked' :  '' }>
										${HTMLUtils.escapeHTML(category.value)}
									</label>
								</c:forEach>
							</c:if>

							<c:if test="${param.category == null }">
								<c:forEach var="category" items="${categoryMap}">
									<label class="radio-inline">
										<input type="radio" name="category" value="${category.key}"
										${saleList.category == category.key ? 'checked' :  '' }>
										${HTMLUtils.escapeHTML(category.value)}
									</label>
								</c:forEach>
							</c:if>

						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">商品名 <span class="badge">必須</span></label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="tradeName" id="name" placeholder="商品名"
							value="${param.tradeName != null ? HTMLUtils.escapeHTML(param.tradeName)
							: HTMLUtils.escapeHTML(saleList.tradeName) }">
						</div>
					</div>

					<div class="form-group">
						<label for="price" class="col-sm-2 control-label">単価 <span class="badge">必須</span></label>
						<div class="col-sm-2">
							<input type="text" class="form-control  text-right" name="unitPrice"
							id="price" placeholder="単価"
							value="${param.unitPrice != null ? HTMLUtils.escapeHTML(param.unitPrice)
							: HTMLUtils.escapeHTML(saleList.unitPrice) }">
						</div>
					</div>

					<div class="form-group">
						<label for="count" class="col-sm-2 control-label">個数 <span class="badge">必須</span></label>
						<div class="col-sm-2">
							<input type="text" class="form-control  text-right" name="saleNumber"
							id="count" placeholder="個数"
							value="${param.saleNumber != null ? HTMLUtils.escapeHTML(param.saleNumber)
							: HTMLUtils.escapeHTML(saleList.saleNumber) }">
						</div>
					</div>

					<div class="form-group">
						<label for="note" class="col-sm-2 control-label">備考 </label>
						<div class="col-sm-5">
							<textarea class="form-control" name="note" id="note"
							placeholder="備考" rows="5">${param.note != null ? HTMLUtils.escapeHTML(param.note)
							 : HTMLUtils.escapeHTML(saleList.note) }</textarea>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>更 新</button>
							<a href="S0022.html?saleId=${HTMLUtils.escapeHTML(saleList.saleId)}" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>