package src.model.model;

import java.util.ArrayList;
import java.util.List;

public class Supervisor extends Employee {

    public Supervisor() {
        this.privilegio = Employee.PRIVILEGE_SUPERVISOR;
    }

    @Override
    public void upGradeEmployee() {
        //upgrade to Admin
    }

    public boolean confirmOperarion(Integer operation){
        return false;
    }

    public List<String> getOperators(){
        return new ArrayList<>();
    }

    public List<String> serachOperator(String operator){
        return new ArrayList<>();
    }
}
