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

<title>Academic Officer Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-5">

<h2 class="text-center mb-4">Academic Officer Dashboard</h2>

<div class="row">

<div class="col-md-4 mb-3">
<button class="btn btn-primary w-100">View Students</button>
</div>

<div class="col-md-4 mb-3">
<button class="btn btn-success w-100">Check Eligibility</button>
</div>

<div class="col-md-4 mb-3">
<button class="btn btn-warning w-100">Generate Academic Report</button>
</div>

<div class="col-md-4 mb-3">
<button class="btn btn-info w-100">View Recovery Progress</button>
</div>

<div class="col-md-4 mb-3">
<a href="logout" class="btn btn-danger w-100">Logout</a>
</div>

</div>

</div>

</body>
</html>