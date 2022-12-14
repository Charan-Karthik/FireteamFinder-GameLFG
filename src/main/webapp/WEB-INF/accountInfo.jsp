<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fireteam Finder - Account Info</title>
<!-- get Bootstrap from web -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
	crossorigin="anonymous"></script>
<!-- for CSS and/or JS -->
<!-- <link rel="stylesheet" href="{{ CSS PATH }}"> -->
<!-- <script src="{{ JS PATH }}"></script> -->
</head>
<body style="background-color: #1d232f; color: white">
	<div class="container mt-4">
		<div class="d-flex justify-content-between align-items-center">
			<a href="/" class="btn btn-outline-light">Home</a> <a href="/logout"
				class="btn btn-outline-danger">Log Out</a>
		</div>

		<h1 class="text-center mt-3">
			Welcome,
			<c:out value="${thisUser.username}" />
		</h1>

		<div class="d-flex justify-content-around">
			<form:form action="/edit/account" method="post" modelAttribute="thisUserCopy"
				class="w-25">

				<h4 class="mt-4 mb-3">Edit Account Info:</h4>
				<form:input type="hidden" path="id" />
				<form:input type="hidden" path="confirm" value="tempValue"/>

				<div class="form-group mb-3">
					<form:label path="username" class="form-label">Username:</form:label>
					<form:input path="username" class="form-control" />
					<form:errors path="username" class="text-danger" />
				</div>

				<div class="form-group mb-3">
					<form:label path="email" class="form-label">Email:</form:label>
					<form:input path="email" class="form-control" />
					<form:errors path="email" class="text-danger" />
				</div>

				<div>
					<input type="submit" value="Submit" class="btn btn-primary" />
				</div>
			</form:form>

			<form class="w-25">
				<h4 class="mt-4 mb-3">Change Password:</h4>
				<div class="form-group mb-3">
					<label for="password" class="form-label">Current Password:</label>
					<input name="password" type="password" class="form-control">
				</div>
				
				<div class="form-group mb-3">
					<label for="newPassword" class="form-label">New Password:</label>
					<input name="newPassword" type="password" class="form-control">
				</div>
				
				<div class="form-group mb-3">
					<label for="confirmNewPassword" class="form-label">Confirm New Password:</label>
					<input name="confirmNewPassword" type="password" class="form-control">
				</div>
				
				<input type="submit" value="Submit" class="btn btn-primary">
			</form>
		</div>
	</div>
</body>
</html>