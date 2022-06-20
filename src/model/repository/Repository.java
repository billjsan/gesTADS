package src.model.repository;

import src.model.repository.database.GesTADSDataBaseInterface;
import src.model.repository.database.PersistDatabase;
import src.model.repository.database.VolatileDataBase;
import src.util.tools.GesLogger;

import java.util.HashMap;

public class Repository {

    private static Repository instance;
    private final GesTADSDataBaseInterface mDB;
    private final String TAG = Repository.class.getSimpleName();

    //private final List<String> teste = Collections.synchronizedList(new ArrayList<>());

    private Repository(){
        mDB = VolatileDataBase.getInstance(); //operacao sincrona na thread main
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName()
                + " antes de iniciar outa thread");

        mDB.startUpDadaBase(); // inicia uma thread de execução
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName()
                + " depois de iniciar outa thread");
    }

    public boolean checkDb(){
        System.out.println("is db ok: " + mDB.isDBInitialized());
        return mDB.isDBInitialized();
    }

    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        if(GesLogger.ISLOGABLE) GesLogger.d("Repository", Thread.currentThread().getName() +
                " vou retornar uma instacia de repository");
        return instance;
    }

    public HashMap<String, String> getCredentialsInfo() {
       return new HashMap<String, String>();
    }
}