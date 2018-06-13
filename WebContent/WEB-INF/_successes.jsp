<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
		<c:if test="${successes != null}">
			<div class="alert alert-success alert-dismissible fade in" role="alert"  style="margin-top: 21px;">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4>完了しました！</h4>
				<ul>
					<c:forEach var="list" items="${successes}">
						<li>${list}</li>
					</c:forEach>
				</ul>
			</div>
			<% session.setAttribute("successes", null); %>
			<% session.setAttribute("sales", null); %>
		</c:if>
	</div>
</div><!-- row -->
