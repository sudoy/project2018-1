<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${errors != null}">
	<div class="row">
		<div class="col">
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<h4 class="alert-heading h5 font-weight-bold"><span class="oi oi-pin"></span> エラーが発生しました！</h4>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<ul>
					<c:forEach var="error" items="${errors}">
						<li>${error}</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<% session.setAttribute("errors", null); %>
</c:if>
