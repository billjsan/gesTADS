package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class RegisterEmployeeScreen extends GesTADSUI {


    private final String TAG = "RegisterEmployeeScreen";
    private String mContextFlag = "";
    private final String PRIMARY_SCREEN = "primary-screen";
    private final String EDIT_EMPLOYEE_SCREEN = "edit-employee-screen";


    public RegisterEmployeeScreen(Intent intent) {
        // [LAS] imprimir a action do intent

        super(intent);
    }

    /**
     * Método é chamado no momento da criação da tela pela classe pai GesTADSUI. Ele configura o contexto
     * em que a UI deve ser exibida (mContextFlag) a partir do intent de contexto (mContextIntent)
     */
    @Override
    public void onCreate() {
        // [LAS]

        if (mContextIntent.getAction() == Intent.ACTION_LAUNCH_EDIT_EMPLOYEE &&
                mContextIntent.hasExtras()) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "ACTION_EDIT_EMPLOYEE");
            }

            mContextFlag = EDIT_EMPLOYEE_SCREEN;

        }else if (mContextIntent.getAction() == Intent.ACTION_LAUNCH_REGISTER_EMPLOYEE_SCREEN &&
                mContextIntent.hasExtras()){
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "ACTION_LAUNCH_REGISTER_EMPLOYEE_SCREEN");
            }

            mContextFlag = PRIMARY_SCREEN;
        }else {

            mContextFlag = null;
        }
        super.onCreate();
    }

    /**
     * Metodo chamado na criação da view. Tudo o que será exibido na tela é definido
     * e panipulado aqui nesse método, que pode delegar ações para outros métodos dentro ou fora da tela
     */
    @Override
    public void createView() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "createView");

        switch (mContextFlag){
            case PRIMARY_SCREEN:

                registerEmployee();
                break;

            case EDIT_EMPLOYEE_SCREEN:

                editEmployee();
                break;

            default:

                unreachCase();
        }

        onDestroy();
    }

    private void unreachCase() {
        // [LAS]
        BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN));
    }

    private void registerEmployee() {
        // [LAS]

        System.out.println(formattedTitle("REGISTER"));
        System.out.println(formattedTitle("Bem vindo ao GesTADS"));
        System.out.println(formattedTitle("Vocês está registrando um usuário"));
        System.out.println();

        Intent intent = new Intent(Intent.ACTION_VALIDATE_NEW_USER);

        System.out.print("Please insert your name: ");
        intent.putString(Intent.KEY_EMPLOYEE_NAME, screenGetTextFromUser());

        System.out.print("Please insert your CPF: ");
        intent.putString(Intent.KEY_EMPLOYEE_CPF, screenGetTextFromUser());

        System.out.print("Please insert username: ");
        intent.putString(Intent.KEY_EMPLOYEE_USERNAME, screenGetTextFromUser());

        System.out.print("Please insert password: ");
        intent.putString(Intent.KEY_EMPLOYEE_PASSWORD, getPassword());

        System.out.println("Please insert the position: ");
        System.out.println();
        intent.putString(Intent.KEY_EMPLOYEE_CARGO, getCargo());

        BroadcastReceiver.sendBroadcast(intent);
    }

    /**
     * Esse método é chamado quando a ação dessa tela é editar um utilizador. Essa tela foi
     * implementada na mesma classe da tela de cadastro para reutilizar metodos úteis como getCargo()
     * e getPassword() e evitar boilet plate, talvez no futuro essa tela seja implementada na sua propria classe
     */
    private void editEmployee(){
        // [LAS]

        List<Integer> contextFlags = getContextFlags();
        if(contextFlags.contains(Intent.FLAG_RESULT_SET)){
            try {

                List<Intent> list = (List<Intent>) mContextIntent.getList(Intent.KEY_RESULT_SET);
                Intent employeeContentIntent = list.get(0);

                System.out.println(formattedTitle("EDITAR USUÁRIO"));
                System.out.println(formattedTitle("Bem vindo ao GesTADS"));
                System.out.println(formattedTitle("Vocês está editando um usuário"));
                System.out.println();
                System.out.println("Select one item to edit it:");
                System.out.println();
                System.out.println(formattedLineMenu("Name: " +
                        employeeContentIntent.getString(Intent.KEY_EMPLOYEE_NAME), "[1]"));
                System.out.println(formattedLineMenu("CPF: " +
                        employeeContentIntent.getString(Intent.KEY_EMPLOYEE_CPF), "[2]"));
                System.out.println(formattedLineMenu("Username: " +
                        employeeContentIntent.getString(Intent.KEY_EMPLOYEE_USERNAME), "[3]"));
                System.out.println(formattedLineMenu("Position: " +
                        employeeContentIntent.getString(Intent.KEY_EMPLOYEE_CARGO), "[4]"));
                System.out.println();
                System.out.println(formattedLineMenu("Cancel", "[0]"));
                System.out.println(formattedLineMenu("Save changes", "[s]"));
                System.out.println();
                System.out.print("Chose one option bove:");

                handleEditEmployeeOption(employeeContentIntent);

            }catch (Exception e){
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "Ocorreu um erro ao recuperar empregado: " + e.getMessage());

                showGesTADSUIDialogScreen("Ocorreu um erro ao recuperar empregado!");
                BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN));
            }

        }else {
            showGesTADSUIDialogScreen("Essa etapa não foi prevista!");
            BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN));
        }
    }

    /**
     * Esse método trata a escolha do usuário sobre quais campos deseja editar e direciona o fluxo
     * da aplicação de acordo com a decisão. Recene como @param employeeContentIntent um intent contendo
     * os dados do usuário que será editado.
     */
    private void handleEditEmployeeOption(Intent employeeContentIntent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "handleEditEmployeeOption");

        String userInput = screenGetTextFromUser();

        switch (userInput){
            case "1":
                System.out.print("Enter new name: ");
                employeeContentIntent.putString(Intent.KEY_EMPLOYEE_NAME, screenGetTextFromUser());
                break;

            case "2":
                System.out.print("Enter new CPF: ");
                employeeContentIntent.putString(Intent.KEY_EMPLOYEE_CPF, screenGetTextFromUser());
                break;

            case "3":
                System.out.print("Enter new username: ");
                employeeContentIntent.putString(Intent.KEY_EMPLOYEE_USERNAME, screenGetTextFromUser());
                break;

            case "4":
                System.out.println("Enter new position: ");
                employeeContentIntent.putString(Intent.KEY_EMPLOYEE_CARGO, getCargo());
                break;

            case "0":
                BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN));
                return;

            case "s":

                mContextIntent = new Intent(Intent.ACTION_UPDATE_EMPLOYEE);
                mContextIntent.putFlag(Intent.FLAG_RESULT_SET);
                break;

        }

        List<Intent> intents = new ArrayList<>();
        intents.add(employeeContentIntent);

        // nesse ponto o mContextIntent é configurado para chamar a tela de edição novamente, desta vez com os dados
        //atualizados de acordo com a entrada do usuário. Em resumo essa tela chama a si propria com o campo editado
        mContextIntent.putList(Intent.KEY_RESULT_SET, intents);
        BroadcastReceiver.sendBroadcast(mContextIntent);
    }

    private String getPassword() {
        //[LAS]

        boolean isPswrdSetd = false;
        String pass = "";
        while (!isPswrdSetd) {
            String pswrd = screenGetTextFromUser();
            System.out.print("Please re-insert password: ");
            String repswrd = screenGetTextFromUser();

            if (!pswrd.equals(repswrd)) {
                System.out.println("password mismatch");
                System.out.print("Please insert password: ");
            } else {
                pass = pswrd;
                isPswrdSetd = true;

            }
        }
        return pass;
    }

    private String getCargo() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "getCargo");

        boolean isPositionSet = false;
        String cargo = "";
        while (!isPositionSet) {

            try {
                List<String> cargos = (List<String>) mContextIntent.getList(Intent.KEY_DATA_CARGOS);

                int listSize = cargos.size();
                for (int i = 0; i < cargos.size(); i++) {

                    System.out.println(formattedLineMenu(cargos.get(i), "[" + i + "] "));
                }

                System.out.print("cargo: ");
                int input = Integer.parseInt(screenGetTextFromUser());

                if (input >= 0 && input < listSize) {

                    cargo = cargos.get(input);
                    isPositionSet = true;
                } else {
                    throw new MissingFormatArgumentException("array out of boundary");
                }

            } catch (NullPointerException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) {
                    GesLogger.e(TAG, e.getMessage());
                }
                System.out.println(formattedTitle("erro"));

            } catch (MissingFormatArgumentException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) {
                    GesLogger.e(TAG, e.getMessage());
                }
                System.out.println(formattedTitle("dado inválido"));
            } catch (NumberFormatException e){
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) {
                    GesLogger.e(TAG, e.getMessage());
                }
                System.out.println(formattedTitle("você não digitou um número"));
            } catch (Exception e){
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) {
                    GesLogger.e(TAG, e.getMessage());
                }
                System.out.println(formattedTitle("Algo deu muito errado"));
            }

        }
        return cargo;
    }

    @Override
    protected void onDestroy() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "onDestroy");
        super.onDestroy();
    }

}