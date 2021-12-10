<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Edit Housing</title>
</head>
<body class="text-center" style="background: rgb(236, 236, 236);">
    <main class="d-flex justify-content-center align-items-center" style="width: 100%; height: 100vh;" }>
        <div class="card shadow-lg d-flex justify-content-center align-items-center"
            style="width: 30%; background: transparent; border: none">
            <div class="card-body" style="width: 70%;">
                
                
                
                <form action="edit" method="post">
                    <h1 class="h3 mb-3 fw-normal">Edit Housing</h1>
                   	
                   	<label for="housingid">housing id</label>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="housingid" name="housingid" value="${fn:escapeXml(param.housingid)}"> 
                    </div>
                   	
                   	
                   	
                   	<label for="housingname">Name</label>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="housingname" name="housingname" value=""> 
                    </div>
                    
                    
                    <label for="price">Price</label>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="price" name="price" value=""> 
                    </div>
                     
                    <p>${messages.success}</p>
                    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">Submit</button>
                    <a href="./home" class="w-100 btn btn-lg btn-danger">Back to Home</a>
                    
                </form>
                
           </div>
                
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
