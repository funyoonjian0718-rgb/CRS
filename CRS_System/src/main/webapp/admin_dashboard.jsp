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

<h2 class="text-center mb-4">Course Administrator Dashboard</h2>   <%--allhamdullilah --%>

<div class="row">

<div class="col-md-4 mb-3">
<button class="btn btn-primary w-100">Manage Users</button>
</div>

<div class="col-md-4 mb-3">
<button class="btn btn-success w-100">Create Recovery Plan</button>
</div>

<div class="col-md-4 mb-3">
<button class="btn btn-warning w-100">Update Recovery Plan</button>
</div>

<div class="col-md-4 mb-3">
<button class="btn btn-info w-100">Track Recovery Progress</button>
</div>

<div class="col-md-4 mb-3">
<button class="btn btn-secondary w-100">Send Notifications</button>
</div>

<div class="col-md-4 mb-3">
<a href="logout" class="btn btn-danger w-100">Logout</a>
</div>

</div>

</div>

</body>
</html>