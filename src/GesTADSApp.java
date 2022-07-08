package src;

import src.control.Control;

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
}
