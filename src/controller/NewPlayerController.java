package controller;

import model.Database;
import model.Player;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewPlayerController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTeam;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtDay;

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
    private LocalDate birthdate;

    public NewPlayerController(DatabaseController databaseController) {
        database = databaseController.getDatabase();
        this.databaseController = databaseController;
    }

    @FXML
    public void addPlayer(ActionEvent event) {
        if(checkForm()) {//Si todos los campos están completos
            Player player = new Player(txtName.getText(),
                    txtTeam.getText(),
                    birthdate,
                    Double.parseDouble(txtPoints.getText()),
                    Double.parseDouble(txtRebounds.getText()),
                    Double.parseDouble(txtAssists.getText()),
                    Double.parseDouble(txtSteals.getText()),
                    Double.parseDouble(txtBlocks.getText()));
            database.addPlayer(player);
            goBack(null);
            databaseController.initializeTableView();
        }
    }

    private LocalDate checkDate() {
        LocalDate tempBirthdate;
        try {
            int year = Integer.parseInt(txtYear.getText());
            int month = Integer.parseInt(txtMonth.getText());
            int day = Integer.parseInt(txtDay.getText());
            tempBirthdate = LocalDate.of(year, month, day);
        } catch (Exception e) {
            return null;
        }
        birthdate = tempBirthdate;
        return tempBirthdate;
    }

    private boolean checkForm() {
        ArrayList<TextField> emptyFields = new ArrayList<>();

        if(txtName.getText().isEmpty()) emptyFields.add(txtName);
        if(txtTeam.getText().isEmpty()) emptyFields.add(txtTeam);
        if(txtYear.getText().isEmpty()) emptyFields.add(txtYear);
        if(txtMonth.getText().isEmpty()) emptyFields.add(txtMonth);
        if(txtDay.getText().isEmpty()) emptyFields.add(txtDay);
        if(checkDate()==null) {
            emptyFields.add(txtYear);
            emptyFields.add(txtMonth);
            emptyFields.add(txtDay);
        }

        highlightEmptyFields(emptyFields);

        return emptyFields.isEmpty();
    }

    private void highlightEmptyFields(ArrayList<TextField> emptyFields) {
        for(TextField field : emptyFields) {
            field.setStyle("-fx-border-color: #ffa724;");
        }
    }
    
    @FXML
    public void goBack(ActionEvent event) { //Cancel button
        Stage stage = (Stage) txtName.getScene().getWindow();
        databaseController.getModalOpaque().setVisible(false);
        stage.close();
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {
        TextField clickedField = (TextField) event.getSource();
        clickedField.setStyle("-fx-border-color: #16161c;");
    }


    @FXML
    public void windowDragged(MouseEvent event) {
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    public void windowPressed(MouseEvent event) {
        Stage stage = (Stage) txtName.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

}
