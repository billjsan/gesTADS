package src.ui.screen;

import src.model.model.Employee;
import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.List;
import java.util.Scanner;

public class CadastroScreen extends GesTADSUI {

    private final String TAG = "CadastroScreen";

    public CadastroScreen(Intent intent) {
        super(intent);
    }

    @Override
    public void onCreated() {
        super.onCreated();
    }

    @Override
    public void createView() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "createView");

        Intent intent = new Intent(Intent.ACTION_REGISTER);
        List<Integer> flags;

        if(mContextIntent != null && (mContextIntent.getAction() == Intent.ACTION_UI_FLAG)){
            flags = mContextIntent.getFlags();

            if(flags.contains(Intent.FLAG_FIRST_LOGIN)){
                System.out.println("Você é o primeiro usuário do sistema");

                intent.putInt(Intent.KEY_PRIVILEGE, Employee.PRIVILEGE_ADMIN);
            }
        }

        System.out.println("TELA DE CADASTRO");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please insert your name: ");
        String name = scanner.nextLine();

        System.out.println("Please insert your CPF: ");
        String cpf = scanner.nextLine();

        System.out.println("Please insert username: ");
        String username = scanner.nextLine();

        System.out.println("Please insert password: ");
        String password = scanner.nextLine();

        System.out.println("Please re-insert password: ");
        String passwordConfirm = scanner.nextLine();

        System.out.println("Please RG");
        String rg = scanner.nextLine();

        System.out.println("Please insert address");
        String address = scanner.nextLine();

        intent.putString(Intent.KEY_USERNAME, username);
        intent.putString(Intent.KEY_PASSWORD, password);
        intent.putString(Intent.KEY_PASSWORD_CONFIRM, passwordConfirm);
        intent.putString(Intent.KEY_NAME, name);
        intent.putString(Intent.KEY_CPF, cpf);
        intent.putString(Intent.KEY_RG, rg);
        intent.putString(Intent.KEY_ENDERECO, address);

        //completar
        intent.putString(Intent.KEY_PRIVILEGE, "address");
        intent.putString(Intent.KEY_ADMISSAO, address);
        intent.putString(Intent.KEY_CARGO, address);
        intent.putString(Intent.KEY_ESTADO_CIVIL, address);
        intent.putString(Intent.KEY_SEXO, address);

        BroadcastReceiver.sendBroadcast(intent);
    }
}