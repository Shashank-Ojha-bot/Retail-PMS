<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> --%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Retail Products</title>
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
<link rel="stylesheet" href="/style.css" />

<body class="mainPageBody">

		<%@ include file="fragments//header.jsp"%>
	<!-- navbar for the pages -->
 	<nav class="navbar navbar-expand-lg navbar-dark ">
		<!--  <a class="navbar-brand" href="/portal/index">RetailProductMangement</a>   -->

		<div class="collapse navbar-collapse buttom-danger"
			id="navbarSupportedContent" style="margin-left: 9%">
			<form class="form-inline my-2 my-lg-0" action="/portal/productsByName"
				method="get">
				<input class="form-control mr-sm-2" type="text" placeholder="Search"
					name="productName" id="productName" required /> <input
					class="btn btn-outline-danger my-2 my-sm-0" type="submit"
					value="Search By Name">
			</form>
		</div>
		
		<div class="collapse navbar-collapse buttom-danger"
			id="navbarSupportedContent">
			<form class="form-inline my-2 my-lg-0" action="/portal/productById"
				method="get">
				<input class="form-control mr-sm-2" type="number" placeholder="Search"
					name="productId" id="productId" required /> <input
					class="btn btn-outline-danger my-2 my-sm-0" type="submit"
					value="Search By Id">
			</form>
			<br>
			
		
			
		</div>
		
		<br>
			
		
	</nav>
	<div style="margin-left: 10%">
		<h3> ${Message} </h3>
	</div>
		
			
	<!-- body section of the page use jsp -->
	<!-- use loop to fetch the data and display it -->
	<div class="container">
		<div class="row">
			<!-- add a single item fech from database  -->
			<c:forEach var="product" items="${productList}">
				<div class="card my-4 mx-4 color display" style="width: 18rem">
					<img src="${product.imageName}" class="card-img-top" height="250" width="100"
						alt="..." />
					<div class="card-body">
						<h5 class="card-title">${product.name}</h5>
						<p class="card-text">${product.description}</p>
						<h5 class="price">
							<strong>Price : ₹ ${product.price}</strong>
						</h5>

						<span id="rating">Rating:${product.rating} <i
							style="font-size: 15px" class="fa">&#xf005;</i></span>
						<br>
						<span id="resultSpan"></span>
						
						<div id="zipcode${product.id}" style="display: none;">
						
						<form action="/portal/addToCart" method="get" >
						<label>Enter Zip Code</label>
						<input type="text" id="zip" name="zip"
									class="form-control input-number" maxlength=6 required>
						<label>Enter Rating</label>
						<input type="number" id="rating" name="rating" class="form-control input-number" min=0.0 max=5.0 step=0.1 required >
						<br>
						<input type="number" id="productId" name="productId" value="${product.id}" hidden >
						
									<input type="submit" class="btn btn-warning" value="Add" style="margin-left: 35%"/>
						</form>
						</div>
						<br>
						<button class="btn btn-warning addcart" style="background:#ffffbf" id="buttoncart${product.id}" onClick="Cart(${product.id})">Add To Cart</button>
							<br>
							<br>
							
						<div id="wishlist${product.id}" style="display: none;">
						
						<form action="/portal/addToWishList" method="get" >
						<label>Enter Quantity</label>
						<input type="number" id="quantity" name="quantity" class="form-control input-number" min=1 value=1 required >
						<input type="number" id="productId" name="productId" value="${product.id}" hidden >
						
						<br>
									<input type="submit" class="btn btn-warning" value="Add" style="margin-left: 35%"/>
						</form>
						</div>
						<button class="btn btn-warning addcart" style="background:#ffffbf" id="buttonwish${product.id}" onClick="WishList(${product.id})">Add To WishList</button>
							
						<!-- 	<button class="btn btn-warning addcart" id="" data-toggle="modal"
							data-target="#myModal" onClick="configureProductId(${product.id})">Add
							to Cart2</button>    -->

						
					</div>
				</div>
			</c:forEach>
			
	
		
		
		
		</div>
	</div>


	<script>
	function Cart(id)
	{
		document.getElementById("zipcode"+id).style.display="block";
		document.getElementById("buttoncart"+id).style.display="none";
		document.getElementById("buttonwish"+id).style.display="none";
	}
	
	function WishList(id)
	{
		document.getElementById("wishlist"+id).style.display="block";
		document.getElementById("buttonwish"+id).style.display="none";
		document.getElementById("buttoncart"+id).style.display="none";
	
	}
	

     </script>
     
     <%@ include file="fragments//footer1.jsp"%>    
     
</body>
</html>
