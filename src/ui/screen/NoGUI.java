package src.ui.screen;

import java.util.Scanner;

public class NoGUI{

    private boolean isLogged = false;
    public void startUI() {

        System.out.println("Welcome to GesTADS System");
        Scanner inputStream = new Scanner(System.in);
        while (!isLogged){
            System.out.println("Press enter key to login");

            String input = inputStream.nextLine();
            if (input != null  && input.equals("")){
                //showLoginDialog();
            }
        }
    }

    public void showLoginDialog() {

        System.out.print("Enter login: ");
        Scanner input = new Scanner(System.in);

        String login = input.nextLine();

        if(login != null){
            if(login.equals("willian"))
                System.out.println(login);
        }

        System.out.print("Enter password: ");

        String pass = input.nextLine();

        if(pass != null){
            System.out.println(pass);
        }

        if(pass != null && login != null){
            if(login.equals("willian") && pass.equals("1234")){
                System.out.println("you're logged in");
            }
        }
    }

    public void showMainScreen() {

    }
}
