package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
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

    @FXML
    private Rectangle modalOpaque;

    private Database database;

    private double xOffset = 0;
    private double yOffset = 0;

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
        modalOpaque.setVisible(true);
        stage.show();
    }

    @FXML
    void windowDragged(MouseEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    void windowPressed(MouseEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    public Rectangle getModalOpaque() {
        return modalOpaque;
    }

    public Database getDatabase() {
        return database;
    }
}
