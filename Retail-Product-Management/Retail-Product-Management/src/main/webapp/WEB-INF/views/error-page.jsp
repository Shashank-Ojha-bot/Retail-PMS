<%@ page language="java" 
	pageEncoding="ISO-8859-1"%>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Error</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Pinyon+Script&display=swap"
	rel="stylesheet">
	<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Raleway:wght@600;800&display=swap" rel="stylesheet">

<link rel="stylesheet" href="/style.css">

</head>
<body class="mainPageBody">

		<%@ include file="fragments/header.jsp"%>
		
	<div class="main-container-register" style="margin-bottom: 28%">

		<div class="container error-container">
			
			<br>
		<div class="error"> <h2> <strong> ${errorMsg} </strong> </h2> </div>
			
			<div class="error"> <h3> <strong> ${error} </strong> </h3></div>
		
		
		<!-- To Add Product into Customer Wish List if it is out of stock -->

		<div>
		<c:if test="${not empty msg}">
		<form action="addToWishList">
			<input type="text" id="productId" name="productId" value="${productId }" hidden /> 
			<input type="text" id="quantity" name="quantity" value="${quantity}" hidden /> 
			
			<input type="text" id="productId" name="productId" value="${product.id}" hidden >
			<br>	
			<input type="submit" class="btn btn-primary" value="${msg}" />
		</form>
		</c:if>
		</div>
		</div>
	</div>
	
	     <%@ include file="fragments//footer1.jsp"%>    
</body>


</html>