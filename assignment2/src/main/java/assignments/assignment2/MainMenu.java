package main.java.assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
import assignments.assignment1.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args) {
        boolean programRunning = true;
        initUser();
        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                User userLoggedIn; 
                userLoggedIn = getUser(nama,noTelp);
                if(userLoggedIn!=null){
                    System.out.printf("Selamat datang %s!", nama);
                }
                else{
                    System.out.println("Pengguna dengan data tersebut tidak dapat ditemukan!");
                    continue;
                }

                boolean isLoggedIn = true;

                if(userLoggedIn.role == "Customer"){
                    while (isLoggedIn){
                        boolean valid = true;
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan();
                            case 2 -> handleCetakBill();
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    public static User getUser(String nama, String nomorTelepon){
        for(User usr : userList){
            if(usr.getName().equals(nama) & usr.getNomorTelepon().equals(nomorTelepon)){
                return usr
            }
            else {
                return null;
            }
        }
    }

    public static void handleBuatPesanan(){
        System.out.println("----------------Buat Pesanan----------------");
        boolean valid = true;
        while(valid){
            System.out.println("Nama Restoran: ");
            String nama = input.nextLine();
        }
    }

    public static void handleCetakBill(){
        System.out.println("----------------Cetak Bill----------------");
    }

    public static void handleLihatMenu(){
        System.out.println("----------------Lihat Menu----------------");
    }

    public static void handleUpdateStatusPesanan(){
        System.out.println("----------------Update Status Pesanan----------------");
    }

    public static void handleTambahRestoran() {
        System.out.println("----------------Tambah Restoran----------------");
        boolean isValid = true;
        while(isValid){
            boolean vld = true;
            boolean vld2 = true;
            System.out.print("Nama: ");
            String nama = input.nextLine();

            if (nama.length() < 4) {
                System.out.println("Nama Restoran tidak valid");
                continue;
            }

            for (Restaurant resto : restoList) {
                if (resto.getNama().equals(nama)) {
                    System.out.printf("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda", nama);
                    vld = false;
                    break;
                }
            }
            if(!vld){
                continue;
            }

            System.out.print("Jumlah Makanan: ");
            int jumlahMakanan = input.nextInt();
            input.nextLine(); 

            String[] makananArray = new String[jumlahMakanan];

            for (int i = 0; i < jumlahMakanan; i++) {
                String makananHarga = input.nextLine();
                makananArray[i] = makananHarga;
            }

            for (String makanan : makananArray) {
                String[] mknSplit = makanan.split(" ");
                try {
                    double harga = Double.parseDouble(mknSplit[mknSplit.length - 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Harga menu harus bilangan bulat!");
                    vld = false;
                    break;
                }
            }
            if(!vld){
                continue;
            }

            Restaurant newRestaurant = new Restaurant(nama);
            for (String makanan : makananArray) {
                String[] mknSplits = makanan.split(" ");
                StringBuilder namaMakananBuilder = new StringBuilder();
                for (int i = 0; i < mknSplits.length - 1; i++) {
                    namaMakananBuilder.append(mknSplits[i]).append(" ");
                }
                String namaMakanan = namaMakananBuilder.toString().trim();
                double harga = Double.parseDouble(mknSplits[mknSplits.length - 1]);
                Menu menu = new Menu(namaMakanan, harga);
                newRestaurant.addMenu(menu);
            }
            restoList.add(newRestaurant);
            System.out.printf("Restaurant %s Berhasil terdaftar.", nama);
            isValid = false;
        }
    }
        
    public static void handleHapusRestoran(){
        System.out.println("----------------Hapus Restoran----------------");
        boolean valid = true;
        while(valid){
            boolean isValid = false;
            System.out.println("Nama Restoran: ");
            String nama = input.nextLine();
            for(Restaurant resto : restoList){
                if((resto.getName().toLowerCase()).equals(nama.toLowerCase())){
                    restoList.remove(resto);
                    System.out.println("Restoran berhasil dihapus");
                    isValid = true;
                    break;
                }
                else{
                    isValid = false;
                }
            }
            if(!isValid){
                System.out.println("Restoran tidak terdaftar pada sistem");
                continue;
            }
            if(isValid){
                valid=false;
            }
        }
    }

    public static void initUser(){
        userList = new ArrayList<User>();
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}
