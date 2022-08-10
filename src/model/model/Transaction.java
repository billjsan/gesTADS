package src.model.model;

public class Transaction {

    public static final int TRANSACTION_INCOMMING = 1;
    public static final int TRANSACTION_OUTCOMMING = 2;

    private static Long _id = 0L;

    private Long id;
    private Long produtoId;
    private int tipoTransacao;
    private int quantidade;
    private Long solicitante;


    public void generateId(){

        this.id = _id;
        _id ++;
    }
    public void setTipoTranacao(int tipe) {

        this.tipoTransacao = tipe;
    }

    public void setQuantidade(int quantidade) {

        this.quantidade = quantidade;
    }

    public void setSolicitante(Long id) {

        this.solicitante = id;
    }

    public void setProdutoId(Long id) {

        this.produtoId = id;
    }

    public Long getId() {

        return this.id;
    }

    public Long getSolicitanteId() {

        return solicitante;
    }

    public Long getProdutoId() {

        return produtoId;
    }

    public int getQuantidade() {

        return quantidade;
    }

    public int getTipo() {

        return tipoTransacao;
    }
}
