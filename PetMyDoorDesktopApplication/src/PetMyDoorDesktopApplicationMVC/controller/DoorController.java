/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.controller;

import PetMyDoorDesktopApplicationMVC.MainClass;
import PetMyDoorDesktopApplicationMVC.model.dao.DoorDAO;
import PetMyDoorDesktopApplicationMVC.model.database.Database;
import PetMyDoorDesktopApplicationMVC.model.database.DatabaseFactory;
import PetMyDoorDesktopApplicationMVC.model.domain.Porta;
import PetMyDoorDesktopApplicationMVC.model.domain.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lab Rat Systems
 */
public class DoorController implements Initializable {
    @FXML
    private TableView<Porta> tableView_door;
    
    @FXML
    private TableColumn<Porta, Integer> tableColumn_doorId;
    
    @FXML
    private TableColumn<Porta, String> tableColumn_doorLocation;
    
    @FXML
    private Label lbl_helloUser;
    
    @FXML
    private AnchorPane anchorPane;
    
    protected Usuario usuario = new Usuario();
    private List<Porta> listDoor;
    private ObservableList<Porta> observableListDoor;
    protected Database database = DatabaseFactory.getDatabase();
    protected Connection connection = database.connect();
    protected DoorDAO doorDAO = new DoorDAO();
    protected MainClass mainClass = new MainClass();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doorDAO.setConnection(connection);
        this.listTableViewDoor();
    }
    
    public void DoorController(Usuario usuario){
        this.setUsuario(usuario);
        this.lbl_helloUser.setText("Olá, " + this.getUsuario().getLogin());
        this.listTableViewDoor();
    }
    
    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void listTableViewDoor(){
        this.tableColumn_doorId.setCellValueFactory(new PropertyValueFactory<>("idPorta"));
        this.tableColumn_doorLocation.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        
        this.listDoor = doorDAO.read(usuario);
        
        this.observableListDoor = FXCollections.observableArrayList(listDoor);
        this.tableView_door.setItems(observableListDoor);
    }
    
    public void btn_addDoor_onAction() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        AddDoorController addDoorController = mainClass.goToFrame(anchorPane, "view/AddDoor.fxml").getController();
        addDoorController.AddDoorController(usuario, null);
    }
    
    public void btn_editDoor_onAction() throws IOException{
        Porta porta = this.tableView_door.getSelectionModel().getSelectedItem();
        if(porta != null){
            anchorPane.getScene().getWindow().hide();
            anchorPane = new AnchorPane();
            AddDoorController addDoorController = mainClass.goToFrame(anchorPane, "view/AddDoor.fxml").getController();
            addDoorController.AddDoorController(usuario, porta);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("[ERRO] Selecione um pet para alterá-lo!");
            alert.show();
        }
    }
    
    public void img_logOut_onMouseClicked() throws IOException{
        mainClass.logOut(anchorPane);
    }
    
    public void img_minimize_onMouseClicked(){
        mainClass.minimize(anchorPane);
    }
    
    public void img_close_onMouseClicked(){
        mainClass.close();
    }
    
    public void hbox_goToDashboardFrame_onMouseClicked() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        DashboardController dashboardController = mainClass.goToFrame(anchorPane, "view/Dashboard.fxml").getController();
        dashboardController.DashboardController(usuario);
    }
    
    public void hbox_goToPetFrame_onMouseClicked() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        PetController petController = mainClass.goToFrame(anchorPane, "view/Pet.fxml").getController();
        petController.PetController(usuario);
    }
}
