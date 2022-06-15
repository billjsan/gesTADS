package src.model.repository;

import src.model.repository.database.Database;

public class Repository {

    private static Repository instance;
    private Database database;

    private Repository(){
        database = new Database();
    }

    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }
}