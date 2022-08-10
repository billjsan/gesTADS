package src.model.model;

import src.util.tools.GesLogger;

public class Employee {

    private final String TAG = Employee.class.getSimpleName();

    //Privilegios [CDS]
    public static int PRIVILEGE_ADMIN = 3;
    public static int PRIVILEGE_SUPERVISOR = 2;
    public static int PRIVILEGE_OPERATOR = 1;

    public static final String POSITION_ADMIN = "Administrador";
    public static final String POSITION_SUPERVISOR = "Supervisor";
    public static final String POSITION_OPERADOR = "Operador";

    //Dados da empresa [CDS]
    private String mNome = "";
    private String mSenha = "";
    protected int mPrivilegio = 0;
    private String mCargo = "";

    //Dados do usuario [CDS]
    private String mCpf = "";
    private String mLogin;

    private static Long _id = 0L;
    private Long id;

    EmployeeBehavior mEmployeeBehavior;

    public Employee() {

    }

    public Employee(String nome, String login, String senha, String cargo, String cpf) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "Employee Dados cadastrais");
        this.mNome = nome;
        this.mLogin = login;
        this.mSenha = senha;
        this.mCargo = cargo;
        this.mCpf = cpf;

        inferPrivilege();
    }

    public void generateID() {
        this.id = _id;
        _id++;
    }

    public Long getId() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getId id: " + id);

        return this.id;
    }

    public EmployeeBehavior getEmployeeBehavior() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "employeeBehavior getEmployeeBehavior");
        return mEmployeeBehavior;
    }

    public void setEmployeeBehavior(EmployeeBehavior mEmployeeBehavior) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setEmployeeBehavior");
        this.mEmployeeBehavior = mEmployeeBehavior;
    }

    public String getNome() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getNome");
        return mNome;
    }

    public void setNome(String mNome) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setNome");
        this.mNome = mNome;
    }

    public String getSenha() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getSenha");
        return mSenha;
    }

    public final void setSenha(String mSenha) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setSenha");
        this.mSenha = mSenha;
    }

    public int getPrivilegio() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getPrivilegio");
        return mPrivilegio;
    }

    public String getCargo() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getCargo");
        return mCargo;
    }

    public void setCargo(String mCargo) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setCargo" + mCargo);
        this.mCargo = mCargo;
        inferPrivilege();
    }

    public String getCpf() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getCpf");
        return mCpf;
    }

    public void setCpf(String mCpf) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setCpf");
        this.mCpf = mCpf;
    }

    public void setRg(String mRg) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setRg");
    }

    public String getLogin() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getLogin");
        return mLogin;
    }

    public void setLogin(String login) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setLogin" + mLogin);
        this.mLogin = login;
    }

    public void setPrivilegio(int mPrivilegio) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setPrivilegio" + mPrivilegio);
        this.mPrivilegio = mPrivilegio;
    }

    private void inferPrivilege() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "inferPrivilege");

        if (mCargo == null || mCargo.isEmpty()) {
            setPrivilegio(Employee.PRIVILEGE_OPERATOR);
        } else {
            switch (mCargo) {
                case Employee.POSITION_ADMIN:
                    setPrivilegio(Employee.PRIVILEGE_ADMIN);
                    break;

                case Employee.POSITION_SUPERVISOR:
                    setPrivilegio(Employee.PRIVILEGE_SUPERVISOR);
                    break;

                default:
                    setPrivilegio(Employee.PRIVILEGE_OPERATOR);

            }
        }
    }
}