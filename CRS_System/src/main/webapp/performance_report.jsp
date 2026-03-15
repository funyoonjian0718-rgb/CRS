<%@ page import="java.util.List" %>
<%@ page import="model.PerformanceReport" %>

<%
    List<PerformanceReport> reportList =
        (List<PerformanceReport>) request.getAttribute("reportList");

    String studentName = (String) request.getAttribute("studentName");
    String studentId   = (String) request.getAttribute("studentId");
    String program     = (String) request.getAttribute("program");
    double cgpa        = (double) request.getAttribute("cgpa");

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

    <title>Academic Performance Report</title>

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

            <div class="card-body p-4">

                <h3 class="text-center mb-4 fw-bold">
                    Academic Performance Report
                </h3>


                <!-- ============================= -->
                <!-- Student Information -->
                <!-- ============================= -->

                <p><strong>Student Name:</strong> <%=studentName%></p>
                <p><strong>Student ID:</strong> <%=studentId%></p>
                <p><strong>Program:</strong> <%=program%></p>



                <!-- ============================= -->
                <!-- Semester Result Table -->
                <!-- ============================= -->

                <h5 class="mt-4">Semester 1</h5>

                <table class="table table-bordered table-striped mt-3">

                    <thead class="table-dark">

                        <tr>
                            <th>Course Code</th>
                            <th>Course Title</th>
                            <th>Credit Hours</th>
                            <th>Grade</th>
                            <th>Grade Point (4.0 Scale)</th>
                        </tr>

                    </thead>

                    <tbody>

                        <%
                            if(reportList != null){
                                for(PerformanceReport r : reportList){
                        %>

                        <tr>

                            <td><%=r.getCourseCode()%></td>
                            <td><%=r.getCourseTitle()%></td>
                            <td><%=r.getCreditHours()%></td>
                            <td><%=r.getGrade()%></td>
                            <td><%=r.getGradePoint()%></td>

                        </tr>

                        <%
                                }
                            }
                        %>

                    </tbody>

                </table>



                <!-- ============================= -->
                <!-- CGPA -->
                <!-- ============================= -->

                <h5 class="mt-3">
                    Cumulative GPA (CGPA): <%=cgpa%>
                </h5>



                <!-- ============================= -->
                <!-- Back Button -->
                <!-- ============================= -->

                <div class="mt-4">

                    <a href="<%=backPage%>" class="btn btn-secondary">
                        Back to Dashboard
                    </a>

                </div>


            </div>

        </div>

    </div>

</body>

</html>