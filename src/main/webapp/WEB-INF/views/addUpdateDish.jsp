<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<c:choose>
				<c:when test="${not empty requestScope.dish.getId()}">
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
				<c:when test="${not empty requestScope.dish.getId()}">
					<form action="./updateDish" method="POST" role="form">
				</c:when>
				<c:otherwise>
					<form action="./addDish" method="POST" role="form">
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
				<label for="name"><fmt:message key="restaurant.name"
						bundle="${rb}" /></label> <input type="text" class="form-control"
					id="name" name="name"
					placeholder="<fmt:message key="restaurant.name"
							bundle="${rb}" />"
					value="<c:out value="${requestScope.dish.getName()}" />" />
			</div>
			<div class="form-group">
				<label for="description"><fmt:message
						key="restaurant.description" bundle="${rb}" /></label> <input type="text"
					class="form-control" id="description" name="description"
					placeholder="<fmt:message key="restaurant.description" bundle="${rb}"/>"
					value="<c:out value="${requestScope.dish.getDescription()}" />" />
			</div>
			<div class="form-group">
				<label for="weight"><fmt:message key="restaurant.weight"
						bundle="${rb}" /></label> <input type="number" step="0.01"
					class="form-control" id="weight" name="weight"
					placeholder="<fmt:message key="restaurant.weight" bundle="${rb}"/>"
					value="<c:out value="${requestScope.dish.getWeight()}" />" />
			</div>
			<div class="form-group">
				<label for="cost"><fmt:message key="restaurant.cost"
						bundle="${rb}" /></label> <input type="number" step="0.01"
					class="form-control" id="cost" name="cost"
					placeholder="<fmt:message key="restaurant.cost" bundle="${rb}"/>"
					value="<c:out value="${requestScope.dish.getCost()}" />" />
			</div>
			<div class="form-group">
				<label for="category"><fmt:message key="restaurant.category"
						bundle="${rb}" /></label> <select class="form-control" id="category"
					name="category">
					<c:if test="${not empty requestScope.dish}">
						<option value="${requestScope.dish.getCategory().getId()}">
							${requestScope.dish.getCategory().getName()}</option>
					</c:if>
					<c:forEach items="${categories}" var="category">
						<option value="${category.getId()}">${category.getName()}</option>
					</c:forEach>
				</select>
			</div>

			<c:if test="${not empty requestScope.dish.getId()}">
				<input type="hidden" id="id_dish" name="id_dish"
					value="<c:out value="${requestScope.dish.getId()}"/>">
			</c:if>
			<c:choose>
				<c:when test="${not empty requestScope.dish.getId()}">
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