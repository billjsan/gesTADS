package src.model.repository;

import src.model.repository.database.GesTADSDataBaseInterface;
import src.model.repository.database.PersistDatabase;
import src.model.repository.database.VolatileDataBase;

public class DBFactory {

    static final int VOLATILE_DB = 1;
    static final int PERSIST_DB = 2;

    static GesTADSDataBaseInterface getDatabase(int db) {
        //[LAS]

        return db == VOLATILE_DB ? getVolatileDB() : getPersistDB();
    }

    private static GesTADSDataBaseInterface getVolatileDB() {
        //[LAS]

        return VolatileDataBase.getInstance();
    }

    private static GesTADSDataBaseInterface getPersistDB() {
        //[LAS]

        return PersistDatabase.getInstance();
    }
}
