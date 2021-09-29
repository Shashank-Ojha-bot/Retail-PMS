<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Cart</title>
</head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
<link rel="stylesheet" href="./style.css" />

<body class="otherPageBody">
	<!-- navbar for the pages -->
	<%@ include file="fragments//header.jsp"%>
	<div class="container">
		<div class="row">
			<c:set var="totalPrice" value="${0}" />
			<c:set var="delivery" value="${0}" />
			<c:forEach var="item" items="${cartList}">
				<c:set var="totalPrice"
					value="${totalPrice+(item.product.price)}" />
				<c:set var="delivery"
					value="${delivery+(item.vendor.deliveryCharge)}" />
				<!-- add a single item fech from database  -->
				<div class="col-sm">
					<div class="card my-4 color" style="width: 18rem">
						<img src="${item.product.imageName}"
							class="card-img-top img-height" alt="..." />
						<div class="card-body">
							<h5 class="card-title">${item.product.name}</h5>
							<p class="card-text">${item.product.description}</p>

							<h5 class="price">
								<strong>Price : ₹ ${item.product.price}</strong>
							</h5>
							<h5 id="price">
								Rating : ${item.product.rating} <i style="font-size: 15px"
									class="fa">&#xf005;</i>
							</h5>
							
							<div>
								<span id="zipCode">Delivering to ZipCode : ${item.zipcode}
								</span> <br> <span id="expectedDeliveryDate">
									Delivery Date : <fmt:parseDate value="${item.deliveryDate}" pattern="yyyy-MM-dd" var="parsedDateTime" type="both" />
									<fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedDateTime }" /> </span>
									</br>
									
							</div>
							
							<h5 class="card-text">
								Vendor Name :${item.vendor.vendorName}
							</h5>
						
							<h5 class="card-text">
								Delivery Charge : ₹ ${item.vendor.deliveryCharge}
							</h5>
							<h5 class="card-text">
								Vendor Rating :${item.vendor.vendorRating}
							</h5>
						</div>

					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<c:if test = "${not empty cartList}">
	<div class="table">
		<table id="example" style="width: 100%">
			<thead>
				<tr>
					<th id="price">Price</th>
					<th id="delivery">Delivery Charge</th>
					<th id="total">Total</th>

				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${totalPrice}</td>
					<td>${delivery}</td>
					<td>${totalPrice+delivery}</td>
				</tr>
			</tbody>
			<tfoot>

			</tfoot>
		</table>
	
	</div>
</c:if>

<c:if test="${empty cartList}">
	<c:set var="error" value="${errorMessage}" />
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
