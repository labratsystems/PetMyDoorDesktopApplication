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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lab Rat Systems
 */
public class RegisterController implements Initializable {    
    @FXML 
    private TextField txt_username;
    
    @FXML 
    private TextField txt_userEmail;
    
    @FXML 
    private TextField txt_userTelephone;
    
    @FXML
    private PasswordField pwd_password;
    
    @FXML
    private AnchorPane anchorPane;
    
    private final Database database = DatabaseFactory.getDatabase();
    private final Connection connection = database.connect();
    private final UserDAO usuarioDAO = new UserDAO();
    private final MainClass mainClass = new MainClass();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO.setConnection(connection);
    }

    public void btn_register_onAction() throws IOException{
        if(this.txt_username.getText().isEmpty() || this.pwd_password.getText().isEmpty() || this.txt_userEmail.getText().isEmpty() || this.txt_userTelephone.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("[ERRO] Preencha todos os campos!");
            alert.show();
        }
        else{
            Usuario usuario = new Usuario();
            usuario.setLogin(this.txt_username.getText());
            usuario.setSenha(this.pwd_password.getText());
            usuario.setEmail(this.txt_userEmail.getText());
            usuario.setTelefone(this.txt_userTelephone.getText());
            if(usuarioDAO.createUser(usuario)){
                anchorPane.getScene().getWindow().hide();
                anchorPane = new AnchorPane();
                DashboardController dashboardController = mainClass.goToFrame(anchorPane, "view/dashboard.fxml").getController();
                dashboardController.DashboardController(usuario);
            }else if(usuario == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("[ERRO] Usuário já existe!");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("[ERRO] Não foi possível cadastrar usuário!");
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
    
    public void lbl_login_onMouseClicked() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        mainClass.goToFrame(anchorPane, "view/Login.fxml");
    }
    
    public void txt_userEmail_onKeyPressed(){
        RegisterController.emailMask(txt_userEmail);
    }
    
    public void txt_userTelephone_onKeyPressed(){
        RegisterController.telephoneMask(txt_userTelephone);
    }
    
    public static void emailMask(TextField textField){
        textField.setOnKeyTyped((KeyEvent event) -> {
            if("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz._-@".contains(event.getCharacter()) == false) event.consume();
            if("@".equals(event.getCharacter())&&textField.getText().contains("@")) event.consume();
            if("@".equals(event.getCharacter())&&textField.getText().length() == 0) event.consume();
            if(".".equals(event.getCharacter())&&textField.getText().contains(".")) event.consume();
            if(".".equals(event.getCharacter())&&textField.getText().length() == 0) event.consume();
        });
    }

    public static void telephoneMask(TextField textField){
        textField.setOnKeyTyped((KeyEvent event) -> {
            if("0123456789".contains(event.getCharacter()) == false) event.consume();
            if(event.getCharacter().trim().length()==0){ // erasing
                if(textField.getText().length() == 10 && textField.getText().substring(9,10).equals("-")){
                    textField.setText(textField.getText().substring(0,9));
                    textField.positionCaret(textField.getText().length());
                }
                if(textField.getText().length() == 9 && textField.getText().substring(8,9).equals("-")){
                    textField.setText(textField.getText().substring(0,8));
                    textField.positionCaret(textField.getText().length());
                }
                if(textField.getText().length() == 4){
                    textField.setText(textField.getText().substring(0,3));
                    textField.positionCaret(textField.getText().length());
                }
                if(textField.getText().length() == 1) textField.setText("");
            }else{ //writing
                if(textField.getText().length() == 15) event.consume();
                if(textField.getText().length() == 0){
                    textField.setText("("+event.getCharacter());
                    textField.positionCaret(textField.getText().length());
                    event.consume();
                }
                if(textField.getText().length() == 3){
                    textField.setText(textField.getText() + ") " + event.getCharacter());
                    textField.positionCaret(textField.getText().length());
                    event.consume();
                }
                if(textField.getText().length() == 9){
                    textField.setText(textField.getText() + "-" + event.getCharacter());
                    textField.positionCaret(textField.getText().length());
                    event.consume();
                }
                if(textField.getText().length() == 10 && !"-".equals(textField.getText().substring(9,10))){
                    textField.setText(textField.getText() + "-" + event.getCharacter());
                    textField.positionCaret(textField.getText().length());
                    event.consume();
                }
                if(textField.getText().length() == 14 && textField.getText().substring(9,10).equals("-")){
                    textField.setText(textField.getText().substring(0,9) + textField.getText().substring(10,11) + "-" + textField.getText().substring(11,14) + event.getCharacter());
                    textField.positionCaret(textField.getText().length());
                    event.consume();
                }
            }
        });

        textField.setOnKeyReleased((KeyEvent evt) -> {
            if(!textField.getText().matches("\\d() -*")){
                textField.setText(textField.getText().replaceAll("[^\\d() -]", ""));
                textField.positionCaret(textField.getText().length());
            }
        });
    }
}
