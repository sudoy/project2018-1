<%@page pageEncoding="UTF-8"%>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default">
	<div class="container">

		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#grobalnum" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="C0020.html">物品売上管理システム</a>
		</div>


		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li ${fn:contains(pageContext.request.servletPath,"C002") ? 'class=active' : ''}>
				<a href="C0020.html">ダッシュボード</a></li>
				<c:if test="${accounts.authority == 1 || accounts.authority == 11}">
					<li ${fn:contains(pageContext.request.servletPath,"S001") ? 'class=active' : ''}>
					<a href="S0010.html">売上登録</a></li>
				</c:if>
				<li ${fn:contains(pageContext.request.servletPath,"S002") ? 'class=active' : ''}>
				<a href="S0020.html">売上検索</a></li>
				<c:if test="${accounts.authority == 10 || accounts.authority == 11}">
					<li ${fn:contains(pageContext.request.servletPath,"S003") ? 'class=active' : ''}>
					<a href="S0030.html">アカウント登録</a></li>
				</c:if>
				<li ${fn:contains(pageContext.request.servletPath,"S004") ? 'class=active' : ''}>
				<a href="S0040.html">アカウント検索</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="C0030.html">ログアウト</a></li>

			</ul>
		</div>
	</div>
</nav>
