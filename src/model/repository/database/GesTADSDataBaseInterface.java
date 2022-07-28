package src.model.repository.database;

import src.model.model.Employee;
import src.util.tools.Intent;

import java.util.List;

public interface GesTADSDataBaseInterface {
    void startUpDadaBase();
    void closeDataBase();
    boolean isDBInitialized();
    void executeInsertQuery(String query);

    /** abaixo virão todos os metodos que irão
     * efetivamente manipular o banco de dados.
     * inserir, remover, buscar, etc. As queries
     * SQL serão executadas a partir daqui com a criaçao
     * de threads separadas para cada consulta ao banco.
     *
     * precisamos de uma abordagem pra fazer isso como
     * por exemplo estudar como usar as APIS java para banco
     * de dados ou até mesmo usar outra abordagem para salvar os
     * dados. De todo modo o banco de dados real estará abaixo do
     * objeto Database. Cada objeto Database tem sua própria implementação
     * podendo um ser SQL o outro ser File, etc.
     */

   void allOtherMethods();

    Employee getEmployeeByCPF(String cpf);

    void insertEmployee(Employee employee);

    boolean isEmptyDB();

    List<Employee> getEmployees();

    List<String> getCargo();

    void setCargo(String position);

    void removeEmployee(Employee employee);

    void updateEmployee(Employee employee, Long id);
}
