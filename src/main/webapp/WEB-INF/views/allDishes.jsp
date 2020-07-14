<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<c:if test="${not empty user}">
				<c:if test="${user.getRole().getValue() eq 'manager' }">
					<button type="button" class="btn btn-default"
						onclick="location.href='${pageContext.request.contextPath}/controller/manager/dishes/addDish';">
						<fmt:message key="restaurant.add" bundle="${rb}" />
					</button>
				</c:if>
			</c:if>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#searchByName">
				<fmt:message key="restaurant.category.searchByName" bundle="${rb}" />
			</button>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#searchByCategory">
				<fmt:message key="restaurant.dish.searchByCategory" bundle="${rb}" />
			</button>
			<c:if test="${not empty user}">
				<c:if test="${user.getRole().getValue() eq 'manager' }">
					<button type="button" class="btn btn-default" data-toggle="modal"
						data-target="#searchBestDishes">
						<fmt:message key="restaurant.dish.searchBestDishes" bundle="${rb}" />
					</button>
				</c:if>
			</c:if>
		</div>
	</div>

	<!-- modal searchByName -->
	<div class="modal fade" id="searchByName" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="restaurant.category.searchByName" bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/dishes/name"
						method="POST" role="form">

						<div class="form-group">
							<label for="surname"><fmt:message key="restaurant.name"
									bundle="${rb}" /></label> <input type="text" class="form-control"
								id="name" name="name" />
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

	<!-- modal searchByCategory -->
	<div class="modal fade" id="searchByCategory" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="restaurant.dish.searchByCategory" bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/dishes/category"
						method="POST" role="form">

						<div class="form-group">
							<label for="role"><fmt:message key="restaurant.category"
									bundle="${rb}" /></label> <select class="form-control" id="category"
								name="category">
								<option value=""><fmt:message key="restaurant.category"
										bundle="${rb}" /></option>
								<c:forEach items="${categories}" var="category">
									<option value="${category.getName()}">${category.getName()}</option>
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

	<!-- modal searchBestDishes -->
	<div class="modal fade" id="searchBestDishes" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="restaurant.dish.searchBestDishes" bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/manager/dishes/bestDishes"
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
			<fmt:message key="restaurant.menu" bundle="${rb}" />
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
					<th><fmt:message key="restaurant.description" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.weight" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.cost" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.category" bundle="${rb}" /></th>
					<c:if test="${not empty user}">
						<c:if test="${user.getRole().getValue() eq 'manager' }">
							<th>***</th>
						</c:if>
					</c:if>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${dishes}" var="dish" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${dish.getName()}</td>
						<td>${dish.getDescription()}</td>
						<td>${dish.getWeight()}</td>
						<td>${dish.getCost()}</td>
						<td>${dish.getCategory().getName()}</td>
						<c:if test="${not empty user}">
							<c:if test="${user.getRole().getValue() eq 'manager' }">
								<td><a
									href="./manager/dishes/updateDish?id_dish=${dish.getId()}"><fmt:message
											key="restaurant.update" bundle="${rb}" /></a> <br> <a
									href="./manager/dishes/deleteDish?id_dish=${dish.getId()}"><fmt:message
											key="restaurant.delete" bundle="${rb}" /></a></td>
							</c:if>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>

<%@include file="footer.jsp"%>
