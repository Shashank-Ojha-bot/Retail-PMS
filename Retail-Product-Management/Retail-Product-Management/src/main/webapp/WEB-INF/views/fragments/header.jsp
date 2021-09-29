<%@ page language="java" 
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="header">
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="/portal/viewProducts">RetailProductManagement</a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

    <div style="margin-left: 55%">
        <a href="/portal/viewWishList">
          <button
            class="btn btn-outline-warning my-2 mx-3 my-sm-0"
            type="submit"
          >
            <i style="font-size: 20px" class="fa">WishList</i>
          </button>
        </a>
        
        <!-- 
        <a href="/portal/viewAllCarts">
          <button class="btn btn-outline-warning my-2 my-sm-0" type="submit">
            <i style="font-size: 30px" class="fa">&#xf07a;</i>
          </button>
        </a>   -->
        
         <a href="/portal/viewAllCarts">
          <button class="btn btn-outline-warning my-2 my-sm-0" type="submit">
           <i style="font-size: 20px" class="fa">Cart</i>
          </button>
        </a>
        
        <a href="/portal/logout">
          <button
            class="btn btn-danger my-2 mx-3 my-sm-0"
            type="submit"
          >
            Logout
          </button>
        </a>
 
      </div>
    </nav>
    
</div>