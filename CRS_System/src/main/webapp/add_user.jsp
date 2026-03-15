<!DOCTYPE html>
<html>

<head>

    <title>Add User</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>

        body{
            background: linear-gradient(135deg,#f5f7fa,#c3cfe2);
        }

        .dashboard-card{
            border-radius:12px;
        }

    </style>

</head>

<body>

    <div class="container mt-5" style="max-width:600px;">

        <div class="card dashboard-card shadow">

            <div class="card-header">
                <h3 class="mb-0">Add New User</h3>
            </div>

            <div class="card-body">

                <form action="addUser" method="post">


                    <!-- ============================= -->
                    <!-- Username -->
                    <!-- ============================= -->

                    <div class="mb-3">
                        <label class="form-label">Username</label>

                        <input
                            type="text"
                            name="username"
                            class="form-control"
                            required
                        >
                    </div>



                    <!-- ============================= -->
                    <!-- Password -->
                    <!-- ============================= -->

                    <div class="mb-3">
                        <label class="form-label">Password</label>

                        <input
                            type="password"
                            name="password"
                            class="form-control"
                            required
                        >
                    </div>



                    <!-- ============================= -->
                    <!-- Email -->
                    <!-- ============================= -->

                    <div class="mb-3">
                        <label class="form-label">Email</label>

                        <input
                            type="email"
                            name="email"
                            class="form-control"
                        >
                    </div>



                    <!-- ============================= -->
                    <!-- Role -->
                    <!-- ============================= -->

                    <div class="mb-3">

                        <label class="form-label">Role</label>

                        <select name="role" class="form-control">

                            <option value="course_admin">
                                Course Admin
                            </option>

                            <option value="academic_officer">
                                Academic Officer
                            </option>

                        </select>

                    </div>



                    <!-- ============================= -->
                    <!-- Buttons -->
                    <!-- ============================= -->

                    <button class="btn btn-success">
                        Add User
                    </button>

                    <a href="manage_users.jsp" class="btn btn-secondary">
                        Cancel
                    </a>


                </form>

            </div>

        </div>

    </div>

</body>

</html>