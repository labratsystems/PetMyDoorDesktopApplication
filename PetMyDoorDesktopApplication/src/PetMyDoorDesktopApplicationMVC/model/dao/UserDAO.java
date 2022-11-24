/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PetMyDoorDesktopApplicationMVC.model.dao;

import PetMyDoorDesktopApplicationMVC.model.domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lab Rat Systems
 */
public class UserDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean createUser(Usuario usuario){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario WHERE login = ?");
            preparedStatement.setString(1, usuario.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) usuario = null;
            else {
                preparedStatement = connection.prepareStatement("INSERT INTO usuario (login, senha, email, telefone) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, usuario.getLogin());
                preparedStatement.setString(2, usuario.getSenha());
                preparedStatement.setString(3, usuario.getEmail());
                preparedStatement.setString(4, usuario.getTelefone());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Usuario checkLogin(String login, String senha){
        Usuario usuario = new Usuario();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario WHERE login = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                usuario.setLogin(resultSet.getString("login"));
                if(checkPassword(preparedStatement, senha, resultSet)) usuario.setSenha(resultSet.getString("senha"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }
    
    public boolean checkPassword(PreparedStatement preparedStatement, String senha, ResultSet resultSet) throws SQLException{
        preparedStatement = connection.prepareStatement("SELECT * FROM usuario WHERE senha = ?");
        preparedStatement.setString(1, senha);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) return true; 
        else return false;
    }
}
