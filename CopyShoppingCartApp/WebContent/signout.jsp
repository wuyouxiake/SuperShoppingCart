<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Signed Out</title>
 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
 
</head>
<body>
<c:set var="user_type" scope="session" value="${user_type}" />
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="index.jsp">Shopping Cart</a>
			</div>
			<div>
			<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
				<ul class="nav navbar-nav">
						<li><a href="GetProduct">Product List</a></li>
					<c:if test="${user_type == 'admin'}">
						<li><a href="GetAllCart">All Carts</a></li>
						<li><a href="SendCredit.jsp">Send Credit</a></li>
						<li><a href="SignOut">Sign Out</a></li>
					</c:if>	
					<c:if test="${user_type == 'regular'}">
						<li><a href="GetCart">My Cart</a></li>
						<li><a href="GetMyOrder">My Order</a></li>
						<li><a href="SignOut">Sign Out</a></li>
					</c:if>
					<c:if test="${user_type == null}">
						<li><a href="signin.jsp">Sign In</a></li>
						<li><a href="signup.jsp">Sign Up</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
<p>${alert}</p><br><br>
<a href="index.jsp">Home</a>
</body>
</html>