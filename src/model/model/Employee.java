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
    private String mAdmissao = "";

    //Dados do usuario [CDS]
    private String mSexo = "";
    private String mCpf = "";
    private String mRg = "";
    private String mEndereco = "";
    private String mEstadoCivil = "";
    private String mMatricula = "";
    private String mLogin;

    EmployeeBehavior  mEmployeeBehavior;

    public Employee(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "Employee");

    }

    public Employee(String nome, String login, String senha, String cargo, String admissao, String sexo,
                    String cpf, String rg, String endereco, String estadoCivil, String matricula) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "Employee: nome, cpf, cargo, matricula");

        this.mNome = nome;
        this.mLogin = login;
        this.mSenha = senha;
        this.mCargo = cargo;
        this.mAdmissao = admissao;
        this.mSexo = sexo;
        this.mCpf = cpf;
        this.mRg = rg;
        this.mEndereco = endereco;
        this.mEstadoCivil = estadoCivil;
        this.mMatricula = matricula;

        setPrivilege();
    }

    public EmployeeBehavior getEmployeeBehavior() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "EmployeeBehavior");

        return mEmployeeBehavior;
    }

    public void setEmployeeBehavior(EmployeeBehavior mEmployeeBehavior) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, " m Employeer Behavior");

        this.mEmployeeBehavior = mEmployeeBehavior;
    }

    public String getMatricula() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "getMatricula");

        return mMatricula;
    }

    public void setMatricula(String mMatricula) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "setMatricula");

        this.mMatricula = mMatricula;
    }

    public String getNome() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "getNome");

        return mNome;
    }

    public void setNome(String mNome) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "SetNome");

        this.mNome = mNome;
    }

    public String getSenha() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "get senha");

        return mSenha;
    }

    public final void setSenha(String mSenha) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "SET SENHA");

        this.mSenha = mSenha;
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("SHA-256");
//            md.update(senha.getBytes());
//            this.senha = arrayToString(md.digest());
//        } catch (NoSuchAlgorithmException e) {
//            if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "error while encrypting the password");
//        }
    }

    public int getPrivilegio() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "get Privilegio");

        return mPrivilegio;
    }

    public String getCargo() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "get cargo");

        return mCargo;
    }

    public void setCargo(String mCargo) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "set Cargo");

        this.mCargo = mCargo;
        setPrivilege();
    }

    public String getAdmissao() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "get Adimissao");

        return mAdmissao;
    }

    public void setAdmissao(String mAdmissao) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "set Admissao");

        this.mAdmissao = mAdmissao;
    }

    public String getSexo() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "getSexo");

        return mSexo;
    }

    public void setSexo(String mSexo) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "set Sexo");

        this.mSexo = mSexo;
    }

    public String getCpf() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "get Cpf");

        return mCpf;
    }

    public void setCpf(String mCpf) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "set CPF");

        this.mCpf = mCpf;
    }

    public String getRg() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "get RG");

        return mRg;
    }

    public void setRg(String mRg) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "set RG");

    }

//    protected String arrayToString(byte[] array){
//        StringBuilder s = new StringBuilder();
//        for (byte b : array) {
//            int parteAlta = ((b >> 4) & 0xf) << 4;
//            int parteBaixa = b & 0xf;
//            if (parteAlta == 0) s.append('0');
//            s.append(Integer.toHexString(parteAlta | parteBaixa));
//        }
//        return s.toString();
//    }

    public String getEndereco() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "get endereço");

        return mEndereco;
    }

    public void setEndereco(String mEndereco) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "set endereço");

        this.mEndereco = mEndereco;
    }

    public String getEstadoCivil() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "get Estado Civil");

        return mEstadoCivil;
    }

    public void setEstadoCivil(String mEstadoCivil) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, "set estado civil");

        this.mEstadoCivil = mEstadoCivil;
    }

    public String getLogin() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "Get Login");

        return mLogin;
    }

    public void setLogin(String login) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "set Login");

        this.mLogin = login;
    }

    public void setPrivilegio(int mPrivilegio) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "set Privilegio");

        this.mPrivilegio = mPrivilegio;
    }

    private void setPrivilege(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "Qualidade do Privilegio");


        if (mCargo == null || mCargo.isEmpty()){
            setPrivilegio(Employee.PRIVILEGE_OPERATOR);
        }else {
            switch (mCargo){
                case Employee.POSITION_ADMIN:
                    setPrivilegio(Employee.PRIVILEGE_ADMIN);
                    break;

                case Employee.POSITION_SUPERVISOR:
                    setPrivilegio(Employee.PRIVILEGE_SUPERVISOR);
                    break;

                case Employee.POSITION_OPERADOR:
                    setPrivilegio(Employee.PRIVILEGE_OPERATOR);
                    break;
            }
        }
    }
}