package src.model.model;

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
        //[LAS]
    }

    public Employee(String nome, String login, String senha, String cargo, String admissao, String sexo,
                    String cpf, String rg, String endereco, String estadoCivil, String matricula) {
        //[LAS]
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
        //[LAS]
        return mEmployeeBehavior;
    }

    public void setEmployeeBehavior(EmployeeBehavior mEmployeeBehavior) {
        //[LAS]
        this.mEmployeeBehavior = mEmployeeBehavior;
    }

    public String getMatricula() {
        //[LAS]
        return mMatricula;
    }

    public void setMatricula(String mMatricula) {
        //[LAS]
        this.mMatricula = mMatricula;
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
        setPrivilege();
    }

    public String getAdmissao() {
        //[LAS]
        return mAdmissao;
    }

    public void setAdmissao(String mAdmissao) {
        //[LAS]
        this.mAdmissao = mAdmissao;
    }

    public String getSexo() {
        //[LAS]
        return mSexo;
    }

    public void setSexo(String mSexo) {
        //[LAS]
        this.mSexo = mSexo;
    }

    public String getCpf() {
        //[LAS]
        return mCpf;
    }

    public void setCpf(String mCpf) {
        //[LAS]
        this.mCpf = mCpf;
    }

    public String getRg() {
        //[LAS]
        return mRg;
    }

    public void setRg(String mRg) {
        //[LAS]
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
        //[LAS]
        return mEndereco;
    }

    public void setEndereco(String mEndereco) {
        //[LAS]
        this.mEndereco = mEndereco;
    }

    public String getEstadoCivil() {
        //[LAS]
        return mEstadoCivil;
    }

    public void setEstadoCivil(String mEstadoCivil) {
        //[LAS]
        this.mEstadoCivil = mEstadoCivil;
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

    private void setPrivilege(){
        //[LAS]

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