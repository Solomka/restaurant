<%@include file="/WEB-INF/views/header.jsp"%>

<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="library.add" bundle="${rb}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./addReader" method="POST" role="form">

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
					<label for="readerCardNumber"><fmt:message
							key="library.readerCardNumber" bundle="${rb}" /></label> <input
						type="text" class="form-control" id="readerCardNumber"
						name="readerCardNumber"
						placeholder="<fmt:message key="library.readerCardNumber"
							bundle="${rb}" />"
						value="<c:out value="${requestScope.reader.getReaderCardNumber()}" />" />
				</div>
				<div class="form-group">
					<label for="surname"><fmt:message key="library.surname"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="surname" name="surname"
						placeholder="<fmt:message key="library.surname" bundle="${rb}"/>"
						value="<c:out value="${requestScope.reader.getSurname()}" />" />
				</div>

				<div class="form-group">
					<label for="name"><fmt:message key="library.name"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="name" name="name"
						placeholder="<fmt:message key="library.name"
							bundle="${rb}" />"
						value="<c:out value="${requestScope.reader.getName()}" />" />
				</div>
				<div class="form-group">
					<label for="patronymic"><fmt:message
							key="library.patronymic" bundle="${rb}" /></label> <input type="text"
						class="form-control" id="patronymic" name="patronymic"
						placeholder="<fmt:message key="library.patronymic"
							bundle="${rb}" />"
						value="<c:out value="${requestScope.reader.getPatronymic()}" />" />
				</div>
				<div class="form-group">
					<label for="email"><fmt:message key="library.email"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="email" name="email"
						placeholder="<fmt:message key="library.email" bundle="${rb}"/>"
						value="<c:out value="${requestScope.reader.getEmail()}" />" />
				</div>
				<div class="form-group">
					<label for="phone"><fmt:message key="library.phone"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="phone" name="phone"
						placeholder="<fmt:message key="library.phone" bundle="${rb}"/>"
						value="<c:out value="${requestScope.reader.getPhone()}" />" />
				</div>
				<div class="form-group">
					<label for="address"><fmt:message key="library.address"
							bundle="${rb}" /></label> <input type="text" class="form-control"
						id="address" name="address"
						placeholder="<fmt:message key="library.address" bundle="${rb}"/>"
						value="<c:out value="${requestScope.reader.getAddress()}" />" />
				</div>

				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="library.add" bundle="${rb}" />
				</button>
			</form>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>