
package PetMyDoorDesktopApplicationMVC.model.database;

import java.sql.Connection;

public interface Database {
    
    public Connection connect();
    public void disconnect(Connection connection);
}
