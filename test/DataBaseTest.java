package test;

import src.model.model.Employee;
import src.model.repository.Repository;

public class DataBaseTest {
    public static void testeConexao (){
        Repository instance = Repository.getInstance();
        instance.startRepository();

    }
}
