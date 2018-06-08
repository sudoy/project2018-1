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

			<div class="row">
				<h1>売上詳細表示</h1>
			</div>

			<div class="row">
				<form class="form-horizontal" action="#" method="post">
					<div class="form-group">
						<label class="col-sm-2 control-label">販売日</label>
						<div class="col-sm-2 form-control-static">${saleList.sale_date}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">担当</label>
						<div class="col-sm-5 form-control-static">${saleList.name}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">商品カテゴリー</label>
						<div class="col-sm-5 form-control-static">${saleList.category_name}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">商品名</label>
						<div class="col-sm-5 form-control-static">${saleList.trade_name}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">単価</label>
						<div class="col-sm-2 form-control-static text-right">${saleList.price}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">個数</label>
						<div class="col-sm-2 form-control-static text-right">${saleList.number}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">小計</label>
						<div class="col-sm-2  form-control-static text-right">${saleList.total}</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">備考 </label>
						<div class="col-sm-5 form-control-static">${saleList.note}</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3">
							<a href="S0023.html?sale_id=${param.sale_id }" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 編 集</a>
							<a href="S0025.html?sale_id=${param.sale_id }" class="btn btn-danger"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 削 除</a>
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