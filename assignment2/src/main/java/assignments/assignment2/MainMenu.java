package assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate; //import built in library untuk input tanggal
import java.time.format.DateTimeFormatter;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args) {
        boolean programRunning = true;
        initUser();
        printHeader();
        while(programRunning){
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
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
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

    public static int convertCTV(char c) {  //Function untuk mengubah character menjadi suatu value
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 10;
        } else {
            return -1;
        }
    }

    public static char convertVTC(int value) { //Function untuk mengembalikan character berdasarkan value yang dipunya
        if (value >= 0 && value <= 9) {
            return (char) (value + '0');
        } else if (value >= 10 && value <= 35) {
            return (char) (value - 10 + 'A');
        } else {
            return '?';
        }
    }

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) { //Function untuk generate order ID
        String namaRes = namaRestoran.substring(0,4); //Mengambil 4 karakter pertama dari input dan 
        String output = "";
        output+= namaRes.toUpperCase(); //Mengubah menjadi kapital

        String [] tanggalSplit = tanggalOrder.split("/");
        for(String tggl : tanggalSplit){
            output += tggl; //Mengekstaksi input tanggal
        }
        
        int totalDigitNoTelp = 0;
        for(int k = 0; k<noTelepon.length();k++){
            totalDigitNoTelp += Character.getNumericValue(noTelepon.charAt(k)); //Menjumlahkan seluruh digit nomor telepon
        }
        int digitNoTelp = totalDigitNoTelp%100;
        if(digitNoTelp<10){
            output += "0"+String.valueOf(digitNoTelp);
        }
        else{
            output += String.valueOf(digitNoTelp);
        }
        

        int totalCheckSum1 = 0;
        int totalCheckSum2 = 0;
        for(int l = 0; l<output.length();l++){
            if(l%2==0){
                totalCheckSum1 += convertCTV(output.charAt(l)); //Membuat checksum index genap
            }
            else{
                totalCheckSum2 += convertCTV(output.charAt(l)); //Membuat checksum index ganjil
            }
        }

        int checkSum1 = totalCheckSum1%36;
        int checkSum2 = totalCheckSum2%36;

        char check1 = convertVTC(checkSum1);
        char check2 = convertVTC(checkSum2);

        output += check1;
        output+= check2;

        return output;
    }

    public static User getUser(String nama, String nomorTelepon){
        User ret = null;
        for(User usr : userList){
            if(usr.getNama().equals(nama) & usr.getNomorTelepon().equals(nomorTelepon)){
                ret = usr;
                break;
            }
        }
        return ret;
    }

    public static void handleBuatPesanan(User user) {
        System.out.println("----------------Buat Pesanan----------------");
        boolean valid = true;
        while (valid) {
            System.out.printf("Nama Restoran: ");
            String nama = input.nextLine();
            boolean validNama = false;
            Restaurant restos = null;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(nama)) {
                    validNama = true;
                    restos = resto;
                    break;
                }
            }
            if (!validNama) {
                System.out.println("Restoran tidak terdaftar pada sistem.");
                continue;
            }

            System.out.printf("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalOrder = input.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate tanggal = LocalDate.parse(tanggalOrder, formatter);
            } catch (Exception e) {
                System.out.printf("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n\n");
                continue;
            }

            System.out.printf("Jumlah Pesanan: ");
            int jumlahPesanan = input.nextInt();
            input.nextLine();
            System.out.printf("Order: ");
            String[] tempMenu = new String[jumlahPesanan];
            for (int i = 0; i < jumlahPesanan; i++) {
                String namaMenu = input.nextLine();
                tempMenu[i] = namaMenu;
            }

            boolean semuaMenuValid = true;
            Menu[] menuMakanan = new Menu[jumlahPesanan]; 
            for (int k = 0; k < jumlahPesanan; k++) {
                boolean found = false;
                for (Menu restMenu : restos.getMenuList()) {
                    if (restMenu.getNamaMakanan().equalsIgnoreCase(tempMenu[k])) {
                        found = true;
                        menuMakanan[k] = restMenu; 
                        break;
                    }
                }
                if (!found) {
                    semuaMenuValid = false;
                    System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                    break; 
                }
            }
            if (!semuaMenuValid) {
                continue;
            }

            String orderID = generateOrderID(nama, tanggalOrder, user.getNomorTelepon());
            Order newOrder = new Order(orderID, tanggalOrder, user.getOngkir(user.getLokasi()), restos, menuMakanan);
            user.addOrderHistory(newOrder);
            System.out.println("Pesanan dengan ID " + orderID + " diterima!");
            valid = false;
        }
    }

    public static void handleCetakBill(User user){
        System.out.println("----------------Cetak Bill----------------");
        boolean valid = true;
        while(valid){
            System.out.printf("Masukkan Order ID: ");
            String orderID = input.nextLine();
            boolean isValid = true;
            Order tempOrder = null;
            for(Order order : user.getOrderHistory()){
                if(orderID.equals(order.getOrderID())){
                    tempOrder = order;
                    break;
                }
                isValid=false;
            }
            if(!isValid){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println("Bill:");
            System.out.println("Order ID: " + orderID);
            System.out.println("Tanggal Pemesanan: " + tempOrder.getTanggalPemesanan());
            System.out.println("Restaurant: " + tempOrder.getRestaurantName());
            System.out.println("Lokasi Pengiriman: " + user.getLokasi());
            if(!tempOrder.getOrderFinished()){
                System.out.println("Status Pengiriman: " + "Not Finished");
            }
            else{
                System.out.println("Status Pengiriman: " + "Selesai");
            }
            System.out.println("Pesanan:");
            double totalHarga = 0;
            for(Menu menu : tempOrder.getItemsList()){
                System.out.println("- " + menu.getNamaMakanan() + " " + menu.getHarga());
                totalHarga += menu.getHarga();
            }
            int biayaOngkosKirim = user.getOngkir(user.getLokasi());
            System.out.println("Biaya Ongkos Kirim: Rp " + biayaOngkosKirim);
            int totalHarga2 = (int) (totalHarga);
            int sumHarga = biayaOngkosKirim + totalHarga2;
            System.out.println("Total Biaya: Rp " + sumHarga);
            valid = false;
        }
    }

    public static void handleLihatMenu(){
        System.out.println("----------------Lihat Menu----------------");
        boolean valid = true;
        while(valid){
            System.out.printf("Nama Restoran: ");
            String namaRestoran = input.nextLine();
    
            Restaurant restoranDitemukan = null;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                    restoranDitemukan = resto;
                    break;
                }
            }
    
            if (restoranDitemukan == null) {
                System.out.println("Restoran tidak ditemukan.");
                continue;
            }
    
            ArrayList<Menu> daftarMenu = restoranDitemukan.getMenuList();
            for (int i = 0; i < daftarMenu.size() - 1; i++) {
                for (int j = 0; j < daftarMenu.size() - i - 1; j++) {
                    if (daftarMenu.get(j).getHarga() > daftarMenu.get(j + 1).getHarga()) {
                        Menu temp = daftarMenu.get(j);
                        daftarMenu.set(j, daftarMenu.get(j + 1));
                        daftarMenu.set(j + 1, temp);
                    } else if (daftarMenu.get(j).getHarga() == daftarMenu.get(j + 1).getHarga()) {
                        if (daftarMenu.get(j).getNamaMakanan().compareTo(daftarMenu.get(j + 1).getNamaMakanan()) > 0) {
                            Menu temp = daftarMenu.get(j);
                            daftarMenu.set(j, daftarMenu.get(j + 1));
                            daftarMenu.set(j + 1, temp);
                        }
                    }
                }
            }
            
            int counter = 1;
            for (Menu menu : daftarMenu) {
                System.out.println(counter + ". " +menu.getNamaMakanan() + " " + menu.getHarga());
                counter +=1;
            }
        }
    }
    

    public static void handleUpdateStatusPesanan(User user){
        System.out.println("----------------Update Status Pesanan----------------");
        boolean valid = true;
        while(valid){
            System.out.printf("Masukkan Order ID: ");
            String orderID = input.nextLine();
            boolean isValid = true;
            Order tempOrder = null;
            for(Order order : user.getOrderHistory()){
                if(orderID.equals(order.getOrderID())){
                    tempOrder = order;
                    break;
                }
                isValid=false;
            }
            if(!isValid){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.printf("Status: ");
            String status = input.nextLine();
            if(status.equals("Selesai") & (tempOrder.getOrderFinished() == false)){
                System.out.println("Status pesanan dengan ID " + orderID + " berhasil diupdate!");
            }
            else{
                System.out.println("Status pesanan dengan ID " + orderID + " tidak berhasil diupdate!");
            }
        }
    }

    public static void handleTambahRestoran() {
        System.out.println("----------------Tambah Restoran----------------");
        boolean isValid = true;
        while(isValid){
            boolean vld = true;
            boolean vld2 = true;
            System.out.printf("Nama: ");
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
