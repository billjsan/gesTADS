package src.model.repository.database;

import src.util.tools.GesLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolatileDataBase implements GesTADSDataBaseInterface {
    public final String TAG = VolatileDataBase.class.getSimpleName();
    private final ExecutorService mExecutor;
    private static VolatileDataBase instance;
    private boolean isDBStarted;
    private VolatileDataBase(){
        mExecutor = Executors.newFixedThreadPool(3); // define o n de Threads para o executor
    }

    public static VolatileDataBase getInstance(){
        if(instance == null){
            instance = new VolatileDataBase();
        }
        return instance;
    }
    @Override
    public void startUpDadaBase() {
        mExecutor.submit(() -> {
            try {
                Thread.sleep(5000);
                if(GesLogger.ISLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName()
                        + " Espera finalizada");
                isDBStarted = true;
                closeDataBase();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void closeDataBase() {
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName()
                + " closeDataBase");
        mExecutor.shutdown();
        try {
            if (!mExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                mExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            mExecutor.shutdownNow();
        }
    }

    @Override
    public boolean isDBInitialized() {
        return isDBStarted;
    }

    @Override
    public void executeInsertQuery(String query) {

    }

    @Override
    public void allOtherMethods() {

    }
}
