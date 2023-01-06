/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.model.dao;

import PetMyDoorDesktopApplicationMVC.model.domain.Pet;
import PetMyDoorDesktopApplicationMVC.model.domain.Usuario;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Lab Rat Systems
 */
public class PetDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public List<Pet> read(Usuario usuario){
        List<Pet> pets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pet WHERE login = ?");
            preparedStatement.setString(1, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Pet pet = new Pet();
                pet.setIdTag(resultSet.getString("idTag"));
                pet.setNomePet(resultSet.getString("nomePet"));
                pets.add(pet);
            }
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return pets;
    }
    
    public int createPet(Pet pet, Usuario usuario){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pet WHERE idTag = ?");
            preparedStatement.setString(1, pet.getIdTag());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return 0;
            else {
                preparedStatement = connection.prepareStatement("INSERT INTO pet (idTag, login, nomePet) VALUES (?, ?, ?)");
                preparedStatement.setString(1, pet.getIdTag());
                preparedStatement.setString(2, usuario.getLogin());
                preparedStatement.setString(3, pet.getNomePet());
                preparedStatement.executeUpdate();
                return 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 2;
    }
    
    public int editPet(List<Pet> pets, Usuario usuario){
        try {
            //Verifica se já existe algum pet com a tag digitada
            if(!pets.get(1).getIdTag().equals(pets.get(0).getIdTag())){
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pet WHERE idTag = ?");
                preparedStatement.setString(1, pets.get(1).getIdTag());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) return 0; //Tag já existe
                else{
                    //Alterar tag, porém altera o nome também se este for diferente
                    preparedStatement = connection.prepareStatement("UPDATE pet SET idTag = ?, nomePet = ? WHERE idTag = ?");
                    preparedStatement.setString(1, pets.get(1).getIdTag());
                    preparedStatement.setString(2, pets.get(1).getNomePet());
                    preparedStatement.setString(3, pets.get(0).getIdTag());
                    preparedStatement.executeUpdate();
                    return 1;
                }
            }
            else {
                //Alterar nome
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE pet SET idTag = ?, nomePet = ? WHERE idTag = ?");
                preparedStatement.setString(1, pets.get(1).getIdTag());
                preparedStatement.setString(2, pets.get(1).getNomePet());
                preparedStatement.setString(3, pets.get(0).getIdTag());
                preparedStatement.executeUpdate();
                return 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 2;
    }
    
    public int deletePet(Pet pet){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pet WHERE idTag = ?");
            preparedStatement.setString(1, pet.getIdTag());
            preparedStatement.execute();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }
}
