<%@include file="header.jsp"%>

<div class="container-fluid">

	<div class="row-fluid" align="left">
		<div class="btn-group" role="group" aria-label="buttons">
			<button type="button" class="btn btn-default"
				onclick="location.href='${pageContext.request.contextPath}/controller/manager/categories/addCategory';">
				<fmt:message key="restaurant.add" bundle="${rb}" />
			</button>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#searchByName">
				<fmt:message key="restaurant.category.searchByName" bundle="${rb}" />
			</button>
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
						action="${pageContext.request.contextPath}/controller/manager/categories/name"
						method="POST" role="form">

						<div class="form-group">
							<label for="name"><fmt:message
									key="restaurant.category.name" bundle="${rb}" /></label> <input
								type="text" class="form-control" id="name" name="name" />
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
			<fmt:message key="restaurant.categories" bundle="${rb}" />
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
					<th>***</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${categories}" var="category" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${category.getName()}</td>
						<td><a
							href="./categories/updateCategory?id_category=${category.getId()}"><fmt:message
									key="restaurant.update" bundle="${rb}" /></a> <br> <a
							href="./categories/deleteCategory?id_category=${category.getId()}"><fmt:message
									key="restaurant.delete" bundle="${rb}" /></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>

<%@include file="footer.jsp"%>
