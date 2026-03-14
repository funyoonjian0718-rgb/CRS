package model;

public class Student {

    private String studentId;
    private String studentName;
    private double cgpa;
    private int failedCourses;

    public Student() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getFailedCourses() {
        return failedCourses;
    }

    public void setFailedCourses(int failedCourses) {
        this.failedCourses = failedCourses;
    }
}