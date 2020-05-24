<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<c:if test="${user.getRole().getValue() eq 'waiter' }">
				<button type="button" class="btn btn-default"
					onclick="location.href='./waiter/orders/addOrder';">
					<fmt:message key="restaurant.add" bundle="${rb}" />
				</button>
			</c:if>
			<c:if test="${user.getRole().getValue() eq 'manager' }">
				<button type="button" class="btn btn-default" data-toggle="modal"
					data-target="#searchOrdersPerPeriod">
					<fmt:message key="restaurant.order.searchOrdersPerPeriod"
						bundle="${rb}" />
				</button>
			</c:if>
		</div>
	</div>

	<!-- modal searchOrdersPerPerio -->
	<div class="modal fade" id="searchOrdersPerPeriod" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<fmt:message key="restaurant.order.searchOrdersPerPeriod"
							bundle="${rb}" />
					</h4>
				</div>
				<div class="modal-body">
					<form
						action="${pageContext.request.contextPath}/controller/manager/orders/perPeriod"
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
			<fmt:message key="restaurant.orders" bundle="${rb}" />
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
					<th><fmt:message key="restaurant.date" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.dishes" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.total" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.status" bundle="${rb}" /></th>
					<th><fmt:message key="restaurant.waiter" bundle="${rb}" /></th>
					<th>***</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${orders}" var="order" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${order.getDate()}</td>
						<td><c:forEach items="${order.getDishes()}" var="dish">
						${dish.getName()}<br />
							</c:forEach></td>
						<td>${order.getTotal()}</td>
						<td><fmt:message
								key="${order.getStatus().getLocalizedValue()}" bundle="${rb}" /></td>
						<td>${order.getUser().getName()}
							${order.getUser().getSurname()}</td>
						<td><c:if
								test="${user.getRole().getValue() eq 'waiter' or user.getRole().getValue() eq 'chief' }">
								<a href="./orders/updateOrder?id_order=${order.getId()}"><fmt:message
										key="restaurant.update" bundle="${rb}" /></a>
							</c:if> <c:if test="${user.getRole().getValue() eq 'manager' }">
								<br>
								<a href="./manager/orders/deleteOrder?id_order=${order.getId()}"><fmt:message
										key="restaurant.delete" bundle="${rb}" /></a>

							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>

<%@include file="footer.jsp"%>
