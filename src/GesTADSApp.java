package src;

import src.control.Control;
import src.util.tools.BroadcastReceiver;
import src.util.tools.Intent;

// melhoria de codigo = [ICS]
// adicao de logs no futuro = [LAS]
// mudança de metodologia = [MCS]
// adicao de documentacao no futuro = [CDS]
public class GesTADSApp {
    public static void main(String[] args) {
        // [ICS] adicionar uma intent que recebe
        // argumentos ao inicializar o programa
        // que decide o tipo da UI e do DB


        new Control().startApplication();
    }


    public static void infoThread(){

//        int count = Thread.activeCount();
//        System.out.println("currently active threads = " + count);
//
//        Thread th[] = new Thread[count];
//        // returns the number of threads put into the array
//        Thread.enumerate(th);
//
//        // prints active threads
//        for (int i = 0; i < count; i++) {
//            System.out.println(i + ": " + th[i]);
//        }
    }
}
