<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>WishList</title>
</head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="./style.css" />
<body class="otherPageBody">
	<!-- navbar for the pages -->
	
	<%@ include file="fragments//header.jsp"%>

	<!-- body section of the page use jsp -->
	<!-- use loop to fetch the data and display it -->
	
	<div class="container">
			<div class="row" style="padding-right: 5 ">
				<!-- add a single item fech from database  -->
				
	<c:forEach var="item" items="${wishList}">
		
				<div class="col-sm " >
					<div class="card my-4 color" style="width: 18rem">
						<img src="${item.product.imageName}"
							class="card-img-top img-height" alt="..." />
						<div class="card-body">
							<h5 class="card-title">${item.product.name}</h5>
							<p class="card-text">${item.product.description}</p>

							<h5 class="price">
								<strong>Price : ₹ ${item.product.price}</strong>
							</h5>
							<br>
							<h5 class="price">
								<strong>Quantity :${item.quantity}</strong>
							</h5>
							<br> Rating :
								${item.product.rating} <i style="font-size: 15px" class="fa">&#xf005;</i>
							 <br>
						</div>
					</div>
				</div>
	</c:forEach>

			</div>
		</div>
<c:if test="${empty wishList}">
	<c:set var="error" value="${errorMEssage}" />
	<c:set var="errorHead" value="${errorHead}" />
	<div class="errorDiv" >
	<div class="alert">
  <strong>${errorHead}</strong><br>${error}
  <br>
   <a href="/portal/viewProducts" >
			
			<button class="btn btn-primary">
				<h4>Start Shopping</h4>
			</button>
			</a> 
  </div>
 
  </div>
  
  </c:if>

	
	  <%@ include file="fragments//footer1.jsp"%>    
	  
</body>
</html>

