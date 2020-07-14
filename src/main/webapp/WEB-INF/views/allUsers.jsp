<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<button type="button" class="btn btn-default"
				onclick="location.href='${pageContext.request.contextPath}/controller/manager/users/addUser';">
				<fmt:message key="restaurant.add" bundle="${rb}" />
			</button>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#searchBySurname">
				<fmt:message key="restaurant.user.searchBySurname" bundle="${rb}" />
			</button>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#searchByRole">
				<fmt:message key="restaurant.user.searchByRole" bundle="${rb}" />
			</button>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#searchBestWaiters">
				<fmt:message key="restaurant.user.searchBestWaiters" bundle="${rb}" />
			</button>
		</div>
	</div>

	<!-- modal searchBySurname -->
	<div class="modal fade" id="searchBySurname" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="restaurant.user.searchBySurname" bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/manager/users/surname"
						method="POST" role="form">

						<div class="form-group">
							<label for="surname"><fmt:message
									key="restaurant.surname" bundle="${rb}" /></label> <input type="text"
								class="form-control" id="surname" name="surname" />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default" id="submitButton">
								<fmt:message key="restaurant.search" bundle="${rb}" />
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- modal searchByRole -->
	<div class="modal fade" id="searchByRole" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="restaurant.user.searchByRole" bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/manager/users/role"
						method="POST" role="form">

						<div class="form-group">
							<label for="role"><fmt:message key="restaurant.role"
									bundle="${rb}" /></label> <select class="form-control" id="role"
								name="role">
								<option value=""><fmt:message key="restaurant.role"
										bundle="${rb}" /></option>
								<c:forEach items="${roles}" var="role">
									<option value="${role.getValue()}"><fmt:message
											key="${role.getLocalizedValue()}" bundle="${rb}" /></option>
								</c:forEach>
							</select>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default" id="submitButton">
								<fmt:message key="restaurant.search" bundle="${rb}" />
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- modal searchBestWaiters -->
	<div class="modal fade" id="searchBestWaiters" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="restaurant.user.searchBestWaiters"
							bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/manager/users/bestWaiters"
						method="POST" role="form">

						<div class="form-group">
							<label for="surname"><fmt:message
									key="restaurant.fromDate" bundle="${rb}" /></label> <input type="date"
								class="form-control" id="fromDate" name="fromDate" />
						</div>
						<div class="form-group">
							<label for="surname"><fmt:message key="restaurant.toDate"
									bundle="${rb}" /></label> <input type="date" class="form-control"
								id="toDate" name="toDate" />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default" id="submitButton">
								<fmt:message key="restaurant.search" bundle="${rb}" />
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	<div class="row-fluid" align="center">
		<h2>
			<fmt:message key="restaurant.users" bundle="${rb}" />
		</h2>
	</div>

	<div class="row-fluid" align="center">
		<c:if test="${not empty param.success}">
			<div class="alert alert-success">
				<fmt:message key="${param.success}" bundle="${rb}" />
			</div>
		</c:if>
	</div>
	<div class="row-fluid" align="center">
		<c:if test="${not empty param.error}">
			<div class="alert alert-danger">
				<fmt:message key="${param.error}" bundle="${rb}" />
			</div>
		</c:if>
	</div>

	<div class="row-fluid top-margin" align="center">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th><fmt:message key="restaurant.name" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.surname" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.address" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.phone" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.role" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.email" bundle="${rb}" /></th>
					<th>***</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${users}" var="user" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${user.getName()}</td>
						<td>${user.getSurname()}</td>
						<td>${user.getAddress()}</td>
						<td>${user.getPhone()}</td>
						<td><fmt:message key="${user.getRole().getLocalizedValue()}"
								bundle="${rb}" /></td>
						<td>${user.getEmail()}</td>

						<td><a href="./users/updateUser?id_user=${user.getId()}"><fmt:message
									key="restaurant.update" bundle="${rb}" /></a> <br> <a
							href="./users/deleteUser?id_user=${user.getId()}"><fmt:message
									key="restaurant.delete" bundle="${rb}" /></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>

<%@include file="footer.jsp"%>
