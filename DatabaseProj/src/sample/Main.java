package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
        try {
            Connection conn = DriverManager.getConnection("" +
                    "" +
                    "jdbc:sqlite:/Users/Sangeetha/DatabaseProj/Student.db");
            Statement statement = conn.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS test (name TEXT, ID INTEGER)");

            statement.execute("INSERT INTO test(name, ID) " + "VALUES('Sangeetha', 666)");

            statement.close();
            conn.close();

        } catch(SQLException e) {
            System.out.println("Something went wrong..." + e.getMessage());
        }
    }
}