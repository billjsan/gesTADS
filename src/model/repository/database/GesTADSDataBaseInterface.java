package src.model.repository.database;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.model.Transacao;

import java.util.List;
public interface GesTADSDataBaseInterface {

    //metodos do banco de dados
    void startUpDadaBase();
    void closeDataBase();
    boolean isDBInitialized();

    //metodos do employee
    Employee getEmployeeByCPF(String cpf);
    List<Employee> getEmployees();
    void insertEmployee(Employee employee);
    List<String> getCargos();
    void setCargo(String position);
    void removeEmployee(Employee employee);
    void updateEmployee(Employee employee, Long id);

    //metodos do produto
    Product getProdutoPorId(Long id);
    List<Product> getProdutos();
    void setProduto(Product produto);
    void editProduto(Product produto, Long id);
    void removeProduto(Product produto);

    //metodos das transações
    void setSaidaProduto(Transacao transacao);
    void setEntradaProduto(Transacao transacao);

    Product getProdutoPorSerial(String serialNo);
}
