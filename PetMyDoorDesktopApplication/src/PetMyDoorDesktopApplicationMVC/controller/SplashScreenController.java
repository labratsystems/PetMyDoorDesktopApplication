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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author User
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
                }catch(Exception exception){
                    System.out.println(exception);
                }
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        try {
                            MainClass mainClass = new MainClass();
                            anchorPane.getScene().getWindow().hide();
                            anchorPane = new AnchorPane();
                            mainClass.goToDashboard(anchorPane);
                            
                        } catch (IOException ex) {
                            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }.start();
    }
}
