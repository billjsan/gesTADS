package src.model.model;

public class Product {

    private final String TAG = Product.class.getSimpleName();
    private static Long _id = 0L;
    private Long id;

    private String nome;
    private String serialN;
    private String fabricante;
    private String descricao;
    private String fabricacao;
    private String validade;

    public Product() {
        //[LAS]
    }

    public Product(String nome, String serialN, String fabricante, String descricao,
                   String fabricacao, String validade) {
        //[LAS]
        this.nome = nome;
        this.serialN = serialN;
        this.fabricante = fabricante;
        this.descricao = descricao;
        this.fabricacao = fabricacao;
        this.validade = validade;
    }

    public Long getId(){
        return id;
    }
    public void generateId(){
        //[LAS] imprimir o id
        this.id = _id;
        _id ++;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSerialN() {
        return serialN;
    }

    public void setSerialN(String serialN) {
        this.serialN = serialN;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFabricacao() {
        return fabricacao;
    }

    public void setFabricacao(String fabricacao) {
        this.fabricacao = fabricacao;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}