package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Database;

public class NewPlayerController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTeam;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private TextField txtPoints;

    @FXML
    private TextField txtRebounds;

    @FXML
    private TextField txtAssists;

    @FXML
    private TextField txtBlocks;

    private Database database;
    private DatabaseController databaseController;

    public NewPlayerController(DatabaseController databaseController) {
        database = databaseController.getDatabase();
        this.databaseController = databaseController;
    }


    @FXML
    void addPlayer(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }

}