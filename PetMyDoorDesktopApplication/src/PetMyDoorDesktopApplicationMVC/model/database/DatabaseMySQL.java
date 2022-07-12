package petmydoordesktopapplicationmvc.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import petmydoordesktopapplicationmvc.model.database.Database;

/**
 *
 * @author Rafael Vargas Mesquita
 */
public class DatabaseMySQL implements Database {

    private Connection connection;

    @Override
    public Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/petmydoor", "root","Wesley20082000");
            return this.connection;
        } catch (SQLException | ClassNotFoundException exception) {
            Logger.getLogger(DatabaseMySQL.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
    }

    @Override
    public void disconnect(Connection connection) {
        try {
            connection.close();
        } catch (SQLException exception) {
            Logger.getLogger(DatabaseMySQL.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
    
}
