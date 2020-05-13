<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="restaurant.auth" bundle="${rb}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./login" method="POST" role="form">

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
					<label for="email"><fmt:message key="restaurant.email"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="email" name="email"
						placeholder="<fmt:message key="restaurant.email" bundle="${rb}"/>"
						value="<c:out value="${requestScope.loginUser.getEmail()}" />" />
				</div>
				<div class="form-group">
					<label for="password"><fmt:message key="restaurant.pass"
							bundle="${rb}" /></label> <input type="password" class="form-control"
						id="password" name="password"
						placeholder="<fmt:message key="restaurant.pass" bundle="${rb}"/>" />
				</div>
				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="restaurant.login" bundle="${rb}" />
				</button>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>