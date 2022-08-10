package src.model.repository;

import src.model.repository.database.GesTADSDataBaseInterface;
import src.model.repository.database.PersistDatabase;
import src.model.repository.database.VolatileDataBase;
import src.util.GesLogger;


public class DBFactory {

    private static final String TAG = DBFactory.class.getSimpleName();
    static final int VOLATILE_DB = 1;
    static final int PERSIST_DB = 2;

    static GesTADSDataBaseInterface getDatabase(int db) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getDatabase");

        return db == PERSIST_DB ? getPersistDB() : getVolatileDB();
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
