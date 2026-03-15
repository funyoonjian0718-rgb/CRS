<%@ page import="java.util.*,model.Student" %>

<!DOCTYPE html>
<html>

<head>

    <title>Student Eligibility Check</title>

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

        <div class="card dashboard-card shadow">

            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Student Eligibility Check</h4>
            </div>

            <div class="card-body">


                <!-- ============================= -->
                <!-- ELIGIBLE STUDENTS TABLE -->
                <!-- ============================= -->

                <h5 class="mb-3 text-success">
                    Students Eligible for Progression
                </h5>

                <table class="table table-striped table-hover">

                    <thead class="table-success">

                        <tr>
                            <th>Student ID</th>
                            <th>Name</th>
                            <th>CGPA</th>
                            <th>Failed Courses</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>

                    </thead>

                    <tbody>

                        <%
                            List<Student> eligibleList =
                                (List<Student>) request.getAttribute("eligibleStudents");

                            if(eligibleList != null && !eligibleList.isEmpty()){
                                for(Student s : eligibleList){
                        %>

                        <tr>

                            <td><%= s.getStudentId() %></td>
                            <td><%= s.getStudentName() %></td>
                            <td><%= s.getCgpa() %></td>
                            <td><%= s.getFailedCourses() %></td>

                            <td>
                                <span class="badge bg-success">
                                    Eligible
                                </span>
                            </td>

                            <td>

                                <form action="registerStudent" method="post">

                                    <input
                                        type="hidden"
                                        name="studentId"
                                        value="<%= s.getStudentId() %>"
                                    >

                                    <button class="btn btn-success btn-sm">
                                        Register
                                    </button>

                                </form>

                            </td>

                        </tr>

                        <%
                                }
                            } else {
                        %>

                        <tr>

                            <td colspan="6" class="text-center text-muted">
                                No eligible students found.
                            </td>

                        </tr>

                        <% } %>

                    </tbody>

                </table>



                <hr>



                <!-- ============================= -->
                <!-- NOT ELIGIBLE STUDENTS TABLE -->
                <!-- ============================= -->

                <h5 class="mb-3 text-danger">
                    Students Not Eligible for Progression
                </h5>

                <table class="table table-striped table-hover">

                    <thead class="table-dark">

                        <tr>
                            <th>Student ID</th>
                            <th>Name</th>
                            <th>CGPA</th>
                            <th>Failed Courses</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>

                    </thead>

                    <tbody>

                        <%
                            List<Student> list =
                                (List<Student>) request.getAttribute("students");

                            if(list != null && !list.isEmpty()){
                                for(Student s : list){
                        %>

                        <tr>

                            <td><%= s.getStudentId() %></td>
                            <td><%= s.getStudentName() %></td>
                            <td><%= s.getCgpa() %></td>
                            <td><%= s.getFailedCourses() %></td>

                            <td>
                                <span class="badge bg-danger">
                                    Not Eligible
                                </span>
                            </td>

                            <td>

                                <form action="enrollStudent" method="post">

                                    <input
                                        type="hidden"
                                        name="studentId"
                                        value="<%= s.getStudentId() %>"
                                    >

                                    <button class="btn btn-warning btn-sm">
                                        Enroll Recovery
                                    </button>

                                </form>

                            </td>

                        </tr>

                        <%
                                }
                            } else {
                        %>

                        <tr>

                            <td colspan="5" class="text-center text-muted">
                                No students found.
                            </td>

                        </tr>

                        <% } %>

                    </tbody>

                </table>



                <!-- ============================= -->
                <!-- BACK BUTTON -->
                <!-- ============================= -->

                <div class="mt-3">

                    <%-- check session token to see what are the user type --%>

                    <%
                        String role = (String) session.getAttribute("role");

                        String dashboard = "login.jsp";

                        if(role != null){
                            if(role.equals("course_admin")){
                                dashboard = "admin_dashboard.jsp";
                            }
                            else if(role.equals("academic_officer")){
                                dashboard = "officer_dashboard.jsp";
                            }
                        }
                    %>

                    <a href="<%=dashboard%>" class="btn btn-secondary btn-sm">
                        Back to Dashboard
                    </a>

                </div>


            </div>

        </div>

    </div>

</body>

</html>