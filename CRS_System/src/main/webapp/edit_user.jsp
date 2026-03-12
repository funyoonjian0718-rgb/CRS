<%@ page import="model.User" %>

<%
User u = (User) request.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>

<title>Edit User</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-md-6">

<div class="card shadow">

<div class="card-header bg-warning text-dark">
<h4>Edit User</h4>
</div>

<div class="card-body">

<form action="updateUser" method="post">

<input type="hidden" name="userId" value="<%=u.getUserId()%>">

<div class="mb-3">
<label class="form-label">Username</label>
<input type="text" name="username" class="form-control"
       value="<%=u.getUsername()%>" required>
</div>

<div class="mb-3">
<label class="form-label">Email</label>
<input type="email" name="email" class="form-control"
       value="<%=u.getEmail()%>" required>
</div>

<div class="mb-3">
<label class="form-label">Role</label>

<select name="role" class="form-select">

<option value="course_admin"
<%= u.getRole().equals("course_admin") ? "selected" : "" %>>
Course Admin
</option>

<option value="academic_officer"
<%= u.getRole().equals("academic_officer") ? "selected" : "" %>>
Academic Officer
</option>

</select>

</div>

<div class="mb-3">
<label class="form-label">Status</label>

<select name="status" class="form-select">

<option value="active"
<%= u.getStatus().equals("active") ? "selected" : "" %>>
Active
</option>

<option value="inactive"
<%= u.getStatus().equals("inactive") ? "selected" : "" %>>
Inactive
</option>

</select>

</div>

<div class="d-grid gap-2">

<button type="submit" class="btn btn-primary">
Update User
</button>

<a href="viewUsers" class="btn btn-secondary">
Cancel
</a>

</div>

</form>

</div>

</div>

</div>

</div>

</div>

</body>
</html>