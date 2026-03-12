<!DOCTYPE html>
<html>
<head>

<title>Add User</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-5">

<div class="card">

<div class="card-header">
<h3>Add New User</h3>
</div>

<div class="card-body">

<form action="addUser" method="post">

<div class="mb-3">
<label>Username</label>
<input type="text" name="username" class="form-control" required>
</div>

<div class="mb-3">
<label>Password</label>
<input type="password" name="password" class="form-control" required>
</div>

<div class="mb-3">
<label>Email</label>
<input type="email" name="email" class="form-control">
</div>

<div class="mb-3">
<label>Role</label>
<select name="role" class="form-control">

<option value="course_admin">Course Admin</option>
<option value="academic_officer">Academic Officer</option>

</select>
</div>

<button class="btn btn-success">Add User</button>

<a href="manage_users.jsp" class="btn btn-secondary">Cancel</a>

</form>

</div>

</div>

</div>

</body>
</html>