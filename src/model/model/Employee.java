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
        //[LAS]
    }

    public Employee(String nome, String login, String senha, String cargo, String cpf) {
        //[LAS]
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
        //[LAS]
        return mEmployeeBehavior;
    }

    public void setEmployeeBehavior(EmployeeBehavior mEmployeeBehavior) {
        //[LAS]
        this.mEmployeeBehavior = mEmployeeBehavior;
    }

    public String getNome() {
        //[LAS]
        return mNome;
    }

    public void setNome(String mNome) {
        //[LAS]
        this.mNome = mNome;
    }

    public String getSenha() {
        //[LAS]
        return mSenha;
    }

    public final void setSenha(String mSenha) {
        //[LAS]
        this.mSenha = mSenha;
    }

    public int getPrivilegio() {
        //[LAS]
        return mPrivilegio;
    }

    public String getCargo() {
        //[LAS]
        return mCargo;
    }

    public void setCargo(String mCargo) {
        //[LAS]
        this.mCargo = mCargo;
        inferPrivilege();
    }

    public String getCpf() {
        //[LAS]
        return mCpf;
    }

    public void setCpf(String mCpf) {
        //[LAS]
        this.mCpf = mCpf;
    }

    public void setRg(String mRg) {
        //[LAS]
    }

    public String getLogin() {
        //[LAS]
        return mLogin;
    }

    public void setLogin(String login) {
        //[LAS]
        this.mLogin = login;
    }

    public void setPrivilegio(int mPrivilegio) {
        //[LAS]
        this.mPrivilegio = mPrivilegio;
    }

    private void inferPrivilege() {
        //[LAS]

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