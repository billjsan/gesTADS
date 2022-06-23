package src.model.model;

import java.util.Date;

public class Employee {

    private final String TAG = Employee.class.getSimpleName();

    //Privilegios [CDS]
    public static int PRIVILEGE_ADMIN = 3;
    public static int PRIVILEGE_SUPERVISOR = 2;
    public static int PRIVILEGE_OPERATOR = 1;

    //Dados da empresa [CDS]
    private String nome = "";
    private String senha = "";
    protected int mPrivilegio = 0;
    private String cargo = "";
    private Date admissao = new Date();

    //Dados do usuario [CDS]
    private String sexo = "";
    private String cpf = "";
    private String rg = "";
    private String endereco = "";
    private String estadoCivil = "";
    private String matricula = "";
    private String mLogin;

    EmployeeBehavior  mEmployeeBehavior;

    public Employee(){
        //[LAS]
    }

    public Employee(String nome,String login, String senha, String cargo, Date admissao, String sexo,
                    String cpf, String rg, String endereco, String estadoCivil, String matricula) {
        //[LAS]
        this.nome = nome;
        this.mLogin = login;
        this.senha = senha;
        this.cargo = cargo;
        this.admissao = admissao;
        this.sexo = sexo;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.estadoCivil = estadoCivil;
        this.matricula = matricula;
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
        return matricula;
    }

    public void setMatricula(String matricula) {
        //[LAS]
        this.matricula = matricula;
    }

    public String getNome() {
        //[LAS]
        return nome;
    }

    public void setNome(String nome) {
        //[LAS]
        this.nome = nome;
    }

    public String getSenha() {
        //[LAS]
        return senha;
    }

    public final void setSenha(String senha) {
        //[LAS]
        this.senha = senha;
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
        return cargo;
    }

    public void setCargo(String cargo) {
        //[LAS]
        this.cargo = cargo;
    }

    public Date getAdmissao() {
        //[LAS]
        return admissao;
    }

    public void setAdmissao(Date admissao) {
        //[LAS]
        this.admissao = admissao;
    }

    public String getSexo() {
        //[LAS]
        return sexo;
    }

    public void setSexo(String sexo) {
        //[LAS]
        this.sexo = sexo;
    }

    public String getCpf() {
        //[LAS]
        return cpf;
    }

    public void setCpf(String cpf) {
        //[LAS]
        this.cpf = cpf;
    }

    public String getRg() {
        //[LAS]
        return rg;
    }

    public void setRg(String rg) {
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
        return endereco;
    }

    public void setEndereco(String endereco) {
        //[LAS]
        this.endereco = endereco;
    }

    public String getEstadoCivil() {
        //[LAS]
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        //[LAS]
        this.estadoCivil = estadoCivil;
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
}