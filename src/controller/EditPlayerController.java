package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Database;
import model.Player;

public class EditPlayerController {
	
	//Attributes
    private double xOffset = 0;
    private double yOffset = 0;

    //Relations
    private Database database;
    private DatabaseController databaseController;
    
    //Constructor
    public EditPlayerController(DatabaseController databaseController) {
    	database = databaseController.getDatabase();
    	this.databaseController = databaseController;
    }

    @FXML
    private TextField txtAssists;

    @FXML
    private TextField txtBlocks;

    @FXML
    private TextField txtDay;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPoints;

    @FXML
    private TextField txtRebounds;

    @FXML
    private TextField txtSteals;

    @FXML
    private TextField txtTeam;

    @FXML
    private TextField txtYear;

    private Player player;

    public void initializeWindow(Player player) {
        this.player = player;
    	txtName.setText(player.getName());
    	txtTeam.setText(player.getActualTeam());
    	txtYear.setText(Integer.toString(player.getBirthdate().getYear()));
    	txtMonth.setText(Integer.toString(player.getBirthdate().getMonthValue()));
    	txtDay.setText(Integer.toString(player.getBirthdate().getDayOfMonth()));
    	txtPoints.setText(Double.toString(player.getPointsPerGame()));
    	txtRebounds.setText(Double.toString(player.getReboundsPerGame()));
    	txtAssists.setText(Double.toString(player.getAssistsPerGame()));
    	txtSteals.setText(Double.toString(player.getStealsPerGame()));
    	txtBlocks.setText(Double.toString(player.getBlocksPerGame()));
        // Prompt text
        txtName.setPromptText(player.getName());
        txtTeam.setPromptText(player.getActualTeam());
        txtYear.setPromptText(Integer.toString(player.getBirthdate().getYear()));
        txtMonth.setPromptText(Integer.toString(player.getBirthdate().getMonthValue()));
        txtDay.setPromptText(Integer.toString(player.getBirthdate().getDayOfMonth()));
        txtPoints.setPromptText(Double.toString(player.getPointsPerGame()));
        txtRebounds.setPromptText(Double.toString(player.getReboundsPerGame()));
        txtAssists.setPromptText(Double.toString(player.getAssistsPerGame()));
        txtSteals.setPromptText(Double.toString(player.getStealsPerGame()));
        txtBlocks.setPromptText(Double.toString(player.getBlocksPerGame()));
    }

    @FXML
    public void editPlayer(ActionEvent event) {
    	if (checkForm()) {
            player.setName(txtName.getText());
            player.setActualTeam(txtTeam.getText());
            player.setBirthdate(LocalDate.of(Integer.parseInt(txtYear.getText()),Integer.parseInt(txtMonth.getText()),Integer.parseInt(txtDay.getText())));
            player.setPointsPerGame(Double.parseDouble(txtPoints.getText()));
            player.setReboundsPerGame(Double.parseDouble(txtRebounds.getText()));
            player.setAssistsPerGame(Double.parseDouble(txtAssists.getText()));
            player.setStealsPerGame(Double.parseDouble(txtSteals.getText()));
            player.setBlocksPerGame(Double.parseDouble(txtBlocks.getText()));
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
    public void removePlayer(ActionEvent event) {
    	if(!txtName.getText().isEmpty()) {//Si el textfield NO está vacío

    		database.deletePlayer(player);
    		goBack(null);
    		databaseController.initializeTableView();
    	}    	
    }   


    @FXML
    public void goBack(ActionEvent event) {//Cancel button
        Stage stage = (Stage) txtName.getScene().getWindow();
        databaseController.getModalOpaque().setVisible(false);
        stage.close();
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {//El color del borde del txt cambia dando la ilusión de que está activo
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
