<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
                <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
                    <!-- Bootstrap -->
                    <link href="./stars.css" rel="stylesheet">
                    <link href="css/bootstrap.min.css" rel="stylesheet">
                    <title>Comments</title>
                </head>

                <body class="text-center" style="background: rgb(236, 236, 236);">
                    <main class="d-flex justify-content-center align-items-center" style="width: 100%; height: 100vh;"
                        }>
                        <div class="card shadow-lg d-flex justify-content-center align-items-center"
                            style="width: 30%; background: transparent; border: none">
                            <div class="card-body" style="width: 70%;">
                                <div class="w-30">
                                    <div class="card shadow-sm d-flex">
                                        <div class="card-body d-flex flex-column">
                                            <h1>Comments</h1>
                                            <c:forEach items="${reviews}" var="review">
                                                <div class="card align-self-center mb-3" style="width: 100%;">
                                                    <div class="card-body d-flex justify-content-center align-items-center flex-column">
                                                        <p class="starability-result"
                                                            data-rating="${Math.round(review.getRating())}">
                                                        </p>
                            							<p class="card-text">
                                                            <c:out value="${review.getContent()}" />
                                                        </p>
                                                        <h6 class="card-subtitle mb-2 text-muted">
                                                            <c:out value="by ${review.getUsers().getUserName()}" />                                                        
                                                        </h6>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                            <div class="card mb-3">
                                                <div class="card-body">
                                                    <div class="align-center">
                                                        <form class="card-body d-flex justify-content-center align-items-center flex-column" 
                                                        	action="comments" class="validated-form" method="post">
                                                            <fieldset class="starability-basic">
                                                                <input type="radio" id="no-rate" class="input-no-rate"
                                                                    name="review[rating]" value="1" checked
                                                                    aria-label="No rating." />
                                                                <input type="radio" id="first-rate1"
                                                                    name="review[rating]" value="1" />
                                                                <label for="first-rate1" title="Terrible"></label>
                                                                <input type="radio" id="first-rate2"
                                                                    name="review[rating]" value="2" />
                                                                <label for="first-rate2" title="Not good"></label>
                                                                <input type="radio" id="first-rate3"
                                                                    name="review[rating]" value="3" />
                                                                <label for="first-rate3" title="Average"></label>
                                                                <input type="radio" id="first-rate4"
                                                                    name="review[rating]" value="4" />
                                                                <label for="first-rate4" title="Very good"></label>
                                                                <input type="radio" id="first-rate5"
                                                                    name="review[rating]" value="5" />
                                                                <label for="first-rate5" title="Amazing"></label>
                                                            </fieldset>
                                                            <div>
                                                                <textarea class="form-control mb-3" id="body" cols="30"
                                                                    rows="3"
                                                                    placeholder="Leave your review here."></textarea>
                                                            </div>
                                                            <button class="btn btn-success"
                                                                type="submit">Submit</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <a href="home" class="w-100 btn btn-lg btn-danger">Back to Home</a>
                                    </div>
                                </div>

                    </main>
                    <!-- Bootstrap -->
                    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
                    <!-- Include all compiled plugins (below), or include individual files as needed -->
                    <script src="js/bootstrap.min.js"></script>
                </body>

                </html>