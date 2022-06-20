package src.model.model;

import src.util.tools.GesLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public abstract class Employee {

    private final String TAG = Employee.class.getSimpleName();

    //dados da empresa
    private String nome = "employee";
    private String senha = "1234";
    private int privilegio = 0;
    private String cargo = "";
    private Date admissao = new Date();
    // --------------------------------

    //dados do usuario
    private String sexo = "";
    private String cpf = "";
    private String rg = "";
    private String endereco = "";
    private String estadoCivil = "";
    // ------------------------------

    public Employee(){    }

    public Employee(String nome, String senha, int privilegio, String cargo, Date admissao, String sexo,
                    String cpf, String rg, String endereco, String estadoCivil) {
        this.nome = nome;
        this.senha = senha;
        this.privilegio = privilegio;
        this.cargo = cargo;
        this.admissao = admissao;
        this.sexo = sexo;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.estadoCivil = estadoCivil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public final void setSenha(String senha) {
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
        return privilegio;
    }

    public void setPrivilegio(int privilegio) {
        this.privilegio = privilegio;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getAdmissao() {
        return admissao;
    }

    public void setAdmissao(Date admissao) {
        this.admissao = admissao;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {

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
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
}