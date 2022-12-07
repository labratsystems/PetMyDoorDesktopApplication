/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.controller;

import PetMyDoorDesktopApplicationMVC.MainClass;
import PetMyDoorDesktopApplicationMVC.model.dao.DoorDAO;
import PetMyDoorDesktopApplicationMVC.model.dao.PassageDAO;
import PetMyDoorDesktopApplicationMVC.model.database.Database;
import PetMyDoorDesktopApplicationMVC.model.database.DatabaseFactory;
import PetMyDoorDesktopApplicationMVC.model.domain.Passagem;
import PetMyDoorDesktopApplicationMVC.model.domain.Pet;
import PetMyDoorDesktopApplicationMVC.model.domain.Porta;
import PetMyDoorDesktopApplicationMVC.model.domain.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lab Rat Systems
 */
public class DashboardController implements Initializable{
    @FXML
    private Label lbl_helloUser;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField txt_petNameOrId;
    
    @FXML
    private TextField txt_doorId;
    
    @FXML
    private TextField txt_passageDate;
    
    @FXML
    private TableView<Passagem> tableView_passages;
    
    @FXML
    private TableColumn<Passagem, String> tableColumn_petId;
    
    @FXML
    private TableColumn<Passagem, Integer> tableColumn_doorId;
    
    @FXML
    private TableColumn<Passagem, String> tableColumn_passageDateTime;
    
    @FXML
    private TableColumn<Passagem, String> tableColumn_passageDirection;
    
    private Usuario usuario = new Usuario();
    private Porta porta = new Porta();
    private Pet pet = new Pet();
    private Passagem passagem = new Passagem();
    private List<Passagem> listPassage;
    private ObservableList<Passagem> observableListPassage;
    private final Database database = DatabaseFactory.getDatabase();
    private final Connection connection = database.connect();
    private final PassageDAO passageDAO = new PassageDAO();
    private final MainClass mainClass = new MainClass();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passageDAO.setConnection(connection);
        this.initializeTableView();
    }
    
    public void DashboardController(Usuario usuario){
        this.setUsuario(usuario);
        this.lbl_helloUser.setText("Olá, " + this.getUsuario().getLogin());
        this.initializeTableView();
    }
    
    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void initializeTableView(){
        this.tableColumn_petId.setCellValueFactory(new PropertyValueFactory<>("idTag"));
        this.tableColumn_doorId.setCellValueFactory(new PropertyValueFactory<>("idPorta"));
        this.tableColumn_passageDateTime.setCellValueFactory(new PropertyValueFactory<>("dataHoraPassagem"));
        this.tableColumn_passageDirection.setCellValueFactory(new PropertyValueFactory("direcao"));
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
    
    public void hbox_goToPetFrame_onMouseClicked() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        PetController petController = mainClass.goToFrame(anchorPane, "view/Pet.fxml").getController();
        petController.PetController(usuario);
    }
    
    public void hbox_goToDoorFrame_onMouseClicked() throws IOException{
        anchorPane.getScene().getWindow().hide();
        anchorPane = new AnchorPane();
        DoorController doorController = mainClass.goToFrame(anchorPane, "view/Door.fxml").getController();
        doorController.DoorController(usuario);
    }
    
    public void btn_searchPassages_onMouseClicked(){
        if(this.txt_petNameOrId.getText().isEmpty() && this.txt_doorId.getText().isEmpty() && this.txt_passageDate.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("[ERRO] Preencha pelo menos um dos campos!");
            alert.show();
        }else{
            if(this.txt_petNameOrId.getText().isEmpty() && this.txt_doorId.getText().isEmpty()){
                try {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy", new java.util.Locale("pt", "BR"));
                    format.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
                    java.util.Date date = format.parse(this.txt_passageDate.getText());
                    passagem.setDataHoraPassagem(String.valueOf(new java.sql.Date(date.getTime())));
                } catch (ParseException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                this.listPassage = passageDAO.read(usuario, passagem);
                if(this.listPassage != null){
                    this.observableListPassage = FXCollections.observableArrayList(listPassage);
                    this.tableView_passages.setItems(observableListPassage);
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Não houveram resultados!");
                    alert.show();
                }
            }
            else if(this.txt_petNameOrId.getText().isEmpty() && this.txt_passageDate.getText().isEmpty()){
                porta.setIdPorta(Integer.parseInt(this.txt_doorId.getText()));
                this.listPassage = passageDAO.read(usuario, porta);
                if(this.listPassage != null){
                    this.observableListPassage = FXCollections.observableArrayList(listPassage);
                    this.tableView_passages.setItems(observableListPassage);
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Não houveram resultados!");
                    alert.show();
                }
            }
            else if(this.txt_doorId.getText().isEmpty() && this.txt_passageDate.getText().isEmpty()){
                if(mainClass.isTagId(this.txt_petNameOrId.getText())) pet.setIdTag(this.txt_petNameOrId.getText());
                else pet.setNomePet(this.txt_petNameOrId.getText());
                this.listPassage = passageDAO.read(usuario, pet);
                if(this.listPassage != null){
                    this.observableListPassage = FXCollections.observableArrayList(listPassage);
                    this.tableView_passages.setItems(observableListPassage);
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Não houveram resultados!");
                    alert.show();
                }
            }
            else{
                if(this.txt_petNameOrId.getText().isEmpty()){
                    porta.setIdPorta(Integer.parseInt(this.txt_doorId.getText()));
                    try {
                        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", new java.util.Locale("pt", "BR"));
                        format.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
                        java.util.Date date = format.parse(this.txt_passageDate.getText());
                        passagem.setDataHoraPassagem(String.valueOf(new java.sql.Date(date.getTime())));
                    } catch (ParseException ex) {
                        Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    this.listPassage = passageDAO.read(usuario, porta, passagem);
                    if(this.listPassage != null){
                        this.observableListPassage = FXCollections.observableArrayList(listPassage);
                        this.tableView_passages.setItems(observableListPassage);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Não houveram resultados!");
                        alert.show();
                    }
                }
                else if(this.txt_doorId.getText().isEmpty()){
                    if(mainClass.isTagId(this.txt_petNameOrId.getText())) pet.setIdTag(this.txt_petNameOrId.getText());
                    else pet.setNomePet(this.txt_petNameOrId.getText());
                    try {
                        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", new java.util.Locale("pt", "BR"));
                        format.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
                        java.util.Date date = format.parse(this.txt_passageDate.getText());
                        passagem.setDataHoraPassagem(String.valueOf(new java.sql.Date(date.getTime())));
                    } catch (ParseException ex) {
                        Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    this.listPassage = passageDAO.read(usuario, pet, passagem);
                    if(this.listPassage != null){
                        this.observableListPassage = FXCollections.observableArrayList(listPassage);
                        this.tableView_passages.setItems(observableListPassage);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Não houveram resultados!");
                        alert.show();
                    }
                }
                else if(this.txt_passageDate.getText().isEmpty()){
                    if(mainClass.isTagId(this.txt_petNameOrId.getText())) pet.setIdTag(this.txt_petNameOrId.getText());
                    else pet.setNomePet(this.txt_petNameOrId.getText());
                    porta.setIdPorta(Integer.parseInt(this.txt_doorId.getText()));
                    this.listPassage = passageDAO.read(usuario, pet, porta);
                    if(this.listPassage != null){
                        this.observableListPassage = FXCollections.observableArrayList(listPassage);
                        this.tableView_passages.setItems(observableListPassage);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Não houveram resultados!");
                        alert.show();
                    }
                }
                else{
                    if(mainClass.isTagId(this.txt_petNameOrId.getText())) pet.setIdTag(this.txt_petNameOrId.getText());
                    else pet.setNomePet(this.txt_petNameOrId.getText());
                    porta.setIdPorta(Integer.parseInt(this.txt_doorId.getText()));
                    try {
                        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", new java.util.Locale("pt", "BR"));
                        format.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
                        java.util.Date date = format.parse(this.txt_passageDate.getText());
                        passagem.setDataHoraPassagem(String.valueOf(new java.sql.Date(date.getTime())));
                    } catch (ParseException ex) {
                        Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    this.listPassage = passageDAO.read(usuario, pet, porta, passagem);
                    if(this.listPassage != null){
                        this.observableListPassage = FXCollections.observableArrayList(listPassage);
                        this.tableView_passages.setItems(observableListPassage);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Não houveram resultados!");
                        alert.show();
                    }
                }
            }
        }
    }
    
    public void txt_passageDate_onKeyPressed(){
        DashboardController.dateMask(this.txt_passageDate);
    }
    
    public void txt_doorId_onKeyPressed(){
        DashboardController.numberMask(this.txt_doorId);
    }
    
    public static void dateMask(TextField textField){
        textField.setOnKeyTyped((KeyEvent event) -> {
            if("0123456789".contains(event.getCharacter())==false){
                event.consume();
            }

            if(event.getCharacter().trim().length()==0){ // apagando

                if(textField.getText().length()==3){
                    textField.setText(textField.getText().substring(0,2));
                    textField.positionCaret(textField.getText().length());
                }
                if(textField.getText().length()==6){
                    textField.setText(textField.getText().substring(0,5));
                    textField.positionCaret(textField.getText().length());
                }

            }else{ // escrevendo

                if(textField.getText().length()==10) event.consume();

                if(textField.getText().length()==2){
                    textField.setText(textField.getText()+"/");
                    textField.positionCaret(textField.getText().length());
                }
                if(textField.getText().length()==5){
                    textField.setText(textField.getText()+"/");
                    textField.positionCaret(textField.getText().length());
                }

            }
        });

        textField.setOnKeyReleased((KeyEvent evt) -> {

            if(!textField.getText().matches("\\d/*")){
                textField.setText(textField.getText().replaceAll("[^\\d/]", ""));
                textField.positionCaret(textField.getText().length());
            }
        });
    }
    
    public static void numberMask(TextField textField){
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}