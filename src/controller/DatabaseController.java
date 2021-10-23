package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Database;
import model.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseController implements Initializable {

    @FXML
    private Button btnSearch;    

    @FXML
    private Button btnSearchByCategory;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnClose;

    @FXML
    private Rectangle modalOpaque;

    @FXML
    private TextField txtSearch;
    
    @FXML
    private TextField txtSearchByAssitss;

    @FXML
    private TextField txtSearchByBlocks;

    @FXML
    private TextField txtSearchByPoints;

    @FXML
    private TextField txtSearchByRebounds;

    @FXML
    private TextField txtSearchBySteals;

    @FXML
    private TableView<Player> tvPlayers;

    @FXML
    private TableColumn<Player, String> tcName;

    @FXML
    private TableColumn<Player, String> tcTeam;

    @FXML
    private TableColumn<Player, Integer> tcAge;

    @FXML
    private TableColumn<Player, Double> tcPoints;

    @FXML
    private TableColumn<Player, Double> tcRebounds;

    @FXML
    private TableColumn<Player, Double> tcAssists;

    @FXML
    private TableColumn<Player, Double> tcSteals;

    @FXML
    private TableColumn<Player, Double> tcBlocks;

    @FXML
    private Label lblPlayers;

    @FXML
    private Label lblSearchResult;

    private Database database;

    private double xOffset = 0;
    private double yOffset = 0;

    public DatabaseController(Database database) {
        this.database = database;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableView();
        preventColumnReordering(tvPlayers);
    }

    public static <T> void preventColumnReordering(TableView<T> tableView) {
        Platform.runLater(() -> {
            for (Node header : tableView.lookupAll(".column-header")) {
                header.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
            }
        });
    }

    public void initializeTableView() {
        txtSearch.setText("");
        lblSearchResult.setText("");
        btnSearch.setText("Search");
    	//database.getPlayersInList().clear();
    	//database.fromTreeToArrayList(database.getBinaryTreePlayers().getRoot());
        ObservableList<Player> playersObservableList = FXCollections.observableList(database.getPlayersInList());

        tcName.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        tcTeam.setCellValueFactory(new PropertyValueFactory<Player, String>("actualTeam"));
        tcAge.setCellValueFactory(new PropertyValueFactory<Player, Integer>("age"));
        tcPoints.setCellValueFactory(new PropertyValueFactory<Player, Double>("pointsPerGame"));
        tcRebounds.setCellValueFactory(new PropertyValueFactory<Player, Double>("reboundsPerGame"));
        tcAssists.setCellValueFactory(new PropertyValueFactory<Player, Double>("assistsPerGame"));
        tcSteals.setCellValueFactory(new PropertyValueFactory<Player, Double>("stealsPerGame"));
        tcBlocks.setCellValueFactory(new PropertyValueFactory<Player, Double>("blocksPerGame"));

        tvPlayers.setItems(playersObservableList);
        lblPlayers.setText("Players: " + database.getPlayersInList().size());

         	tvPlayers.setRowFactory(tv -> {
			TableRow<Player> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Player player = row.getItem();
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/scenes/editPlayer.fxml"));
        				EditPlayerController controller = new EditPlayerController(this);
        				fxmlLoader.setController(controller);
        				Parent root = fxmlLoader.load();
        				Stage stage = new Stage();
        				Scene scene = new Scene(root);
        				scene.getStylesheets().add("/ui/styles/newPlayer.css");
        				stage.setTitle("Remove Player");
        				stage.setResizable(false);
        				stage.setScene(scene);
        				stage.initStyle(StageStyle.TRANSPARENT);
        				stage.initModality(Modality.APPLICATION_MODAL);
        				modalOpaque.setVisible(true);
        				stage.show();	
        				
        				controller.initializeWindow(player);
						
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});
			return row;
		});         
    }
    
    @FXML
    public void handleMouseClick(MouseEvent event) {//Para cerrar o minimizar la ventana main.fxml
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        if (event.getSource() == btnClose) {
            stage.close();
            System.exit(0);
        } else if (event.getSource() == btnMinimize) {
            stage.setIconified(true);
        }
    }

    @FXML
    public void showAddPlayer(ActionEvent event) throws IOException {//Abre newPlayer.fxml
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
    public void search() {
        if(!txtSearch.getText().isEmpty()) {//Si el textfield NO está vacío
            if (btnSearch.getText().equals("Search")) {
                String originalSearch = txtSearch.getText();

                long startTime = System.nanoTime();
                Player searchResult = database.findPlayer(txtSearch.getText());
                //Player searchResult = database.findPlayer(database.getBinaryTreePlayers().getRoot(),txtSearch.getText());
                long endTime = System.nanoTime();
                double searchTime = (double)((endTime-startTime))/1000000;

                if (searchResult != null) {
                    List<Player> tempList = new ArrayList<>();
                    tempList.add(searchResult);

                    ObservableList<Player> clientsObservableList = FXCollections.observableList(tempList);

                    tvPlayers.setItems(clientsObservableList);
                    
                    btnSearch.setText("Clean Search");
                    lblSearchResult.setText("Search time: " + searchTime + " ms.");
                } else {
                    lblSearchResult.setText(originalSearch + " was not found in the database.");
                }
            } else {//Si el text es Clean Search
                txtSearch.setText("");
                btnSearch.setText("Search");
                lblSearchResult.setText("");
                initializeTableView();
            }
        }
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {//Enter para buscar el jugador
        if(event.getCode().equals(KeyCode.ENTER)) {
            if(!txtSearch.getText().isEmpty()) {
                search();
            }
        }
    }
    

    @FXML
    void searchByCategory(ActionEvent event) {

    }

    @FXML
    public void importPlayers(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        File file = fileChooser.showOpenDialog(lblPlayers.getScene().getWindow());

        if (file != null) {
            database.importPlayers(file.getPath(), ",");
            initializeTableView();
        }        
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
