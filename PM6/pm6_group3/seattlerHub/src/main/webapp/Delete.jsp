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
<title>Delete Housing</title>
</head>
<body class="text-center" style="background: rgb(236, 236, 236);">

    <main class="d-flex justify-content-center align-items-center" style="width: 100%; height: 100vh;" }>
        <div class="card d-flex justify-content-center align-items-center"
            style="width: 30%; background: transparent; border: none">
            <div class="card-body" style="width: 70%;">
                
                <form action="delete" method="post">
                    <h1 class="h3 mb-3 fw-normal">Delete Housing : </h1>
                    <input class="w-100 btn btn-lg mb-3" id="housingid" name="housingid" value="${fn:escapeXml(param.housingid)}">
                    <button class="w-100 btn btn-lg btn-danger mb-3" type="submit">Delete</button>
                </form>

                <a href="./home" class="w-100 btn btn-lg btn-secondary">Back to Home</a>
            </div>
            <div class="card-footer text-muted" style="width: 100%; background: transparent; border: none">
                <p class="mb-3 text-muted">© SeattlerHub 2021</p>
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
