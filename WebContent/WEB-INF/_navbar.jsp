<%@page pageEncoding="UTF-8"%>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>
<nav class="navbar navbar-default">
	<div class="container">

		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#grobalnum" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">物品売上管理システム</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="${HTMLUtils.checkNavbar(pageContext.request.servletPath) == 'c2' ? 'active' : ''}">
				<a href="C0020.html">ダッシュボード</a></li>
				<li class="${HTMLUtils.checkNavbar(pageContext.request.servletPath) == 's1' ? 'active' : ''}">
				<a href="S0010.html">売上登録</a></li>
				<li class="${HTMLUtils.checkNavbar(pageContext.request.servletPath) == 's2' ? 'active' : ''}">
				<a href="S0020.html">売上検索</a></li>
				<li class="${HTMLUtils.checkNavbar(pageContext.request.servletPath) == 's3' ? 'active' : ''}">
				<a href="S0030.html">アカウント登録</a></li>
				<li class="${HTMLUtils.checkNavbar(pageContext.request.servletPath) == 's4' ? 'active' : ''}">
				<a href="S0040.html">アカウント検索</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="C0010.html?logout=1">ログアウト</a></li>

			</ul>
		</div><!-- /.navbar-collapse -->
	</div><!-- /.container-fluid -->
</nav>
