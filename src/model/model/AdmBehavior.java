package src.model.model;

import src.util.GesLogger;


//[CDS]
public class AdmBehavior implements EmployeeBehavior{

    private static final String TAG = AdmBehavior.class.getSimpleName();
    @Override
    public void solicitaProduto() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "solicitaProduto");

    }

    @Override
    public void devolveProduto() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "devolveProduto");
    }

    @Override
    public void registrarEntradaDeProduto() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "registrarEntradaDeProduto");
    }

    @Override
    public void registrarSaidaDeProduto() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "registrarSaidaDeProduto");
    }

    @Override
    public void aprovacao() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "aprovacao");
    }

    @Override
    public void buscarProduto() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "buscarProduto");
    }

    @Override
    public void buscarUsuario() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "buscarUsuario");
    }

    @Override
    public void adicionarUsuario() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "adicionarUsuario");
    }

    @Override
    public void removerUsuario() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "removerUsuario");
    }
}
