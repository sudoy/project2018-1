<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<div class="row">
		<div class="col-sm-offset-1 col-sm-10">
			<c:if test="${errors != null}">
				<div class="alert alert-danger alert-dismissible fade in" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4>エラーが発生しました！</h4>
					<ul>
						<c:forEach var="list" items="${errors}">
							<li>${list}</li>
						</c:forEach>
					</ul>
				</div>
				<% session.setAttribute("errors", null); %>
			</c:if>
		</div>
	</div><!-- row -->
