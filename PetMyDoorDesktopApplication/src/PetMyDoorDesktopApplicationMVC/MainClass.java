/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author User
 */
public class MainClass extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {       
        Parent root = FXMLLoader.load(getClass().getResource("view/SplashScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Pet My Door");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void goToDashboard(AnchorPane anchorPane) throws IOException{
                            anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("view/Dashboard.fxml"));
                            
                            Scene scene = new Scene(anchorPane);
                            Stage stage = new Stage();
        
                            stage.setTitle("Pet My Door");
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(scene);
                            stage.show();
                            javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
    
}
