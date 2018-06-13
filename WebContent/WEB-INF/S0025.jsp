<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
	<head>
		<%-- headerのinclude --%>
		<jsp:include page="_header.jsp" />
		<title>売上詳細削除確認|物品売上管理システム</title>

	</head>
	<body>

		<%-- navbarのinclude --%>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<div class="row">
				<h1>売上を削除してよろしいですか?</h1>
			</div>

			<div class="row">
				<form class="form-horizontal" action="S0025.html?sale_id=${list.saleId}" method="POST">
					<div class="form-group">
						<label for="salesDate" class="col-sm-2 control-label">販売日</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="saleDate" id="salesDate" placeholder="販売日" value="${HTMLUtils.parseDate(list.saleDate) }" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="person" class="col-sm-2 control-label">担当</label>
						<div class="col-sm-5">
							<select class="form-control" name="account" id="person" readonly>
								<option value="${list.account }" selected>${list.account }</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="category" class="col-sm-2 control-label">商品カテゴリー</label>
						<div class="col-sm-5">
							<select class="form-control" name="category" id="category"  readonly>
								<option value="${list.category }" selected>${list.category }</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">商品名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="tradeName" id="name" placeholder="商品名" value="${list.tradeName} " readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="price" class="col-sm-2 control-label">単価</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="unitPrice" id="price" placeholder="単価" value="${list.unitPrice}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="count" class="col-sm-2 control-label">個数</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="saleNumber" id="count" placeholder="個数" value="${list.saleNumber}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="total" class="col-sm-2 control-label">小計</label>
						<div class="col-sm-2">
							<input type="text" class="form-control text-right" name="total" id="total" placeholder="小計" value="${HTMLUtils.sumCalc(list.unitPrice,list.saleNumber)}" readonly>
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
							<button  class="btn btn-danger"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> OK</button>
							<a href="S0022.html?sale_id=${list.saleId}" class="btn btn-default"> キャンセル</a>
						</div>
					</div>

				</form>
			</div>

		</div><!-- /container -->

		<%-- footerのinclude --%>
		<jsp:include page="_footer.jsp" />
	</body>
</html>