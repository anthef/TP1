package assignments.assignment3;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment3.payment.*;
import assignments.assignment3.LoginManager;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class MainMenu {
    private final Scanner input;
    private final LoginManager loginManager;
    private static ArrayList<Restaurant> restoList = new ArrayList<>();
    private static ArrayList<User> userList;
    private static User userLoggedIn;

    
    public MainMenu(Scanner in, LoginManager loginManager) {
        this.input = in;
        this.loginManager = loginManager;
    }


    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(new Scanner(System.in), new LoginManager(new AdminSystemCLI(), new CustomerSystemCLI()));
        initUser();
        mainMenu.run();
    }

    public void run(){
        printHeader();
        boolean exit = false;
        while (!exit) {
            startMenu();
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1 -> login();
                case 2 -> exit = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
        System.out.println("Terima kasih telah menggunakan DepeFood!");
        input.close();
    }

    private void login(){
        System.out.println("\nSilakan Login:");
        System.out.print("Nama: ");
        String nama = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelp = input.nextLine();

        User userLoggedIn; 
        MainMenu.userLoggedIn = getUser(nama, noTelp);
        if(MainMenu.userLoggedIn!=null){
            System.out.printf("Selamat datang %s!", nama);
        }
        else{
            System.out.println("Pengguna dengan data tersebut tidak dapat ditemukan!");
            login();
        }

        UserSystemCLI user = loginManager.getSystem(MainMenu.userLoggedIn.role);
        if((MainMenu.userLoggedIn.role).equals("Customer")){
            CustomerSystemCLI users = (CustomerSystemCLI) user;
            users.run();
        }
        else if((MainMenu.userLoggedIn.role).equals("Admin")){
            AdminSystemCLI users = (AdminSystemCLI) user;
            users.run();
        }
        
    }
    private static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    private static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }


    public static void initUser(){
        userList = new ArrayList<User>();

        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer", new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer", new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer", new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(), 650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }
    
    public static User getUser(String nama, String noTelp){ //Method untuk validasi apabila nama dan nomor telepon sesuai
        User ret = null;
        if(userList != null){
            for(User usr : userList){
                if(usr.getNama().equals(nama) & usr.getNomorTelepon().equals(noTelp)){
                    ret = usr;
                    break;
                }
            }
        }
        return ret;
    }

    public static ArrayList<Restaurant> getRestoList(){
        return restoList;
    }
    public static void setRestoList(Restaurant resto){
        restoList.add(resto);
    }

    public static ArrayList<User> getUserList(){
        return userList;
    }

    public static User setUser(){
        return userLoggedIn;
    }
}