package src;

import src.control.Control;
import sun.awt.windows.ThemeReader;

public class GesTADSApp {
    public static void main(String[] args) {
        new Control().startApplication();

//        Thread t = Thread.currentThread();
//        System.out.println(t.getName());
//
//        Thread minhaT = new Thread(new MeuRunnable());
//        minhaT.start();
    }
}