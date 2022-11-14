/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.controller;

import PetMyDoorDesktopApplicationMVC.MainClass;
import PetMyDoorDesktopApplicationMVC.model.dao.UserDAO;
import PetMyDoorDesktopApplicationMVC.model.database.Database;
import PetMyDoorDesktopApplicationMVC.model.database.DatabaseFactory;
import PetMyDoorDesktopApplicationMVC.model.domain.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lab Rat Systems
 */
public class LoginController implements Initializable { 
    @FXML 
    private TextField txt_username;
    
    @FXML
    private PasswordField pwd_password;
    
    @FXML
    private AnchorPane anchorPane;
    
    private final Database database = DatabaseFactory.getDatabase();
    private final Connection connection = database.connect();
    private final UserDAO userDAO = new UserDAO();
    private final MainClass mainClass = new MainClass();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userDAO.setConnection(connection);
    }

    public void btn_login_onAction() throws IOException{
        if(this.txt_username.getText().isEmpty() || this.pwd_password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("[ERRO] Preencha todos os campos!");
            alert.show();
        }
        else{
            Usuario usuario = userDAO.checkLogin(this.txt_username.getText(), this.pwd_password.getText());
            if(usuario.getLogin() != null){
                if(usuario.getSenha() != null){
                    anchorPane.getScene().getWindow().hide();
                    anchorPane = new AnchorPane();
                    DashboardController dashboardController = mainClass.goToFrame(anchorPane, "view/Dashboard.fxml").getController();
                    dashboardController.DashboardController(usuario);
                }else{
                    this.pwd_password.clear();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("[ERRO] Senha incorreta! Digite novamente.");
                    alert.show();
                }
            }else{
                this.txt_username.clear();
                this.pwd_password.clear();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("[ERRO] Usuário inexistente!");
                alert.show();
            }
        }
    }
    
    public void img_close_onMouseClicked(){
        mainClass.close();
    }
    
    public void img_minimize_onMouseClicked(){
        mainClass.minimize(anchorPane);
    }
    
    public void lbl_register_onMouseClicked() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        mainClass.goToFrame(anchorPane, "view/Register.fxml");
    }
}
