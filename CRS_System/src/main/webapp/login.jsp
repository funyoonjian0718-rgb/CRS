<html>
<head>
<title>CRS Login</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container">

<div class="row justify-content-center">

<div class="col-md-4">

<div class="card mt-5">

<div class="card-header text-center">
<h3>CRS Login</h3>
</div>

<div class="card-body">

<%
String error = request.getParameter("error");
if(error != null){
%>

<p style="color:red;">Invalid username or password</p>

<%
}
%>

<form action="login" method="post">

<div class="mb-3">
<label>Username</label>
<input type="text" name="username" class="form-control">									<%--admin is admin123 officer is officer123 hehe balls --%>
</div>
<div class="mb-3">
<label>Password</label>
<input type="password" name="password" class="form-control">
</div>

<button class="btn btn-primary w-100">Login</button>

</form>

</div>

</div>

</div>

</div>

</div>

</body>
</html>