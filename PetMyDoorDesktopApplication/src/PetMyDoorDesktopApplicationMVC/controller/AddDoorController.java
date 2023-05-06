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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lab Rat Systems
 */
public class AddDoorController extends DoorController implements Initializable {
    
    @FXML
    private Button btn_registerOrEditDoor;
    
    @FXML
    private Button btn_deleteDoor;
    
    @FXML
    private TextField txt_doorLocation;
    
    @FXML
    private Label lbl_registerOrEditDoor;
    
    @FXML
    private AnchorPane anchorPane;
    
    private Porta porta = new Porta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doorDAO.setConnection(connection);
    }
    
    public void AddDoorController(Usuario usuario, Porta porta){
        this.setUsuario(usuario);
        this.setPorta(porta);
        if(this.porta != null) {
            this.btn_deleteDoor.visibleProperty().set(true);
            this.lbl_registerOrEditDoor.setText("Alterar Porta");
            this.btn_registerOrEditDoor.setText("Alterar");
            this.txt_doorLocation.setText(this.porta.getLocalizacao());
            this.txt_doorLocation.requestFocus();
        }
    }
    
    public Porta getPorta(){
        return this.porta;
    }
    
    public void setPorta(Porta porta){
        this.porta = porta;
    }

    public void btn_registerOrEditDoor_onAction() throws IOException{
        if(this.txt_doorLocation.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("[ERRO] Preencha todos os campos!");
            alert.show();
        }
        else if(this.porta == null) {
            Porta porta = new Porta();
            usuario.setLogin(this.usuario.getLogin());
            porta.setLocalizacao(this.txt_doorLocation.getText());
            if(doorDAO.createDoor(porta, usuario)){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Porta cadastrada com sucesso!");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("[ERRO] Não foi possível cadastrar porta!");
                alert.show();
            }
            this.txt_doorLocation.clear();
            this.txt_doorLocation.requestFocus();
        }
        else{
            Porta porta = new Porta();
            porta.setIdPorta(this.porta.getIdPorta());
            porta.setLocalizacao(this.txt_doorLocation.getText());
            if(doorDAO.editDoor(porta)){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Porta alterada com sucesso!");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("[ERRO] Não foi possível alterar porta!");
                alert.show();
            }
            this.txt_doorLocation.requestFocus();
        }
    }
    
    public void btn_deleteDoor_onAction() throws IOException{
        if(doorDAO.deleteDoor(this.porta)){
            anchorPane.getScene().getWindow().hide();
            anchorPane = new AnchorPane();
            DoorController doorController = mainClass.goToFrame(anchorPane, "view/Door.fxml").getController();
            doorController.DoorController(usuario);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Porta excluída com sucesso!");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("[ERRO] Não foi possível excluir porta!");
            alert.show();
        }
    }
    
    @Override
    public void img_close_onMouseClicked(){
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        DoorController doorController = new DoorController();
        try {
            doorController = mainClass.goToFrame(anchorPane, "view/Door.fxml").getController();
        } catch (IOException ex) {
            Logger.getLogger(AddDoorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        doorController.DoorController(usuario);
    }
    
    @Override
    public void img_minimize_onMouseClicked(){
        mainClass.minimize(anchorPane);
    }
}