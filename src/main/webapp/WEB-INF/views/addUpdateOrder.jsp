<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<c:choose>
				<c:when test="${not empty requestScope.order.getId()}">
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
				<c:when test="${not empty requestScope.order.getId()}">
					<form action="./updateOrder" method="POST" role="form">
				</c:when>
				<c:otherwise>
					<form action="./addOrder" method="POST" role="form">
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

			<c:if test="${empty requestScope.order.getId()}">
				<div class="form-group">
					<label for="dishes"><fmt:message key="restaurant.dishes"
							bundle="${rb}" /></label><br /> <select name="dishes" id="dishes"
						class="form-control" multiple>
						<c:forEach items="${requestScope.dishes}" var="dish">
							<option value="${dish.getId()}">${dish.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</c:if>

			<c:if test="${not empty requestScope.order.getId()}">
				<div class="form-group">
					<label for="status"><fmt:message
							key="restaurant.order.status" bundle="${rb}" /></label> <select
						class="form-control" id="status" name="status">
						<option value="${requestScope.order.getStatus().getValue()}"><fmt:message
								key="${requestScope.order.getStatus().getLocalizedValue()}"
								bundle="${rb}" /></option>
						<c:forEach items="${statuses}" var="status">
							<option value="${status.getValue()}"><fmt:message
									key="${status.getLocalizedValue()}" bundle="${rb}" /></option>
						</c:forEach>
					</select>
				</div>
			</c:if>

			<c:if test="${not empty requestScope.order.getId()}">
				<input type="hidden" id="id_order" name="id_order"
					value="<c:out value="${requestScope.order.getId()}"/>">
			</c:if>
			<c:choose>
				<c:when test="${not empty requestScope.order.getId()}">
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