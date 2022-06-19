package src.model.repository.database;

import src.util.tools.GesLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PersistDatabase implements GesTADSDataBaseInterface {
    private final String TAG = PersistDatabase.class.getSimpleName();
    private boolean isDBInitialized;
    private static PersistDatabase instance;
    private final ExecutorService mExecutor;

    private PersistDatabase() {
        mExecutor = Executors.newFixedThreadPool(3); // define o n de Threads para o executor
    }

    public static PersistDatabase getInstance() {
        if (instance == null) {
            instance = new PersistDatabase();
        }
        return instance;
    }

    @Override
    public void startUpDadaBase() {
        if (GesLogger.ISLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName() + " startUpDB");

        mExecutor.submit(() -> {
            try {
                Thread.sleep(5000);
                if (GesLogger.ISLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName()
                        + " Espera finalizada");
            } catch (InterruptedException e) {
                if (GesLogger.ISLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName()
                        + " Error while getting future value from callable");
            }
        });
    }

    @Override
    public void closeDataBase() {
        System.out.println(Thread.currentThread().getName() + " Database -- closeDB");

        mExecutor.shutdown();
        try {
            if (!mExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                mExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            mExecutor.shutdownNow();
        }
    }

    public boolean isDBInitialized() {
        return isDBInitialized;
    }

    public void executeInsertQuery(String query) {
        //execute the query
    }

    /**
     * abaixo virão todos os metodos que irão
     * efetivamente manipular o banco de dados.
     * inserir, remover, buscar, etc. As queries
     * SQL serão executadas a partir daqui com a criaçao
     * de threads separadas para cada consulta ao banco.
     * <p>
     * precisamos de uma abordagem pra fazer isso como
     * por exemplo estudar como usar as APIS java para banco
     * de dados ou até mesmo usar outra abordagem para salvar os
     * dados. De todo modo o banco de dados real estará abaixo do
     * objeto Database. Cada objeto Database tem sua própria implementação
     * podendo um ser SQL o outro ser File, etc.
     */

    public void allOtherMethods() {

    }
}