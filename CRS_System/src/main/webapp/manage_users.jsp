<%@ page import="java.util.List" %>
<%@ page import="model.User" %>

<!DOCTYPE html>
<html>
<head>

<title>Manage Users</title>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow">

        <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">

            <h4 class="mb-0">Manage Users</h4>

            <a href="add_user.jsp" class="btn btn-success btn-sm">
                + Add User
            </a>

        </div>

        <div class="card-body">

            <table class="table table-bordered table-hover">

                <thead class="table-dark">

                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th width="180">Actions</th>
                    </tr>

                </thead>

                <tbody>

                <%
                    List<User> users = (List<User>) request.getAttribute("users");

                    if(users != null){

                        for(User u : users){
                %>

                    <tr>

                        <td><%=u.getUserId()%></td>

                        <td><%=u.getUsername()%></td>

                        <td><%=u.getEmail()%></td>

                        <td>
                            <span class="badge bg-primary">
                                <%=u.getRole()%>
                            </span>
                        </td>

                        <td>

                            <% if(u.getStatus().equals("active")){ %>

                                <span class="badge bg-success">Active</span>

                            <% } else { %>

                                <span class="badge bg-secondary">Inactive</span>

                            <% } %>

                        </td>

                        <td>

							<a href="editUser?id=<%=u.getUserId()%>"
							   class="btn btn-warning btn-sm">
							   Edit
							</a>

                           <a href="deactivateUser?id=<%=u.getUserId()%>"
							   class="btn btn-danger btn-sm"
							   onclick="return confirm('Deactivate this user?')">
							   Deactivate
							</a>

                        </td>

                    </tr>

                <%
                        }
                    }
                %>

                </tbody>

            </table>

        </div>

    </div>

</div>

</body>
</html>