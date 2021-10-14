package ui;

import controller.DatabaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;

public class Main extends Application {

    private final DatabaseController databaseController;
    private Database database;

    public Main() {
        database = new Database();
        databaseController = new DatabaseController(database);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scenes/main.fxml"));
        fxmlLoader.setController(databaseController);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Basketball Database");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
