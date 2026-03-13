<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
if(session == null || session.getAttribute("username") == null){
    response.sendRedirect("login.jsp");
}
%>

<!DOCTYPE html>
<html>
<head>

<title>Admin Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-5">

<h2 class="text-center mb-4">Course Administrator Dashboard</h2>

<div class="row">

<!-- USER MANAGEMENT -->

<div class="col-md-4 mb-3">
<a href="viewUsers" class="btn btn-primary w-100">Manage Users</a>
</div>


<!-- ELIGIBILITY MANAGEMENT -->

<div class="col-md-4 mb-3">
<a href="checkEligibility" class="btn btn-info w-100">Check Student Eligibility</a>
</div>

<div class="col-md-4 mb-3">
<a href="enrollStudent" class="btn btn-secondary w-100">Enroll Student to Recovery</a>
</div>

<!-- REPORTS -->

<div class="col-md-4 mb-3">
<a href="viewReports" class="btn btn-dark w-100">Academic Performance Reports</a>
</div>

<!-- LOGOUT -->

<div class="col-md-4 mb-3">
<a href="logout" class="btn btn-outline-danger w-100">Logout</a>
</div>

</div>

</div>

</body>
</html>