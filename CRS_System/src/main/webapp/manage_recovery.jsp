<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    if(session == null || session.getAttribute("username") == null){
        response.sendRedirect("login.jsp");
    }

    /* detect role for back button */

    String role = (String) session.getAttribute("role");

    String backPage = "login.jsp";

    if(role != null){
        if(role.equals("course_admin")){
            backPage = "admin_dashboard.jsp";
        }
        else if(role.equals("academic_officer")){
            backPage = "officer_dashboard.jsp";
        }
    }
%>

<!DOCTYPE html>
<html>

<head>

    <title>Manage Recovery Plans</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>

        body{
            background: linear-gradient(135deg,#f5f7fa,#c3cfe2);
        }

        .dashboard-card{
            transition:0.3s;
            border-radius:12px;
        }

        .dashboard-card:hover{
            transform:translateY(-5px);
            box-shadow:0 10px 20px rgba(0,0,0,0.15);
        }

    </style>

</head>

<body>

    <div class="container mt-5">

        <h2 class="text-center mb-5 fw-bold">
            Recovery Plan Management
        </h2>

        <div class="row g-4 justify-content-center">


            <!-- ============================= -->
            <!-- CREATE RECOVERY PLAN -->
            <!-- ============================= -->

            <div class="col-md-4">

                <div class="card dashboard-card shadow text-center p-4">

                    <h5 class="mb-3">Create Recovery Plan</h5>

                    <a href="createRecoveryPlan" class="btn btn-success w-100">
                        Create Recovery Plan
                    </a>

                </div>

            </div>



            <!-- ============================= -->
            <!-- TRACK RECOVERY PROGRESS -->
            <!-- ============================= -->

            <div class="col-md-4">

                <div class="card dashboard-card shadow text-center p-4">

                    <h5 class="mb-3">Track Recovery Progress</h5>

                    <a href="viewRecoveryTasks" class="btn btn-dark w-100">
                        Track Recovery Progress
                    </a>

                </div>

            </div>


        </div>



        <!-- ============================= -->
        <!-- BACK TO DASHBOARD -->
        <!-- ============================= -->

        <div class="mt-4">

            <a href="<%=backPage%>" class="btn btn-outline-secondary px-5">
                Back to Dashboard
            </a>

        </div>


    </div>

</body>

</html>