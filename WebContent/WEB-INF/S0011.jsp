<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>売上登録確認|物品売上管理システム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />
	<jsp:include page="_successes.jsp" />
	<jsp:include page="_errors.jsp" />

	<div class="container">

		<div class="row">
			<h1>売上を登録してよろしいですか?</h1>
		</div>

		<div class="row">
			<form class="form-horizontal" action="#" method="post">
				<div class="form-group">
					<label for="salesDate" class="col-sm-2 control-label">販売日</label>
					<div class="col-sm-2">
						<input type="text" class="form-control" id="salesDate"
							placeholder="販売日" value="2018/5/11" disabled>
					</div>
				</div>

				<div class="form-group">
					<label for="person" class="col-sm-2 control-label">担当</label>
					<div class="col-sm-5">
						<select class="form-control" id="person" disabled>
							<option value="" selected>選択してください</option>
							<option value="イチロー">イチロー</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="category" class="col-sm-2 control-label">商品カテゴリー</label>
					<div class="col-sm-5">
						<select class="form-control" id="category" disabled>
							<option value="" selected>選択してください</option>
							<option value="食料品">食料品</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">商品名</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="name"
							placeholder="商品名" value="" disabled>
					</div>
				</div>

				<div class="form-group">
					<label for="price" class="col-sm-2 control-label">単価</label>
					<div class="col-sm-2">
						<input type="text" class="form-control text-right" id="price"
							placeholder="単価" value="" disabled>
					</div>
				</div>

				<div class="form-group">
					<label for="count" class="col-sm-2 control-label">個数</label>
					<div class="col-sm-2">
						<input type="text" class="form-control text-right" id="count"
							placeholder="個数" value="" disabled>
					</div>
				</div>

				<div class="form-group">
					<label for="note" class="col-sm-2 control-label">備考</label>
					<div class="col-sm-5">
						<textarea class="form-control" id="note" placeholder="備考" rows="5"
							disabled></textarea>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3">
						<a href="s0010.html" class="btn btn-primary"><span
							class="glyphicon glyphicon-ok" aria-hidden="true"></span> OK</a> <a
							href="s0010.html" class="btn btn-default"> キャンセル</a>
					</div>
				</div>

			</form>
		</div>

	</div>
	<!-- /container -->

	<jsp:include page="_footer.jsp" />
</body>
</html>