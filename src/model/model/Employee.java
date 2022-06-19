package src.model.model;

public abstract class Employee {

    private String nome;
    private String senha;
    private int privilegio = 0;
    private String cargo;

    public Employee(String nome, String senha, int privilegio, String cargo) {
        this.nome = nome;
        this.senha = senha;
        this.privilegio = privilegio;
        this.cargo = cargo;
    }
}