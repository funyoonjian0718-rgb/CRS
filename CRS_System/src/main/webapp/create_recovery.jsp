<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>

<%
    if(session == null || session.getAttribute("username") == null){
        response.sendRedirect("login.jsp");
    }

    List<Student> students =
        (List<Student>) request.getAttribute("studentList");

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

<title>Create Recovery Plan</title>

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
Create Recovery Plan
</h3>


<form action="saveRecoveryPlan" method="post">


<!-- ============================= -->
<!-- Student Dropdown -->
<!-- ============================= -->

<label class="form-label">Select Student</label>

<select id="studentSelect" class="form-select mb-3">

<option value="">Select Student</option>

<%
if(students != null){
for(Student s : students){
%>

<option value="<%=s.getStudentId()%>">
<%=s.getStudentId()%> - <%=s.getStudentName()%>
(Failed: <%=s.getFailedCourses()%>)
</option>

<%
}
}
%>

</select>

<input type="hidden" name="student_id" id="selectedStudent">



<!-- ============================= -->
<!-- Failed Courses -->
<!-- ============================= -->

<hr>

<h5 class="mb-3">Failed Courses</h5>

<div id="failedCourses"></div>



<!-- ============================= -->
<!-- Recommendations -->
<!-- ============================= -->

<hr>

<h5 class="mb-3">Recommendations</h5>

<textarea
name="recommendations"
class="form-control"
rows="4"
placeholder="Enter recommendations for the student">
</textarea>



<!-- ============================= -->
<!-- Milestones -->
<!-- ============================= -->

<hr>

<h5 class="mb-3">Milestones</h5>

<table class="table" id="taskTable">

<thead>

<tr>
<th style="width:20%">Study Week</th>
<th style="width:70%">Task</th>
<th style="width:10%">Remove</th>
</tr>

</thead>

<tbody>

<tr>

<td>
<input type="text" name="study_week" class="form-control">
</td>

<td>
<input type="text" name="task_description" class="form-control">
</td>

<td class="text-center">

<button
type="button"
class="btn btn-danger btn-sm"
onclick="removeRow(this)">

X

</button>

</td>

</tr>

</tbody>

</table>

<button
type="button"
class="btn btn-secondary"
onclick="addRow()">

Add Task

</button>



<!-- ============================= -->
<!-- Save Button -->
<!-- ============================= -->

<div class="text-center mt-4">

<button class="btn btn-success px-5">
Save Recovery Plan
</button>

</div>

</form>



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



<script>

/* Load failed courses */

document.getElementById("studentSelect")
.addEventListener("change", function(){

    let studentId = this.value;

    document.getElementById("selectedStudent").value = studentId;

    if(studentId === "") return;

    fetch("createRecoveryPlan?student_id=" + studentId)

    .then(response => response.json())

    .then(data => {

        let container =
        document.getElementById("failedCourses");

        container.innerHTML = "";

        data.forEach(course => {

            container.innerHTML +=
            "<div class='form-check'>" +
            "<input class='form-check-input' type='radio' name='course_id' value='"+course+"' required>" +
            "<label class='form-check-label'>" + course + "</label>" +
            "</div>";

        });

    });

});


/* Add milestone row */

function addRow(){

let tableBody =
document.querySelector("#taskTable tbody");

let row = document.createElement("tr");

row.innerHTML =
"<td><input type='text' name='study_week' class='form-control'></td>" +
"<td><input type='text' name='task_description' class='form-control'></td>" +
"<td class='text-center'>" +
"<button type='button' class='btn btn-danger btn-sm' onclick='removeRow(this)'>X</button>" +
"</td>";

tableBody.appendChild(row);

}


/* Remove milestone row */

function removeRow(button){

let row = button.closest("tr");

let tableBody =
document.querySelector("#taskTable tbody");

let rows = tableBody.querySelectorAll("tr");

/* Prevent deleting last row */

if(rows.length === 1){

alert("At least one milestone is required.");

return;

}

/* Check if row has content */

let week =
row.querySelector("input[name='study_week']").value.trim();

let task =
row.querySelector("input[name='task_description']").value.trim();

if(week !== "" || task !== ""){

let confirmDelete =
confirm("This milestone contains data. Are you sure you want to delete it?");

if(!confirmDelete){
return;
}

}

row.remove();

}

</script>

</body>

</html>