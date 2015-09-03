<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign up</title>
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
<div align="center">
<form class="form-horizontal" role="form" method="get" action="SignUp">
    
<label for="username">User Name: </label><br>
<input type="text" name="username" required>
<br><br>

<label for="password">Password: </label><br>
<input type="password" name="password" required>
<br><br>

<label for="email">Email: </label><br>
<input type="email" name="email">
<br><br>

<label for="photolink">Photo Link: </label><br>
<input type="text" name="photolink">
<br><br>

<label for="usertype">User Type: </label><br>
<select name="usertype" required>
  <option value="admin">Administrator</option>
  <option value="regular">Regular User</option>
</select>
<br><br>

<input type="submit" name="submit" Value="submit">

</form>

</div>


</body>
</html>