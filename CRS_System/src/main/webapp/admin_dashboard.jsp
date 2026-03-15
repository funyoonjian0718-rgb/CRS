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
            Course Administrator Dashboard
        </h2>

        <div class="row g-4 justify-content-center">

            <!-- ============================= -->
            <!-- Manage Users -->
            <!-- ============================= -->
            <div class="col-md-4">

                <div class="card dashboard-card shadow text-center p-4">

                    <h5 class="mb-3">User Management</h5>

                    <a href="viewUsers" class="btn btn-primary w-100">
                        Manage Users
                    </a>

                </div>

            </div>


            <!-- ============================= -->
            <!-- Eligibility -->
            <!-- ============================= -->
            <div class="col-md-4">

                <div class="card dashboard-card shadow text-center p-4">

                    <h5 class="mb-3">Eligibility Check</h5>

                    <a href="checkEligibility" class="btn btn-info w-100">
                        Eligibility & Enrollment
                    </a>

                </div>

            </div>


            <!-- ============================= -->
            <!-- Performance Report -->
            <!-- ============================= -->
            <div class="col-md-4">

                <div class="card dashboard-card shadow text-center p-4">

                    <h5 class="mb-3">Academic Report</h5>

                    <form action="viewPerformanceReport">

                        <select name="student_id" class="form-select mb-3" required>

                            <option value="">Select Student</option>

                            <%
                                java.sql.Connection conn = util.DatabaseConnection.getConnection();
                                java.sql.Statement stmt = conn.createStatement();
                                java.sql.ResultSet rs = stmt.executeQuery(
                                    "SELECT student_id, name FROM students"
                                );

                                while(rs.next()){
                            %>

                                <option value="<%=rs.getString("student_id")%>">
                                    <%=rs.getString("student_id")%> - 
                                    <%=rs.getString("name")%>
                                </option>

                            <%
                                }
                            %>

                        </select>

                        <button class="btn btn-dark w-100">
                            Generate Report
                        </button>

                    </form>

                </div>

            </div>

        </div>


        <!-- Logout -->
        <div class="text-center mt-5">

            <a href="logout" class="btn btn-outline-danger px-5">
                Logout
            </a>

        </div>

    </div>

</body>

</html>