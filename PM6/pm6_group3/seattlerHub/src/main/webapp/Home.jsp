<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <title>SeattlerHub</title>
    </htead>

<body>

    <div class="navbar navbar-dark bg-dark shadow-sm">
        <div class="container">
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>SeattlerHub</strong>
            </a>
            <ul class="navbar-nav d-flex flex-row justify-content-evenly" style="width: 20%;">
                <li class="nav-item">
                    <a class="nav-link mx-3" href="./login"><strong>Login</strong></a>
                    <p id="username" style="display: none">${username} </p>
                </li>
                <li class="nav-item">
                    <a class="nav-link mx-3" href="./register"><strong>Register</strong></a>
                </li>
            </ul>

        </div>
    </div>
    </header>

    <main>
        <section class="text-center container">
            <div class="row py-lg-5">
                <div class="col-lg-8 col-md-8 mx-auto d-flex flex-column align-items-center">
                    <h1 class="fw-light">SeattlerHub</h1>
                    <p class="lead text-muted">Not just to settle, but to be Seattle</p>
                    <form action="home" method="get">
                        <button class="btn btn-primary my-2">display all</button>
                    </form>
                    <div class="d-flex flex-row">
                        <div class="card-body">
                            <h3 class="card-title">Price</h3>
                            <div class="btn-group mx-3">
                                <form action="findHousingPriceHighToLow" method="post">
                                    <button class="btn btn-primary my-2">high to low</button>
                                </form>
                                <form action="findHousingPriceLowToHigh" method="post">
                                    <button class="btn btn-secondary my-2">low to high</button>
                                </form>
                            </div>
                        </div>
                        <div class="card-body">
                            <h3 class="card-title">Rating</h3>
                            <div class="btn-group mx-3">
                                <form action="findHousingRatingHighToLow" method="post">
                                    <button class="btn btn-primary my-2">high to low</button>
                                </form>
                                <form action="findHousingRatingLowToHigh" method="post">
                                    <button class="btn btn-secondary my-2">low to high</button>
                                </form>
                            </div>
                        </div>
                         
                    </div>
                </div>    
            </div>
        
            
           <form action="filterHousing" method="post" class="mb-5">
           		<h5 class="mb-3">Filter Housing based on: </h5>
            	<div class="form-check form-check-inline">
  					<input class="form-check-input" type="checkbox" name="filterchoice" id="inlineCheckbox1" value="crimecases">
  					<label class="form-check-label" for="inlineCheckbox1">Crime</label>
				</div>
				<div class="form-check form-check-inline">
  					<input class="form-check-input" type="checkbox" name="filterchoice" id="inlineCheckbox2" value="schools">
  					<label class="form-check-label" for="inlineCheckbox2">Schools</label>
				</div>
				<div class="form-check form-check-inline">
  					<input class="form-check-input" type="checkbox" name="filterchoice" id="inlineCheckbox3" value="restaurants">
  					<label class="form-check-label" for="inlineCheckbox3">Restaurants</label>
				</div>
				<div class="form-check form-check-inline">
  					<input class="form-check-input" type="checkbox" name="filterchoice" id="inlineCheckbox4" value="culturalspaces">
  					<label class="form-check-label" for="inlineCheckbox4">Cultural Spaces</label>
				</div>
				<div class="form-check form-check-inline">
  					<input class="form-check-input" type="checkbox" name="filterchoice" id="inlineCheckbox5" value="parks">
  					<label class="form-check-label" for="inlineCheckbox5">Parks</label>
				</div>
				<br>
				<input type="submit" value="Submit" class="btn btn-outline-primary mt-3"/> 
			</form>
        </section>



        <div class="album py-5 bg-light">
            <div class="container">
                <div class="d-flex flex-row flex-wrap">
                    <c:forEach items="${housings}" var="housing">
                        <div class="w-50 px-5 py-3 mb-1">
                            <div class="card shadow-sm d-flex">
                                <div class="card-body d-flex flex-column">
                                    <h3 class="card-title">
                                        $<c:out value="${housing.getRentalPrice()}" />
                                    </h3>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h4 class="card-subtitle">
                                            <c:out value="${housing.getName()}" />
                                        </h4>

                                    </div>
                                    <p class="text-muted">
                                        <c:out value="${housing.getAddress()}" />
                                    </p>
                              		<img class="mb-1" src="${housing.getImgLink()}" alt="" width=100% height=250px />
                                    <a href="./comments?housingId=<c:out value="${housing.getHousingId()}"/>" type="button" class="mb-3 btn btn-success">View Comments</a>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="mb-3 btn-group">
                                            <a href="./edit?housingid=<c:out value="${housing.getHousingId()}" />"class="btn btn-primary">Update</a>
                                            <a href="./delete?housingid=<c:out value="${housing.getHousingId()}" />"class="btn btn-danger">Delete</a>
                                        </div>   
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>


    </main>

    <footer class="text-muted py-3">
        <div class="container">
            <p class="float-end mb-1">
                <button class="btn btn-light"><a href="#">Back to top</a></button>
            </p>
        </div>
    </footer>

    <!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>

</html>