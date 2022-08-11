package src.model.repository.database;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.model.Transaction;

import java.util.List;

public interface GesTADSDataBaseInterface {

    /**
     * metodos do banco de dados
     */
    void startUpDadaBase();

    void closeDataBase();

    boolean isDBInitialized();

    /**
     * metodos do employee
     */
    Employee getEmployeeByCPF(String cpf);

    Employee getEmployeeByNome(String nome);

    List<Employee> getEmployees();

    void insertEmployee(Employee employee);

    List<String> getCargos();

    void setCargo(String position);

    void removeEmployee(Employee employee, Long id);

    void updateEmployee(Employee employee, Long id);

    /**
     * metodos do produto
     */
    Product getProdutoPorId(Long id);

    List<Product> getProdutos();

    void setProduto(Product produto);

    void updateProduto(Product product, Long id);

    void removeProdutoById(Long id);

    List<Product> getProdutosPorNome(String nome);

    /**
     * metodos da transação
     */
    void setTransaction(Transaction transaction);

    List<Transaction> getTransacoes();

}