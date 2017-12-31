package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

/**
 * Created by Sangeetha on 12/12/17.
 */
public class Datasource {
    public static final String DB_NAME ="Student.db";
    public static final String CONNECTION_STRING =  "jdbc:sqlite:" + DB_NAME;


    public static final String TABLE_COURSE ="courses";
    public static final String COLUMN_COURSE_NAME = "courseName";
    public static final String COLUMN_COURSE_ID = "courseID";
    public static final String COLUMN_PREREQUISITES = "prerequisites";

    public static final int INDEX_COURSE_ID = 1;
    public static final int INDEX_COURSE_NAME =2;
    public static final int INDEX_COURSE_PREREQ=3;
    public static final int INDEX_COURSE_CHECKACADEMIC =4;


    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_STUDENT_ID ="id";
    public static final String COLUMN_STUDENT_NAME="name";
    public static final String COLUMN_BALANCE="balance";
    public static final String COLUMN_ACADEMIC_STANDING ="academicStanding";
    public static final String COLUMN_STUDENT_CURRENT_COURSE= "currentCourse";

    public static final int INDEX_STUDENT_ID = 1;
    public static final int INDEX_STUDENT_NAME = 3;
    public static final int INDEX_STUDENT_BALANCE =2;
    public static final int INDEX_STUDENT_ACADEMIC_STANDING =4;
    public static final int INDEX_STUDENT_CURRENT_COURSE=5;

    public static final int ORDER_BY_NONE =1;
    public static final int ORDER_BY_ASC =2;
    public static final int ORDER_BY_DESC =3;

    private Connection conn;
    public boolean open(){
        try{
            // Connection is here
            conn= DriverManager.getConnection(CONNECTION_STRING);
            return true;
        }catch(SQLException e){
            System.out.println("Couldn't connect to database:"+ e.getMessage());
            return false;
        }
    }

    public void close(){
        try{
            if(conn!=null){
                conn.close();
            }
        }catch (SQLException e){
            System.out.println("Couldn't close connection" +e.getMessage());
        }
    }


    public void insertStudent(String name, int ID , String academicStanding, double balance) {
//        INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY)
//        VALUES (2, 'Allen', 25, 'Texas', 15000.00 );
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(TABLE_STUDENT);
        sb.append('(');
        sb.append(COLUMN_STUDENT_NAME);
        sb.append(',');
        sb.append(COLUMN_STUDENT_ID);
        sb.append(',');
        sb.append(COLUMN_ACADEMIC_STANDING);
        sb.append(',');
        sb.append(COLUMN_BALANCE);
        sb.append(')');
        sb.append("VALUES");
        sb.append('(');
        sb.append('"');
        sb.append(name);
        sb.append('"');
        sb.append(',');
        sb.append(ID);
        sb.append(',');
        sb.append('"');
        sb.append(academicStanding);
        sb.append('"');
        sb.append(',');
        sb.append(balance);
        sb.append(')');

       //sb.append(';');
       // System.out.println(sb.toString());
       try(Statement statement = conn.createStatement();) {
           statement.executeUpdate(sb.toString());

       } catch (SQLException e) {
           System.out.println("Query failed " + e.getMessage());
       }
    }

    public void insertCourse(String name, int courseID , String prereq) {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(TABLE_COURSE);
        sb.append('(');
        sb.append(COLUMN_COURSE_NAME);
        sb.append(',');
        sb.append(COLUMN_COURSE_ID);
        sb.append(',');
        sb.append(COLUMN_PREREQUISITES);
        sb.append(')');
        sb.append("VALUES");
        sb.append('(');
        sb.append('"');
        sb.append(name);
        sb.append('"');
        sb.append(',');
        sb.append(courseID);
        sb.append(',');
        sb.append('"');
        sb.append(prereq);
        sb.append('"');
        sb.append(')');

        // System.out.println(sb.toString());
        try(Statement statement = conn.createStatement();) {
            statement.executeUpdate(sb.toString());

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        }
    }
    public List<Student> queryStudents()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_STUDENT)) {
            List<Student> students = new ArrayList<>();

            while (resultSet.next())
            {
                Student student = new Student();
                student.setStudentId(resultSet.getInt(INDEX_STUDENT_ID));
                student.setName(resultSet.getString(INDEX_STUDENT_NAME));
                student.setBalance(resultSet.getDouble(INDEX_STUDENT_BALANCE));
                student.setCurrentCourse(resultSet.getInt(INDEX_STUDENT_CURRENT_COURSE));
                student.setAcademicStanding(resultSet.getString(INDEX_STUDENT_ACADEMIC_STANDING));
                students.add(student);
            }
            return students;

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }




    public List<Student> queryStudentByID(int id){

        try(Statement statement = conn.createStatement(); //we use resultSet because we expect a result
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_STUDENT + " " + "WHERE id= " + id)){//"ORDER BY " + sortOrder)){

            List<Student> students = new ArrayList<>();

            while(resultSet.next()) {
                Student student = new Student();
                student.setName(resultSet.getString(INDEX_STUDENT_NAME));
                student.setStudentId(resultSet.getInt(INDEX_STUDENT_ID));
                student.setBalance((resultSet.getDouble(INDEX_STUDENT_BALANCE)));
                student.setAcademicStanding(resultSet.getString(INDEX_STUDENT_ACADEMIC_STANDING));
                students.add(student);
            }
            return students;

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }

    public List <Course> queryCourseByID (int courseID) {

        try (Statement statement = conn.createStatement(); //we use resultSet because we expect a result
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_COURSE + " " + "WHERE courseID= " + courseID)) {//"ORDER BY " + sortOrder)){

            List<Course> courses = new ArrayList<>();

            while (resultSet.next()) {
                Course course = new Course();
                course.setName(resultSet.getString(INDEX_COURSE_NAME));
                course.setId(resultSet.getInt(INDEX_COURSE_ID));
                course.setPrerequisites(resultSet.getString(INDEX_COURSE_PREREQ));
                courses.add(course);
            }
            return courses;

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }
    public boolean updateStudentTable(int studentID, int requestedCourseID){
        String sqlUpdate = "UPDATE " + TABLE_STUDENT + " SET " + COLUMN_STUDENT_CURRENT_COURSE + " = " + requestedCourseID + " WHERE " + COLUMN_STUDENT_ID + " = "+
                studentID;
        try(Statement statement =conn.createStatement();){
            statement.executeUpdate(sqlUpdate);
            return true;

        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }

    }

    public boolean HasBalance(int studentID) {
        try (Statement statement = conn.createStatement();
             ResultSet r = statement.executeQuery("SELECT " + COLUMN_BALANCE + " FROM " + TABLE_STUDENT + " WHERE " +  COLUMN_STUDENT_ID + " = " + studentID )) {

            if (r.getDouble(COLUMN_BALANCE) > 0) {  //
                //Student has a balance
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("HasBalance failed: " + e.getMessage());
            return false;
        }
    }

    public double payBalance(int studentID, double payment) {
    try(Statement statement= conn.createStatement();
        ResultSet r= statement.executeQuery("SELECT "+ COLUMN_BALANCE + " FROM " + TABLE_STUDENT + " WHERE " +  COLUMN_STUDENT_ID + " = " + studentID )){
        double bal = r.getDouble(COLUMN_BALANCE);
        if(r.getDouble(COLUMN_BALANCE)>0) {
            bal -= payment;
        }
        return abs(bal);
       //
    }catch(SQLException e){
        System.out.println("payBalance failed" + e.getMessage());
        return -1;

    }

    }
    public void UpdateBalance(int studentID, double bal){
        try(Statement statement = conn.createStatement();){
          statement.executeUpdate("UPDATE "+ TABLE_STUDENT + " SET " + COLUMN_BALANCE + " = " + bal + " WHERE " + COLUMN_STUDENT_ID + " = " + studentID);

        }catch (SQLException e){
            System.out.println("UpdateBalance failed" + e.getMessage());
        }

    }





    //compare your current courses to course's pre-requisites
    public boolean HasMetPreReq(int studentID, int courseID ){
        try(Statement statement=conn.createStatement();
        ResultSet r=statement.executeQuery("SELECT " + COLUMN_PREREQUISITES + " FROM " + TABLE_COURSE + " WHERE courseID = " + courseID );
        ResultSet currentCourse= statement.executeQuery("SELECT " + COLUMN_STUDENT_CURRENT_COURSE + " FROM " + TABLE_STUDENT + " WHERE " + COLUMN_STUDENT_ID + " = "+ studentID)){
        if(r.getString(COLUMN_COURSE_ID).equals(currentCourse.getString(COLUMN_STUDENT_CURRENT_COURSE)))
            return true;
        return false;
        }catch(SQLException e){
            System.out.println("HasMetPreReq failed: " + e.getMessage());
            return false;
        }



    }

    public boolean academicStatus(int studentID) {
        //returns if academic standing is good or bad , select academic standing and returning that
        try(Statement statement =conn.createStatement();
            ResultSet result= statement.executeQuery(" SELECT " + COLUMN_ACADEMIC_STANDING + " FROM "+ TABLE_STUDENT + " WHERE " + COLUMN_STUDENT_ID + " = " + studentID)){
            if(result.getString(COLUMN_ACADEMIC_STANDING).equals("YES")) {
                return true;
            }
            return false;
        }catch (SQLException e){
            System.out.println("academicStatus failed " + e.getMessage());
            return false;
        }

    }

    public boolean registerforCourse(int studentID, int courseRequested){

         if( HasMetPreReq(studentID, courseRequested) && academicStatus(studentID) && !HasBalance(studentID) /* ==false */ ){
               return updateStudentTable(studentID, courseRequested);

           }

           return false;

        }

}






