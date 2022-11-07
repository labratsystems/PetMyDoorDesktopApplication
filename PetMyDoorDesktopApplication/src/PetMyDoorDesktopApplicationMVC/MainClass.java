/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Lab Rat Systems
 */
public class MainClass extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {       
        Parent root = FXMLLoader.load(getClass().getResource("view/SplashScreen.fxml"));
        this.setStage(stage, new Scene(root));
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public final FXMLLoader goToFrame(AnchorPane anchorPane, String path) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path));
        anchorPane = (AnchorPane) fxmlLoader.load();

        this.setStage(new Stage(), new Scene(anchorPane));
        return fxmlLoader;
    }
    
    public void setStage(Stage stage, Scene scene){
        stage.setTitle("Pet My Door");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
    
    public void close(){
        ButtonType yes = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("Não", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Tem certeza que deseja encerrar a aplicação?");
        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == yes){
            Platform.exit();
            System.exit(0);
        }else alert.close();
    }
    
    public void minimize(AnchorPane anchorPane){
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setIconified(true);
    }
    
    public final void logOut(AnchorPane anchorPane) throws IOException{     
        ButtonType yes = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("Não", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Tem certeza que deseja sair?");
        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == yes){
            anchorPane.getScene().getWindow().hide();
            anchorPane = new AnchorPane();
            this.goToFrame(anchorPane, "view/Login.fxml");
        }else alert.close();
    }
    
    public boolean isTagId(String string){
        char[] chars = string.toCharArray();
        for(char c : chars) if(Character.isDigit(c)) return true;
        return false;
    }
}
