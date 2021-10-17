package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Database;
import model.Player;

import java.util.ArrayList;

public class NewPlayerController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTeam;

    @FXML
    private TextField txtBirthdate;

    @FXML
    private TextField txtPoints;

    @FXML
    private TextField txtRebounds;

    @FXML
    private TextField txtAssists;

    @FXML
    private TextField txtSteals;

    @FXML
    private TextField txtBlocks;

    private double xOffset = 0;
    private double yOffset = 0;

    private Database database;
    private DatabaseController databaseController;

    public NewPlayerController(DatabaseController databaseController) {
        database = databaseController.getDatabase();
        this.databaseController = databaseController;
    }


    @FXML
    void addPlayer(ActionEvent event) {
        if(checkForm()) {
            goBack(null);
        }
    }

    private boolean checkForm() {
        ArrayList<TextField> emptyFields = new ArrayList<>();

        if(txtName.getText().isEmpty()) emptyFields.add(txtName);
        if(txtTeam.getText().isEmpty()) emptyFields.add(txtTeam);
        if(txtBirthdate.getText().isEmpty()) emptyFields.add(txtBirthdate);

        highlightEmptyFields(emptyFields);

        return emptyFields.isEmpty();
    }

    private void highlightEmptyFields(ArrayList<TextField> emptyFields) {
        for(TextField field : emptyFields) {
            field.setStyle("-fx-border-color: #ffa724;");
        }
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        TextField clickedField = (TextField) event.getSource();
        clickedField.setStyle("-fx-border-color: #16161c;");
    }

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) txtName.getScene().getWindow();
        databaseController.getModalOpaque().setVisible(false);
        stage.close();
    }

    @FXML
    void windowDragged(MouseEvent event) {
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    void windowPressed(MouseEvent event) {
        Stage stage = (Stage) txtName.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

}
