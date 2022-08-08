package test;

import src.model.model.Employee;
import src.model.repository.Repository;

public class GesTADSTest {

    public static void main(String[] args) {

        //ScreenInputTest inputTest = new ScreenInputTest();

//        sujaATela();
//
//        esperaEmSeg(1);
//
//        ScreenInputTest.apagaScreen();
//
//        esperaEmSeg(1);
//
//        sujaATela();
//
//        esperaEmSeg(1);
//
//        ScreenInputTest.apagaScreen();

        /**
         * testes do banco de dados
         */

        Repository instance = Repository.getInstance();
        instance.startRepository();

        //se o banco estiver ok....
        if(instance.isDbReady()){
            instance.addEmployee(new Employee(/* colocar aqui os campos do employee*/));

            // mais testes aqui +
        }
    }

    private static void sujaATela() {
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
        System.out.println("sujei a tela");
    }

    private static void esperaEmSeg(int segundos) {
        int timeImMillis = segundos * 1000;
        try {
            Thread.sleep(timeImMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
