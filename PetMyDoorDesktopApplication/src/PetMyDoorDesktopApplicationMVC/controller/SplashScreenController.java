/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.controller;

import PetMyDoorDesktopApplicationMVC.MainClass;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lab Rat Systems
 */
public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

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
                }catch(InterruptedException exception){
                    System.out.println(exception);
                }
                Platform.runLater(() -> {
                    try {
                        anchorPane.getScene().getWindow().hide();
                        anchorPane = new AnchorPane();
                        new MainClass().goToFrame(anchorPane, "view/Login.fxml");
                        
                    } catch (IOException ex) {
                        Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }.start();
    }
}
