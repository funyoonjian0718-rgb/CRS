package model;

public class Student {

    private int studentId;
    private String studentName;
    private double cgpa;
    private int failedCourses;
    private boolean eligible;

    public Student(){}

    public int getStudentId(){
        return studentId;
    }

    public void setStudentId(int studentId){
        this.studentId = studentId;
    }

    public String getStudentName(){
        return studentName;
    }

    public void setStudentName(String studentName){
        this.studentName = studentName;
    }

    public double getCgpa(){
        return cgpa;
    }

    public void setCgpa(double cgpa){
        this.cgpa = cgpa;
    }

    public int getFailedCourses(){
        return failedCourses;
    }

    public void setFailedCourses(int failedCourses){
        this.failedCourses = failedCourses;
    }

    public boolean isEligible(){
        return eligible;
    }

    public void setEligible(boolean eligible){
        this.eligible = eligible;
    }
}