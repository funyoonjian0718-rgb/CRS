<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>

<%
    if(session == null || session.getAttribute("username") == null){
        response.sendRedirect("login.jsp");
    }

    List<String> students =
    (List<String>) request.getAttribute("studentList");

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

<title>Track Recovery Progress</title>

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
Track Recovery Progress
</h3>


<label>Select Student</label>

<select id="studentSelect" class="form-select mb-3">

<option value="">Select Student</option>

<%
if(students != null){
for(String s : students){

String[] parts = s.split("\\|");
%>

<option value="<%=parts[0]%>">
<%=parts[0]%> - <%=parts[1]%> (Active Plans: <%=parts[2]%>)
</option>

<%
}
}
%>

</select>


<label>Select Recovery Plan</label>

<div id="planList"></div>


<hr>

<h5>Recommendation</h5>

<div id="recommendationBox"></div>


<hr>

<h5>Progress</h5>

<p id="progressDisplay"></p>


<hr>

<h5>Course Status</h5>

<div id="courseStatusBox"></div>


<hr>

<h5>Plan Status</h5>

<div id="planStatusBox"></div>


<table class="table" id="taskTable">

<thead>

<tr>
<th>Study Week</th>
<th>Task</th>
<th>Status</th>
</tr>

</thead>

<tbody></tbody>

</table>


<div class="mt-4 d-flex gap-2">

<button id="editBtn" class="btn btn-warning">
Edit
</button>

<button id="saveBtn" class="btn btn-success d-none">
Save
</button>

<button id="cancelBtn" class="btn btn-secondary d-none">
Cancel
</button>

<a href="<%=backPage%>" class="btn btn-outline-dark">
Back to Dashboard
</a>

</div>


</div>
</div>
</div>


<script>

let currentPlanId = null;


/* Load plans */

document.getElementById("studentSelect")
.addEventListener("change", function(){

let studentId = this.value;

let planList = document.getElementById("planList");

currentPlanId = null;

if(studentId === ""){
planList.innerHTML="";
return;
}

fetch("viewRecoveryTasks?student_id=" + studentId)

.then(response => response.json())

.then(data => {

planList.innerHTML="";

data.forEach(plan =>{

let p=plan.split("|");

planList.innerHTML +=
"<div class='form-check'>" +
"<input class='form-check-input' type='radio' name='plan' value='"+p[0]+"' onclick='loadPlan(this.value)'>" +
"<label class='form-check-label'>"+
p[1]+" - Start Date: "+p[2]+
"</label></div>";

});

});

});


/* Load plan */

function loadPlan(planId){

currentPlanId = planId;

fetch("viewRecoveryTasks?plan_id=" + planId)

.then(response => response.json())

.then(data => {

document.getElementById("progressDisplay")
.innerText = "Progress: " + data.progress;

document.getElementById("courseStatusBox")
.innerHTML =
"<span class='badge bg-secondary'>"+data.course_status+"</span>";

document.getElementById("planStatusBox")
.innerHTML =
"<span class='badge bg-dark'>"+data.plan_status+"</span>";

let tbody =
document.querySelector("#taskTable tbody");

let recommendationBox =
document.getElementById("recommendationBox");

tbody.innerHTML="";
recommendationBox.innerHTML="";

let recommendationFound=false;

data.tasks.forEach(t=>{

let parts=t.split("|");

let week=parts[1];
let task=parts[2];
let status=parts[3];

if(status==="recommendation"){

recommendationFound=true;

recommendationBox.innerHTML=
"<div class='alert alert-info'>"+task+"</div>";

}

else{

tbody.innerHTML+=
"<tr>"+
"<td>"+week+"</td>"+
"<td>"+task+"</td>"+
"<td>"+status+"</td>"+
"</tr>";

}

});

if(!recommendationFound){

recommendationBox.innerHTML=
"<div class='alert alert-secondary'>No recommendations available.</div>";

}

});

}


/* Edit Mode */

document.getElementById("editBtn").addEventListener("click",function(){

if(currentPlanId===null){
alert("Please select a recovery plan first.");
return;
}

document.getElementById("studentSelect").disabled=true;

document.querySelectorAll("input[name='plan']")
.forEach(r=>r.disabled=true);

let recommendationText=
document.getElementById("recommendationBox").innerText.trim();

if(recommendationText==="No recommendations available.")
recommendationText="";

document.getElementById("recommendationBox").innerHTML=
"<textarea id='editRecommendation' class='form-control'>"+
recommendationText+
"</textarea>";

let currentCourseStatus=
document.getElementById("courseStatusBox").innerText.trim();

document.getElementById("courseStatusBox").innerHTML=
"<select id='editCourseStatus' class='form-select'>"+
"<option "+(currentCourseStatus==="pending"?"selected":"")+">pending</option>"+
"<option "+(currentCourseStatus==="passed"?"selected":"")+">passed</option>"+
"<option "+(currentCourseStatus==="failed"?"selected":"")+">failed</option>"+
"</select>";

let currentPlanStatus=
document.getElementById("planStatusBox").innerText.trim();

document.getElementById("planStatusBox").innerHTML=
"<select id='editPlanStatus' class='form-select'>"+
"<option "+(currentPlanStatus==="active"?"selected":"")+">active</option>"+
"<option "+(currentPlanStatus==="inactive"?"selected":"")+">inactive</option>"+
"</select>";

document.querySelectorAll("#taskTable tbody tr")
.forEach(row=>{

let week=row.cells[0].innerText;
let task=row.cells[1].innerText;
let status=row.cells[2].innerText;

row.cells[0].innerHTML=
"<input class='form-control' value='"+week+"'>";

row.cells[1].innerHTML=
"<input class='form-control' value='"+task+"'>";

row.cells[2].innerHTML=
"<select class='form-select'>"+
"<option "+(status==="pending"?"selected":"")+">pending</option>"+
"<option "+(status==="completed"?"selected":"")+">completed</option>"+
"</select>";

});

document.getElementById("editBtn").classList.add("d-none");
document.getElementById("saveBtn").classList.remove("d-none");
document.getElementById("cancelBtn").classList.remove("d-none");

});


/* Cancel */

document.getElementById("cancelBtn").addEventListener("click",function(){

loadPlan(currentPlanId);

document.getElementById("studentSelect").disabled=false;

document.querySelectorAll("input[name='plan']")
.forEach(r=>r.disabled=false);

document.getElementById("editBtn").classList.remove("d-none");
document.getElementById("saveBtn").classList.add("d-none");
document.getElementById("cancelBtn").classList.add("d-none");

});


/* Save */

document.getElementById("saveBtn").addEventListener("click",function(){

let recommendation =
document.getElementById("editRecommendation")?.value || "";

let courseStatus =
document.getElementById("editCourseStatus")?.value || "pending";

let planStatus =
document.getElementById("editPlanStatus")?.value || "active";

let rows=document.querySelectorAll("#taskTable tbody tr");

let tasks=[];

rows.forEach(row=>{

let week=row.cells[0].querySelector("input").value;

let task=row.cells[1].querySelector("input").value;

let status=row.cells[2].querySelector("select").value;

tasks.push({
study_week:week,
task_description:task,
status:status
});

});

fetch("updateRecoveryTasks",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify({
plan_id:currentPlanId,
recommendation:recommendation,
course_status:courseStatus,
plan_status:planStatus,
tasks:tasks
})

})
.then(res=>res.text())
.then(()=>{

loadPlan(currentPlanId);

document.getElementById("studentSelect").disabled=false;

document.querySelectorAll("input[name='plan']")
.forEach(r=>r.disabled=false);

document.getElementById("editBtn").classList.remove("d-none");
document.getElementById("saveBtn").classList.add("d-none");
document.getElementById("cancelBtn").classList.add("d-none");

});

});

</script>

</body>
</html>