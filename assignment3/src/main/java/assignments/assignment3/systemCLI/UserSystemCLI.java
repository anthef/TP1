package assignments.assignment3.systemCLI;

import java.util.Scanner;
//Abstract class
public abstract class UserSystemCLI {
    protected Scanner input = new Scanner(System.in);
    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }
    //Abstract method
    abstract void displayMenu();
    abstract boolean handleMenu(int command);
}
