<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="restaurant.add" bundle="${rb}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./addUser" method="POST" role="form">

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
						value="<c:out value="${requestScope.userDto.getName()}" />" />
				</div>
				<div class="form-group">
					<label for="surname"><fmt:message key="restaurant.surname"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="surname" name="surname"
						placeholder="<fmt:message key="restaurant.surname" bundle="${rb}"/>"
						value="<c:out value="${requestScope.userDto.getSurname()}" />" />
				</div>
				<div class="form-group">
					<label for="address"><fmt:message key="restaurant.address"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="address" name="address"
						placeholder="<fmt:message key="restaurant.address" bundle="${rb}"/>"
						value="<c:out value="${requestScope.userDto.getAddress()}" />" />
				</div>
				<div class="form-group">
					<label for="phone"><fmt:message key="restaurant.phone"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="phone" name="phone"
						placeholder="<fmt:message key="restaurant.phone" bundle="${rb}"/>"
						value="<c:out value="${requestScope.userDto.getPhone()}" />" />
				</div>
				<div class="form-group">
					<label for="role"><fmt:message key="restaurant.role"
							bundle="${rb}" /></label> <select class="form-control" id="role"
						name="role">
						<c:if test="${not empty requestScope.userDto}">
							<option value="${requestScope.userDto.getRole().getValue()}"><fmt:message
									key="${requestScope.userDto.getRole().getLocalizedValue()}"
									bundle="${rb}" /></option>
						</c:if>
						<c:forEach items="${roles}" var="role">
							<option value="${role.getValue()}"><fmt:message
									key="${role.getLocalizedValue()}" bundle="${rb}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="email"><fmt:message key="restaurant.email"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="email" name="email"
						placeholder="<fmt:message key="restaurant.email" bundle="${rb}"/>"
						value="<c:out value="${requestScope.userDto.getEmail()}" />" />
				</div>
				<div class="form-group">
					<label for="password"><fmt:message key="restaurant.pass"
							bundle="${rb}" /></label> <input type="password" class="form-control"
						id="password" name="password"
						placeholder="<fmt:message key="restaurant.pass" bundle="${rb}"/>" />
				</div>
				<div class="form-group">
					<label for="confirmPassword"><fmt:message
							key="restaurant.confirmPass" bundle="${rb}" /></label> <input
						type="password" class="form-control" id="confirmPassword"
						name="confirmPassword"
						placeholder="<fmt:message key="restaurant.confirmPass" bundle="${rb}"/>" />
				</div>

				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="restaurant.add" bundle="${rb}" />
				</button>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>