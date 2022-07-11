/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SplashScreenController implements Initializable {

    @FXML
    private Label lbl_loading;
    
    @FXML
    private AnchorPane ancPane_splashScreen;
    
    @FXML
    private AnchorPane ancPane_main;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        splash();
    } 
    
    private void splash(){
        new Thread(){
            @Override
            public void run(){
                try{
                    Thread.sleep(3000);
                }catch(Exception exception){
                    System.out.println(exception);
                }
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        System.out.println("Entrou aqui");
                        ancPane_splashScreen.getScene().getWindow().hide();
                        /*try {
                            ancPane_main = FXMLLoader.load(getClass().getResource(""));
                            Stage stage = new Stage();
                            Scene scene = new Scene(ancPane_main);
                            System.out.println("Entrou aqui");
                            ancPane_splashScreen.getScene().getWindow().hide();
                        } catch (IOException ex) {
                            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }*/
                    }
                });
            }
        }.start();
    }
}
