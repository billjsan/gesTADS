package src.model.model;

public class Product {

    private static Long _id = 0L;

    private final String TAG = Product.class.getSimpleName();

    private Long id;
    private String nome;
    private String fabricante;
    private int qtdEsqoque = 0;

    public Product() {
        //[LAS]
    }

    public Product(String nome,  String fabricante) {
        //[LAS]
        this.nome = nome;
        this.fabricante = fabricante;
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



    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }


    public int getQtdEstoque() {
        return qtdEsqoque;
    }

    public void setQtdEsqoque(int qtd){

        this.qtdEsqoque = qtd;
    }

    public void setId(Long id) {
        this.id = id;
    }
}