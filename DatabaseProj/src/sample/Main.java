package sample;

import model.Datasource;
import model.Student;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);



    public static void main(String[] args) {


        Datasource datasource = new Datasource();

        if (!datasource.open()) {
            System.out.println("Can't open datasource");
            return;

        }
        List<Student> students = datasource.queryStudents();
        if (students == null) {
            System.out.println("No students!");
            return;


        }

        printStudents(students);

        while(true)
        {
            menu();
            int choice = scanner.nextInt();
            if(choice == 3)
                break;

            switch(choice)
            {
                case 1:
                    System.out.print("Please enter student ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Please enter the course you are trying to register for: ");
                    int courseId = scanner.nextInt();

                    if(datasource.registerforCourse(id, courseId) == true)
                    {
                        System.out.println("Course registration successful");
                    }
                    else
                    {
                        System.out.println("Unable to register for course");
                    }
                    break;

                case 2:
                    System.out.print("Please enter student ID: ");
                    int student_id = scanner.nextInt();
                    System.out.println("Please enter the amount you want to pay ");
                    int amount = scanner.nextInt();

                    datasource.payBalance(student_id, amount);

            }
        }


        System.out.println("\n");
        printStudents(students);






        /*
        //Query Student
        List<Student> students = datasource.queryStudentByID(666);
        if(students == null) {
            System.out.println("No Students");
            return;
        }

        for(Student student: students) {
            System.out.println("Student: " + student.getName() + "  ID:" + student.getStudentId() + "  Balance:"+ student.getBalance() + "  Academic Standing:"+ student.getAcademicStanding());

        }
        //datasource.insertStudent("Sangeetha", 666, "YES", 150.00);
        datasource.insertCourse("Calculus 2", 1206, "Calculus 1");
        //datasource.queryStudentByID(145);
        datasource.updateStudentTable(9999, 3110);
        datasource.HasBalance(9999);
        datasource.payBalance(9999, 50.5);
        datasource.UpdateBalance(9999, 50.5);
        datasource.HasMetPreReq(145, 1206 );

        */
        datasource.close();
    }

    public static void menu()
    {
        System.out.println("1. Register for course");
        System.out.println("2. Make payment");
        System.out.println("3. Quit");
        System.out.print("What would you like to do (enter choice number)? ");
    }


    public static void printStudents(List<Student> s){
        for (Student student : s) {
            System.out.println("ID = " + student.getStudentId() + ", Name = " + student.getName() + "," +" balance=" + student.getBalance()
            + " Academic Standing: " + student.getAcademicStanding() + " Current Course: " + student.getCurrentCourse());
        }
    }

}
















