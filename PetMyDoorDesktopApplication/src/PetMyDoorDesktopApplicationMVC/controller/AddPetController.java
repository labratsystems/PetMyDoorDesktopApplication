/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.controller;

import PetMyDoorDesktopApplicationMVC.model.domain.Pet;
import PetMyDoorDesktopApplicationMVC.model.domain.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
public class AddPetController extends PetController implements Initializable {
    @FXML
    private Button btn_registerOrEditPet;
    
    @FXML
    private Button btn_deletePet;
    
    @FXML
    private TextField txt_petId;
    
    @FXML
    private TextField txt_petName;
    
    @FXML
    private Label lbl_registerOrEditPet;
    
    @FXML
    private AnchorPane anchorPane;
    
    private Pet pet = new Pet();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        petDAO.setConnection(connection);
    }
    
    public void AddPetController(Usuario usuario, Pet pet){
        this.setUsuario(usuario);
        this.setPet(pet);
        if(this.pet != null) {
            this.btn_deletePet.visibleProperty().set(true);
            this.lbl_registerOrEditPet.setText("Alterar Pet");
            this.btn_registerOrEditPet.setText("Alterar");
            this.txt_petId.setText(this.pet.getIdTag());
            this.txt_petName.setText(this.pet.getNomePet());
            this.txt_petId.requestFocus();
        }
    }
    
    public Pet getPet() {
        return this.pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void btn_registerOrEditPet_onAction() throws IOException{
        if(this.txt_petId.getText().isEmpty() || this.txt_petName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("[ERRO] Preencha todos os campos!");
            alert.show();
        }else if(this.pet == null){
            Pet pet = new Pet();
            if(mainClass.isTagId(this.txt_petId.getText()) && this.txt_petId.getText().length() >= 6) pet.setIdTag(this.txt_petId.getText());
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("O Id da Tag precisa ter seis caracteres e pelo menos um número!");
                alert.show();
                return;
            }
            usuario.setLogin(this.usuario.getLogin());
            pet.setNomePet(this.txt_petName.getText());
            int result = petDAO.createPet(pet, usuario);
            switch (result) {
                case 0:
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("[ERRO] Pet já existe!");
                        alert.show();
                        break;
                    }
                case 1:
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Pet cadastrado com sucesso!");
                        alert.show();
                        break;
                    }
                case 2:
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("[ERRO] Não foi possível cadastrar pet!");
                        alert.show();
                        break;
                    }
                default:
                    break;
            }
            this.txt_petId.clear();
            this.txt_petName.clear();
            this.txt_petId.requestFocus();
        }else{
            Pet pet = new Pet();
            List<Pet> pets = new ArrayList<Pet>();
            if(mainClass.isTagId(this.txt_petId.getText()) && this.txt_petId.getText().length() >= 6) pet.setIdTag(this.txt_petId.getText());
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("O Id da Tag precisa ter seis caracteres e pelo menos um número!");
                alert.show();
                return;
            }
            usuario.setLogin(this.usuario.getLogin());
            pet.setNomePet(this.txt_petName.getText());
            pets.add(0, this.pet);
            pets.add(1, pet);
            int result = petDAO.editPet(pets, usuario);
            switch (result) {
                case 0:
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("[ERRO] Pet já existe!");
                        alert.show();
                        break;
                    }
                case 1:
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Pet alterado com sucesso!");
                        alert.show();
                        break;
                    }
                case 2:
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("[ERRO] Não foi possível cadastrar pet!");
                        alert.show();
                        break;
                    }
                default:
                    break;
            }
            this.txt_petId.requestFocus();
        }
    }
    
    public void btn_deletePet_onAction() throws IOException{
        int result = petDAO.deletePet(this.pet);
        if(result == 0){
            anchorPane.getScene().getWindow().hide();
            anchorPane = new AnchorPane();
            PetController petController = mainClass.goToFrame(anchorPane, "view/Pet.fxml").getController();
            petController.PetController(usuario);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Pet excluído com sucesso!");
            alert.show();
        }else if(result == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("[ERRO] Não foi possível excluir pet!");
            alert.show();
        }
    }
    
    @Override
    public void img_close_onMouseClicked(){
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        PetController petController = new PetController();
        try {
            petController = mainClass.goToFrame(anchorPane, "view/Pet.fxml").getController();
        } catch (IOException ex) {
            Logger.getLogger(AddPetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        petController.PetController(usuario);
    }
    
    @Override
    public void img_minimize_onMouseClicked(){
        mainClass.minimize(anchorPane);
    }
}
