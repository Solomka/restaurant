<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<c:choose>
				<c:when test="${not empty requestScope.category.getId()}">
					<fmt:message key="restaurant.update" bundle="${rb}" />
				</c:when>
				<c:otherwise>
					<fmt:message key="restaurant.add" bundle="${rb}" />
				</c:otherwise>
			</c:choose>
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<c:choose>
				<c:when test="${not empty requestScope.category.getId()}">
					<form action="./updateCategory" method="POST" role="form">
				</c:when>
				<c:otherwise>
					<form action="./addCategory" method="POST" role="form">
				</c:otherwise>
			</c:choose>

			<c:if test="${not empty requestScope.errors}">
				<div class="alert alert-danger">
					<c:forEach items="${requestScope.errors}" var="error">
						<p>
							<fmt:message key="${error}" bundle="${rb}" />
						</p>
					</c:forEach>
				</div>
			</c:if>

			<div class="form-group">
				<label for="name"><fmt:message
						key="restaurant.category.name" bundle="${rb}" /></label> <input
					type="text" class="form-control" id="name" name="name"
					placeholder="<fmt:message key="restaurant.category.name"
							bundle="${rb}" />"
					value="<c:out value="${requestScope.category.getName()}" />" />
			</div>

			<c:if test="${not empty requestScope.category.getId()}">
				<input type="hidden" id="id_category" name="id_category"
					value="<c:out value="${requestScope.category.getId()}"/>">
			</c:if>
			<c:choose>
				<c:when test="${not empty requestScope.category.getId()}">
					<button type="submit" class="btn btn-default" id="submitButton">
						<fmt:message key="restaurant.update" bundle="${rb}" />
					</button>
				</c:when>
				<c:otherwise>
					<button type="submit" class="btn btn-default" id="submitButton">
						<fmt:message key="restaurant.add" bundle="${rb}" />
					</button>
				</c:otherwise>
			</c:choose>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>