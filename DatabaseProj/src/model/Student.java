package model;

/**
 * Created by Sangeetha on 12/12/17.
 */
public class Student {
    private int StudentId;
    private String name;
    private double balance;
    private String academicStanding;
    private int currentCourse;

    public int getCurrentCourse() { return currentCourse;}

    public void setCurrentCourse(int currentCourse) { this.currentCourse = currentCourse; }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAcademicStanding(){
        return academicStanding;
    }

    public void setAcademicStanding(String academicStanding) {
        this.academicStanding = academicStanding;
    }


}
