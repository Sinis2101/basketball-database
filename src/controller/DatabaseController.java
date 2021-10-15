package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Database;
import java.io.IOException;

public class DatabaseController {

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnClose;

    private Database database;

    public DatabaseController(Database database) {
        this.database = database;
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        if (event.getSource() == btnClose) {
            stage.close();
            System.exit(0);
        } else if (event.getSource() == btnMinimize) {
            stage.setIconified(true);
        }
    }

    @FXML
    public void showAddPlayer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/scenes/newPlayer.fxml"));
        NewPlayerController controller = new NewPlayerController(this);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/ui/styles/newPlayer.css");
        stage.setTitle("New Player");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public Database getDatabase() {
        return database;
    }
}
