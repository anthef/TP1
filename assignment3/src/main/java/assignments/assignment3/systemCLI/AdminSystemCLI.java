package assignments.assignment3.systemCLI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import assignments.assignment3.MainMenu;
import assignments.assignment3.payment.*;
import assignments.assignment3.MainMenu;

public class AdminSystemCLI extends UserSystemCLI{
    private static final Scanner input = new Scanner(System.in);
    private ArrayList<Restaurant> restoList = MainMenu.getRestoList();
    @Override
    protected boolean handleMenu(int command){
        switch(command){
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    @Override
    protected void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    protected void handleTambahRestoran(){
        System.out.println("----------------Tambah Restoran----------------");
        boolean isValid = true;
        while(isValid){
            boolean vld = true;
            boolean vld2 = true;
            System.out.printf("Nama: ");
            String nama = input.nextLine();

            //Validasi input untuk nama restoran
            if (nama.length() < 4) {
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }
            if(restoList != null)
            {
                for (Restaurant resto : restoList) {
                    if(resto==null){
                        break;
                    }
                    if (resto.getNama().equals(nama)) {
                        System.out.printf("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n\n", nama);
                        vld = false;
                        break;
                    }
                }
            }
            if(!vld){
                continue;
            }

            System.out.printf("Jumlah Makanan: ");
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
                    System.out.println("Harga menu harus bilangan bulat!\n"); //Melakukan validasi bahwa harga menu makanan harus bilangan bulat
                    vld = false;
                    break;
                }
            }
            if(!vld){
                continue;
            }

            Restaurant newRestaurant = new Restaurant(nama);
            //Membuat object menu
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
            MainMenu.setRestoList(newRestaurant);
            System.out.printf("Restaurant %s Berhasil terdaftar.", nama);
            isValid = false;
        }
    }

    protected void handleHapusRestoran(){
        System.out.println("----------------Hapus Restoran----------------");
        boolean valid = true;
        while(valid){
            boolean isValid = false;
            System.out.printf("Nama Restoran: ");
            String nama = input.nextLine();
            for(Restaurant resto : restoList){
                if((resto.getNama().toLowerCase()).equals(nama.toLowerCase())){
                    restoList.remove(resto);
                    System.out.print("Restoran berhasil dihapus");
                    isValid = true;
                    break;
                }
                else{
                    isValid = false;
                }
            }
            if(!isValid){
                System.out.println("Restoran tidak terdaftar pada sistem\n");
                continue;
            }
            if(isValid){
                valid=false;
            }
        }
    }
    
}