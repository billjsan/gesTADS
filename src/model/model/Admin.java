package src.model.model;

import java.sql.Struct;

public class Admin extends Employee {

    private void addUser(){

    }

    private void removeUser(){}

    private void addPrivilege(String user, Integer privilege){}

    private void removePrivilege(String user, Integer privilege){}

    private void removeAllPrivileges(String user){}

    private void runDBQuerie(String query){}

    @Override
    public void upGradeEmployee() {
        //can't be upgradded
    }
}
