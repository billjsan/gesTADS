package src.model.model;

import src.util.GesLogger;

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
    private Long mId;


    public Employee() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "construtor vazio");
    }

    public Employee(String nome, String login, String senha, String cargo, String cpf, Long id) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "construtor com parametro");

        this.mNome = nome;
        this.mLogin = login;
        this.mSenha = senha;
        this.mCargo = cargo;
        this.mCpf = cpf;
        this.mId = id;

        //inferPrivilege();
    }

    public void generateID() {
        this.mId = _id;
        _id++;
    }

    public Long getId() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getId id: " + mId);

        return this.mId;
    }

    public String getNome() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getNome: " + mNome);

        return mNome;
    }

    public void setNome(String nome) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setNome: " + nome);

        this.mNome = nome;
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
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getPrivilegio: " + mPrivilegio);

        return mPrivilegio;
    }

    public String getCargo() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getCargo: " + mCargo);

        return mCargo;
    }

    public void setCargo(String cargo) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setCargo: " + cargo);

        this.mCargo = cargo;
        inferPrivilege();
    }

    public void setId(Long id) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getId: " + id);

        this.mId = id;
    }

    public String getCpf() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getCpf: " + mCpf);

        return mCpf;
    }

    public void setCpf(String cpf) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setCpf: " + cpf);

        this.mCpf = cpf;
    }


    public String getLogin() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getLogin: " + mLogin);

        return mLogin;
    }

    public void setLogin(String login) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setLogin: " + login);

        this.mLogin = login;
    }

    public void setPrivilegio(int privilegio) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setPrivilegio: " + privilegio);

        this.mPrivilegio = privilegio;
    }

    private void inferPrivilege() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
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