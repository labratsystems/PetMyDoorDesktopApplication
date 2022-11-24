package PetMyDoorDesktopApplicationMVC.model.database;

public class DatabaseFactory {
    public static Database getDatabase(){
        return new DatabaseMySQL();
    }
}