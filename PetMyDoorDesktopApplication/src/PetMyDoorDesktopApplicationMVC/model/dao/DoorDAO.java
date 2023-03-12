/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.model.dao;

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
public class DoorDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public List<Porta> read(Usuario usuario){
        List<Porta> doors = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM porta WHERE login = ?");
            preparedStatement.setString(1, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Porta porta = new Porta();
                porta.setIdPorta(resultSet.getInt("idPorta"));
                porta.setLocalizacao(resultSet.getString("localizacao"));
                doors.add(porta);
            }
        } catch (SQLException exception) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return doors;
    }
    
    public boolean createDoor(Porta porta, Usuario usuario){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM porta WHERE idPorta = ?");
            preparedStatement.setInt(1, porta.getIdPorta());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) porta = null;
            else {
                preparedStatement = connection.prepareStatement("INSERT INTO porta (login, localizacao) VALUES (?, ?)");
                preparedStatement.setString(1, usuario.getLogin());
                preparedStatement.setString(2, porta.getLocalizacao());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean editDoor(Porta porta){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE porta SET localizacao = ? WHERE idPorta = ?");
            preparedStatement.setString(1, porta.getLocalizacao());
            preparedStatement.setInt(2, porta.getIdPorta());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteDoor(Porta porta){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM porta WHERE idPorta = ?");
            preparedStatement.setInt(1, porta.getIdPorta());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}