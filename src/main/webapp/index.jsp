<%@include file="WEB-INF/views/header.jsp"%>
<div class="container-fluid" align="center">

 	<div class="row-fluid" align="center">
		<c:if test="${not empty param.success}">
			<div class="alert alert-success">
				<fmt:message key="${param.success}" bundle="${rb}" />
			</div>
		</c:if>
	</div>
	<div class="row-fluid">
		<c:if test="${not empty param.error}">
			<div class="alert alert-danger">
				<fmt:message key="${param.error}" bundle="${rb}" />
			</div>
		</c:if>
	</div>

	<div class="row-fluid">
		<h2>
			<fmt:message key="restaurant.greeting" bundle="${rb}" />
		</h2>
	</div>

</div>
<%@include file="WEB-INF/views/footer.jsp"%>
