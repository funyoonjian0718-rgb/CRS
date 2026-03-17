<html>

<head>

    <title>Forgot Password</title>

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

    <div class="container mt-5">

        <div class="row justify-content-center">

            <div class="col-md-4">

                <div class="card dashboard-card shadow p-4">

                    <h4 class="text-center mb-4">
                        Forgot Password
                    </h4>


                    <!-- ============================= -->
                    <!-- FORM -->
                    <!-- ============================= -->

                    <form action="forgotPassword" method="post">

                        <div class="mb-3">

                            <label class="form-label">
                                Email
                            </label>

                            <input
                                type="email"
                                name="email"
                                class="form-control"
                                required
                            >

                        </div>



                        <button class="btn btn-primary w-100">
                            Send OTP
                        </button>

                    </form>


                </div>

            </div>

        </div>

    </div>

</body>

</html>