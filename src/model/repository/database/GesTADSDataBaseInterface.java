package src.model.repository.database;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.model.Transacao;
import src.model.model.Transaction;

import java.util.List;
public interface GesTADSDataBaseInterface {

    //metodos do banco de dados
    void startUpDadaBase();
    void closeDataBase();
    boolean isDBInitialized();

    //metodos do employee
    Employee getEmployeeByCPF(String cpf);
    Employee getEmployeeByNome(String nome);
    List<Employee> getEmployees();
    void insertEmployee(Employee employee);
    List<String> getCargos();
    void setCargo(String position);
    void removeEmployee(Employee employee, Long id);
    void updateEmployee(Employee employee, Long id);

    //metodos do produto
    Product getProdutoPorId(Long id);
    List<Product> getProdutos();
    void setProduto(Product produto);
    @Deprecated
    void editProduto(Product produto, Long id);

    @Deprecated
    void removeProduto(Product produto);

    //metodos das transações
    @Deprecated
    void setSaidaProduto(Transacao transacao);
    @Deprecated
    void setEntradaProduto(Transacao transacao);

    @Deprecated
    Product getProdutoPorSerial(String serialNo);

    public Product getProdutoPorNome(String nome);
    public void removeProdutoById(Long id);

    void updateProduto(Product product, Long id);

    void setTransaction(Transaction transaction);

    List<Transaction> getTransacoes();

}
