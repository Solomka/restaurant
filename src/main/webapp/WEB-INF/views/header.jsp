<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="udt" uri="customtags"%>

<c:set var="lang" scope="session"
	value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}" />
<fmt:setLocale value="${lang}" scope="session" />
<fmt:setBundle basename="/i18n/messages" var="rb" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${lang}">
<head>
<title>Restaurant</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/styles.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/bootstrap-3.3.7/css/bootstrap.min.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/font-awesome-4.7.0/css/font-awesome.min.css" />">
</head>

<body>
	<div class="container main-container">
		<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand"
					href="${pageContext.request.contextPath}/controller/"><fmt:message
						key="restaurant.main" bundle="${rb}" /></a>
			</div>
			 <ul class="nav navbar-nav">
				<li><a
					href="${pageContext.request.contextPath}/controller/menu"><fmt:message
							key="restaurant.menu" bundle="${rb}" /></a></li>
							
				<%-- NOT EMPTY USER --%>
				<%-- <c:if test="${not empty user}">
					<c:if test="${user.getRole().getValue() eq 'librarian' }">
						<li><a
							href="${pageContext.request.contextPath}/controller/librarian/authors"><fmt:message
									key="library.authors" bundle="${rb}" /></a></li>
					</c:if>
					<c:if test="${user.getRole().getValue() eq 'librarian' }">
						<li><a
							href="${pageContext.request.contextPath}/controller/librarian/readers"><fmt:message
									key="library.readers" bundle="${rb}" /></a></li>
					</c:if>
					<li><a
						href="${pageContext.request.contextPath}/controller/${user.getRole().getValue()}/orders"><fmt:message
								key="library.orders" bundle="${rb}" /></a></li>

				</c:if> --%>
			</ul>

			<ul class="nav navbar-nav navbar-right">

				<c:if test="${not empty user}">
					<!-- Custom tag  -->
					<li><p class="navbar-text">
							<udt:user-data user="${user}" />
						</p></li>
				</c:if>
				
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-globe"
						aria-hidden="true"></i> ${sessionScope.locale.getLanguage()} <span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<c:forEach items="${applicationScope.locales}" var="locale">
							<c:set var="lang" value="${locale.getLanguage()}" />
							<li><a
								href="${pageContext.request.contextPath}/controller/locale?lang=${lang}">${lang.toUpperCase()}</a></li>
						</c:forEach>
					</ul></li>
				<c:choose>
					<c:when test="${empty user}">
						<li><a
							href="${pageContext.request.contextPath}/controller/login"><span
								class="glyphicon glyphicon-log-out"></span> <fmt:message
									key="restaurant.login" bundle="${rb}" /></a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.request.contextPath}/controller/logout"><span
								class="glyphicon glyphicon-log-in"></span> <fmt:message
									key="restaurant.logout" bundle="${rb}" /></a></li>
					</c:otherwise>
				</c:choose>

			</ul>
		</div>
		</nav>