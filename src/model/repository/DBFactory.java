package src.model.repository;

import src.model.repository.database.GesTADSDataBaseInterface;
import src.model.repository.database.PersistDatabase;
import src.model.repository.database.VolatileDataBase;
import src.util.tools.GesLogger;

import static src.util.tools.GesLogger.TAG;

public class DBFactory {

    static final int VOLATILE_DB = 1;
    static final int PERSIST_DB = 2;

    static GesTADSDataBaseInterface getDatabase(int db) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getDatabase");

        return db == VOLATILE_DB ? getVolatileDB() : getPersistDB();
    }

    private static GesTADSDataBaseInterface getVolatileDB() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getVolatileDB");

        return VolatileDataBase.getInstance();
    }

    private static GesTADSDataBaseInterface getPersistDB() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getPersistDB");

        return PersistDatabase.getInstance();
    }
}
