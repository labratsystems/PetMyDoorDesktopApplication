/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.model.dao;

import PetMyDoorDesktopApplicationMVC.model.domain.Passagem;
import PetMyDoorDesktopApplicationMVC.model.domain.Pet;
import PetMyDoorDesktopApplicationMVC.model.domain.Porta;
import PetMyDoorDesktopApplicationMVC.model.domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lab Rat Systems
 */
public class PassageDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public List<Passagem> read(Usuario usuario, Pet pet, Porta porta, Passagem passagem){
        List<Passagem> passages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT passagem.idTag, passagem.idPorta, passagem.dataHoraPassagem, passagem.direcao FROM passagem LEFT JOIN pet ON passagem.idTag = pet.idTag WHERE (pet.idTag = ? OR pet.nomePet = ?) AND passagem.idPorta = ? AND passagem.dataHoraPassagem LIKE '" + passagem.getDataHoraPassagem() + "%' AND pet.login = ?");
            preparedStatement.setString(1, pet.getIdTag());
            preparedStatement.setString(2, pet.getNomePet());
            preparedStatement.setInt(3, porta.getIdPorta());
            preparedStatement.setString(4, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) passages = this.resultSet(resultSet, passages);
            else return null;
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return passages;
    }
    
    public List<Passagem> read(Usuario usuario, Passagem passagem){
        List<Passagem> passages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT passagem.idTag, passagem.idPorta, passagem.dataHoraPassagem, passagem.direcao FROM passagem LEFT JOIN pet ON passagem.idTag = pet.idTag WHERE passagem.dataHoraPassagem LIKE '" + passagem.getDataHoraPassagem() + "%' AND pet.login = ?");
            preparedStatement.setString(1, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) passages = this.resultSet(resultSet, passages);
            else return null;
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return passages;
    }
    
    public List<Passagem> read(Usuario usuario, Porta porta){
        List<Passagem> passages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT passagem.idTag, passagem.idPorta, passagem.dataHoraPassagem, passagem.direcao FROM passagem LEFT JOIN pet ON passagem.idTag = pet.idTag WHERE passagem.idPorta = ? AND pet.login = ?");
            preparedStatement.setInt(1, porta.getIdPorta());
            preparedStatement.setString(2, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) passages = this.resultSet(resultSet, passages);
            else return null;
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return passages;
    }
    
    public List<Passagem> read(Usuario usuario, Pet pet){
        List<Passagem> passages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT passagem.idTag, passagem.idPorta, passagem.dataHoraPassagem, passagem.direcao FROM passagem LEFT JOIN pet ON passagem.idTag = pet.idTag WHERE (pet.idTag = ? OR pet.nomePet = ?) AND pet.login = ?");
            preparedStatement.setString(1, pet.getIdTag());
            preparedStatement.setString(2, pet.getNomePet());
            preparedStatement.setString(3, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) passages = this.resultSet(resultSet, passages);
            else return null;
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return passages;
    }
    
    public List<Passagem> read(Usuario usuario, Porta porta, Passagem passagem){
        List<Passagem> passages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT passagem.idTag, passagem.idPorta, passagem.dataHoraPassagem, passagem.direcao FROM passagem LEFT JOIN pet ON passagem.idTag = pet.idTag WHERE passagem.idPorta = ? AND passagem.dataHoraPassagem LIKE '" + passagem.getDataHoraPassagem() + "%' AND pet.login = ?");
            preparedStatement.setInt(1, porta.getIdPorta());
            preparedStatement.setString(2, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) passages = this.resultSet(resultSet, passages);
            else return null;
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return passages;
    }
    
     public List<Passagem> read(Usuario usuario, Pet pet, Passagem passagem){
        List<Passagem> passages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT passagem.idTag, passagem.idPorta, passagem.dataHoraPassagem, passagem.direcao FROM passagem LEFT JOIN pet ON passagem.idTag = pet.idTag WHERE (pet.idTag = ? OR pet.nomePet = ?) AND passagem.dataHoraPassagem LIKE '" + passagem.getDataHoraPassagem() + "%' AND pet.login = ?");
            preparedStatement.setString(1, pet.getIdTag());
            preparedStatement.setString(2, pet.getNomePet());
            preparedStatement.setString(3, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) passages = this.resultSet(resultSet, passages);
            else return null;
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return passages;
    }
     
    public List<Passagem> read(Usuario usuario, Pet pet, Porta porta){
        List<Passagem> passages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT passagem.idTag, passagem.idPorta, passagem.dataHoraPassagem, passagem.direcao FROM passagem LEFT JOIN pet ON passagem.idTag = pet.idTag WHERE (pet.idTag = ? OR pet.nomePet = ?) AND passagem.idPorta = ? AND pet.login = ?");
            preparedStatement.setString(1, pet.getIdTag());
            preparedStatement.setString(2, pet.getNomePet());
            preparedStatement.setInt(3, porta.getIdPorta());
            preparedStatement.setString(4, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) passages = this.resultSet(resultSet, passages);
            else return null;
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return passages;
    }
    
    public List<Passagem> resultSet(ResultSet resultSet, List<Passagem> passages){
        try {
            do{
                Passagem passageResultSet = new Passagem();
                passageResultSet.setIdTag(resultSet.getString("idTag"));
                passageResultSet.setIdPorta(resultSet.getInt("idPorta"));
                passageResultSet.setDataHoraPassagem(resultSet.getString("dataHoraPassagem"));
                passageResultSet.setDirecao(resultSet.getString("direcao"));
                passages.add(passageResultSet);
            }while(resultSet.next());
        } catch (SQLException ex) {
            Logger.getLogger(PassageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passages;
    }
}
