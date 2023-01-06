/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.controller;

import PetMyDoorDesktopApplicationMVC.MainClass;
import PetMyDoorDesktopApplicationMVC.model.dao.PetDAO;
import PetMyDoorDesktopApplicationMVC.model.database.Database;
import PetMyDoorDesktopApplicationMVC.model.database.DatabaseFactory;
import PetMyDoorDesktopApplicationMVC.model.domain.Pet;
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
public class PetController implements Initializable {  
    @FXML
    private TableView<Pet> tableView_pet;
    
    @FXML
    private TableColumn<Pet, String> tableColumn_petId;
    
    @FXML
    private TableColumn<Pet, String> tableColumn_petName;
    
    @FXML
    private Label lbl_helloUser;
    
    @FXML
    private AnchorPane anchorPane;
    
    protected Usuario usuario = new Usuario();
    private List<Pet> listPet;
    private ObservableList<Pet> observableListPet;
    protected Database database = DatabaseFactory.getDatabase();
    protected Connection connection = database.connect();
    protected PetDAO petDAO = new PetDAO();
    protected MainClass mainClass = new MainClass();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        petDAO.setConnection(connection);
        this.listTableViewPet();
    }
    
    public void PetController(Usuario usuario){
        this.setUsuario(usuario);
        this.lbl_helloUser.setText("Olá, " + this.getUsuario().getLogin());
        this.listTableViewPet();
    }
    
    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void listTableViewPet(){
        this.tableColumn_petId.setCellValueFactory(new PropertyValueFactory<>("idTag"));
        this.tableColumn_petName.setCellValueFactory(new PropertyValueFactory<>("nomePet"));
        
        this.listPet = petDAO.read(usuario);
        
        this.observableListPet = FXCollections.observableArrayList(listPet);
        this.tableView_pet.setItems(observableListPet);
    }
    
    public void btn_addPet_onAction() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        AddPetController addPetController = mainClass.goToFrame(anchorPane, "view/AddPet.fxml").getController();
        addPetController.AddPetController(usuario, null);
    }
    
    public void btn_editPet_onAction() throws IOException{
        Pet pet = this.tableView_pet.getSelectionModel().getSelectedItem();
        if(pet != null){
            anchorPane.getScene().getWindow().hide();
            anchorPane = new AnchorPane();
            AddPetController addPetController = mainClass.goToFrame(anchorPane, "view/AddPet.fxml").getController();
            addPetController.AddPetController(usuario, pet);
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
    
    public void hbox_goToDoorFrame_onMouseClicked() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        DoorController doorController = mainClass.goToFrame(anchorPane, "view/Door.fxml").getController();
        doorController.DoorController(usuario);
    }
}
