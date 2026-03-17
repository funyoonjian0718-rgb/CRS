<html>

<head>

    <title>CRS Login</title>

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

    <div class="container">

        <div class="row justify-content-center">

            <div class="col-md-4">

                <div class="card dashboard-card shadow mt-5">

                    <div class="card-header text-center">

                        <h3 class="mb-0">
                            CRS Login
                        </h3>

                    </div>

                    <div class="card-body">


                        <!-- ============================= -->
                        <!-- LOGIN ERROR MESSAGE -->
                        <!-- ============================= -->

                        <%
                            String error = request.getParameter("error");

                            if(error != null){
                        %>

                        <div class="alert alert-danger">
						Invalid username or password
						</div>

                        <%
                            }
                        %>



                        <!-- ============================= -->
                        <!-- LOGIN FORM -->
                        <!-- ============================= -->

                        <form action="login" method="post">

                            <div class="mb-3">

                                <label class="form-label">
                                    Username
                                </label>

                                <input
                                    type="text"
                                    name="username"
                                    class="form-control"
                                >

                                <%-- admin is admin123 officer is officer123 --%>

                            </div>



                            <div class="mb-3">

                                <label class="form-label">
                                    Password
                                </label>

                                <input
                                    type="password"
                                    name="password"
                                    class="form-control"
                                >

                            </div>



                            <button class="btn btn-primary w-100">
                                Login
                            </button>
                            
                        </form>
                             <div class="text-center mt-3">
							    <a href="forgot_password.jsp">Forgot Password?</a>
							</div>
                    </div>

                </div>

            </div>

        </div>

    </div>

</body>

</html>