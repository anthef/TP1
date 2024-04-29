package assignments.assignment3.systemCLI;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import assignments.assignment3.payment.*;
import assignments.assignment3.MainMenu;


public class CustomerSystemCLI extends UserSystemCLI  {
    private static final Scanner input = new Scanner(System.in);
    private ArrayList<Restaurant> restoList = MainMenu.getRestoList();
    
    @Override
    protected boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    @Override
    protected void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
    
    void handleBuatPesanan(){
        User user = MainMenu.setUser();
        System.out.println("----------------Buat Pesanan----------------");
        boolean valid = true;
        while (valid) {
            System.out.printf("Nama Restoran: ");
            String nama = input.nextLine();
            boolean validNama = false;
            Restaurant restos = null;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(nama)) { //Restoran yang dipesan harus sudah terdaftar
                    validNama = true;
                    restos = resto;
                    break;
                }
            }
            if (!validNama) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n"); //Validasi input untuk restoran yang tidak terdaftar
                continue;
            }

            System.out.printf("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalOrder = input.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Validasi tanggal
            try {
                LocalDate tanggal = LocalDate.parse(tanggalOrder, formatter);
            } catch (Exception e) {
                System.out.printf("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n\n");
                continue;
            }

            System.out.printf("Jumlah Pesanan: "); //Input jumlah pesanan
            int jumlahPesanan = input.nextInt();
            input.nextLine();
            System.out.println("Order: ");
            String[] tempMenu = new String[jumlahPesanan]; //Meminta input dan membuat array untuk menampung input
            for (int i = 0; i < jumlahPesanan; i++) {
                String namaMenu = input.nextLine();
                tempMenu[i] = namaMenu;
            }

            boolean semuaMenuValid = true;
            Menu[] menuMakanan = new Menu[jumlahPesanan]; //Melakukan validasi apakah menu pada input sudah sesuai dengan menu yang terdaftar
            for (int k = 0; k < jumlahPesanan; k++) {
                boolean found = false;
                for (Menu restMenu : restos.getMenu()) {
                    if (restMenu.getNamaMakanan().equalsIgnoreCase(tempMenu[k])) {
                        found = true;
                        menuMakanan[k] = restMenu; 
                        break;
                    }
                }
                if (!found) {
                    semuaMenuValid = false;
                    System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                    break; 
                }
            }
            if (!semuaMenuValid) {
                continue;
            }

            String orderID = generateOrderID(nama, tanggalOrder, user.getNomorTelepon()); //membuat order id
            Order newOrder = new Order(orderID, tanggalOrder, user.getOngkir(user.getLokasi()), restos, menuMakanan); //Membuat object order dan menambahkannya ke order history
            user.addOrderHistory(newOrder);
            System.out.print("Pesanan dengan ID " + orderID + " diterima!"); 
            valid = false;
        }
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
        String output = ""; 
        char compare = ' '; //Mengambil 4 karakter pertama
        for(int i = 0;  i<namaRestoran.length(); i++){
            char current  = namaRestoran.charAt(i);
            if(output.length()<4 && (current != compare)){
                output += Character.toUpperCase(current);
            }
            else if(output.length()<4 && (current == compare)){
                continue;
            }
            if(output.length()==4){
                break;
            }
        }

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



    void handleCetakBill(){
        User user = MainMenu.setUser();
        System.out.println("----------------Cetak Bill----------------");
        boolean valid = true;
        while(valid){
            System.out.printf("Masukkan Order ID: ");
            String orderID = input.nextLine();
            boolean isValid = false;
            Order tempOrder = null;
            for(Order order : user.getOrderHistory()){ //Melakukan validasi apakah order id sudah terdaftar
                if(orderID.equals(order.getOrderId())){
                    tempOrder = order;
                    isValid= true;
                    break;
                }
            }
            if(!isValid){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            //Print output
            System.out.println("Bill:");
            System.out.println("Order ID: " + orderID);
            System.out.println("Tanggal Pemesanan: " + tempOrder.getTanggal());
            System.out.println("Restaurant: " + tempOrder.getRestaurant().getNama());
            System.out.println("Lokasi Pengiriman: " + user.getLokasi());
            if(!tempOrder.getOrderFinished()){
                System.out.println("Status Pengiriman: " + "Not Finished");
            }
            else{
                System.out.println("Status Pengiriman: " + "Selesai");
            }
            System.out.println("Pesanan:");
            double totalHarga = 0;
            for(Menu menu : tempOrder.getItems()){
                int harga = (int) menu.getHarga();
                System.out.println("- " + menu.getNamaMakanan() + " " + harga);
                totalHarga += menu.getHarga();
            }
            int biayaOngkosKirim = user.getOngkir(user.getLokasi());
            System.out.println("Biaya Ongkos Kirim: Rp " + biayaOngkosKirim);
            int totalHarga2 = (int)(totalHarga);
            int sumHarga = biayaOngkosKirim + totalHarga2;
            System.out.print("Total Biaya: Rp " + sumHarga +"\n");
            valid = false;
        }
    }

    void handleLihatMenu(){
        User user = MainMenu.setUser();
        System.out.println("----------------Lihat Menu----------------");
        boolean valid = true;
        while(valid){
            System.out.printf("Nama Restoran: ");
            String namaRestoran = input.nextLine();
    
            Restaurant restoranDitemukan = null;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(namaRestoran)) { //Melakukan validasi untuk nama restoran
                    restoranDitemukan = resto;
                    break;
                }
            }
    
            if (restoranDitemukan == null) {
                System.out.println("Restoran tidak ditemukan.\n");
                continue;
            }
    
            ArrayList<Menu> daftarMenu = restoranDitemukan.getMenu();
            //Melakukan sorting menu dengan menggunakan bubble sort algorithm
            for (int i = 0; i < daftarMenu.size() - 1; i++) {
                for (int j = 0; j < daftarMenu.size() - i - 1; j++) {
                    if (daftarMenu.get(j).getHarga() > daftarMenu.get(j + 1).getHarga()) {
                        Menu temp = daftarMenu.get(j);
                        //Menu akan di swap supaya memiliki urutan yang benar
                        daftarMenu.set(j, daftarMenu.get(j + 1));
                        daftarMenu.set(j + 1, temp);
                    } 
                    //Kondisi apabila harga nya sama
                    else if (daftarMenu.get(j).getHarga() == daftarMenu.get(j + 1).getHarga()) {
                        if (daftarMenu.get(j).getNamaMakanan().compareTo(daftarMenu.get(j + 1).getNamaMakanan()) > 0) {
                            Menu temp = daftarMenu.get(j);
                            //Menu akan di swap supaya memiliki urutan yang benar
                            daftarMenu.set(j, daftarMenu.get(j + 1));
                            daftarMenu.set(j + 1, temp);
                        }
                    }
                }
            }
            
            int counter = 1;
            System.out.println("Menu:"); //Print output menu
            for (Menu menu : daftarMenu) {
                int harga = (int) menu.getHarga();
                System.out.println(counter + ". " +menu.getNamaMakanan() + " " + harga);
                counter +=1;
            }
            valid = false;
        }
    }

    void handleBayarBill(){
        User user = MainMenu.setUser();
        System.out.println("----------------Bayar Bill----------------");
        boolean valid = true;
        while(valid){
            System.out.printf("Masukkan Order ID: ");
            String orderID = input.nextLine();
            boolean isValid = false;
            Order tempOrder = null;
            for(Order order : user.getOrderHistory()){ //Melakukan validasi apakah order id sudah terdaftar
                if(orderID.equals(order.getOrderId())){
                    tempOrder = order;
                    isValid= true;
                    break;
                }
            }
            if(!isValid){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            //Print output
            System.out.println("Bill:");
            System.out.println("Order ID: " + orderID);
            System.out.println("Tanggal Pemesanan: " + tempOrder.getTanggal());
            System.out.println("Restaurant: " + tempOrder.getRestaurant().getNama());
            System.out.println("Lokasi Pengiriman: " + user.getLokasi());
            if(!tempOrder.getOrderFinished()){
                System.out.println("Status Pengiriman: " + "Not Finished");
            }
            else{
                System.out.println("Status Pengiriman: " + "Selesai");
            }
            System.out.println("Pesanan:");
            double totalHarga = 0;
            for(Menu menu : tempOrder.getItems()){
                int harga = (int) menu.getHarga();
                System.out.println("- " + menu.getNamaMakanan() + " " + harga);
                totalHarga += menu.getHarga();
            }
            int biayaOngkosKirim = user.getOngkir(user.getLokasi());
            System.out.println("Biaya Ongkos Kirim: Rp " + biayaOngkosKirim);
            int totalHarga2 = (int)(totalHarga);
            int sumHarga = biayaOngkosKirim + totalHarga2;
            System.out.print("Total Biaya: Rp " + sumHarga);
            long totalPembayaran = (long) sumHarga;
            valid = false;
            Restaurant resto = tempOrder.getRestaurant();

            System.out.println("\n\n\nPembayaran:");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit");
            System.out.print("Pilihan Metode Pembayaran: ");
            int metodePembayaran = input.nextInt();
            if(user.getPayment() instanceof CreditCardPayment && (metodePembayaran==1)){
                if(tempOrder.getOrderFinished()){
                    System.out.println("Pesanan dengan ID ini sudah lunas!");
                }
                else{
                CreditCardPayment payment = (CreditCardPayment) user.getPayment();
                long charge = payment.countTransactionFee(totalPembayaran);
                long biayaResult = payment.processPayment(totalPembayaran);
                user.setSaldo(user.getSaldo()-biayaResult);
                resto.setSaldo(resto.getSaldo()+totalPembayaran);
                tempOrder.setOrderFinished(true);
                System.out.print("Berhasil Membayar Bill sebesar Rp " + sumHarga + " dengan biaya transaksi sebesar Rp " + charge);
                }
            }
            else if (((user.getPayment() instanceof CreditCardPayment) && (metodePembayaran == 2)) || 
                ((user.getPayment() instanceof DebitPayment) && (metodePembayaran == 1))) {System.out.println("User belum memiliki metode pembayaran ini!");
            }

            else if(user.getPayment() instanceof DebitPayment && (metodePembayaran==2)){
                DebitPayment payment = (DebitPayment) user.getPayment();
                long biayaResult = payment.processPayment(totalPembayaran);
                if(tempOrder.getOrderFinished()){
                    System.out.println("Pesanan dengan ID ini sudah lunas!");
                }
                else{
                    if(user.getSaldo()>=biayaResult){
                        payment.processPayment(totalPembayaran);
                        user.setSaldo(user.getSaldo()-biayaResult);
                        resto.setSaldo(resto.getSaldo()+totalPembayaran);
                        tempOrder.setOrderFinished(true);
                    }
                    else{
                        System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
                    }
                }
            }
        }
    }

    void handleCekSaldo(){
        User user = MainMenu.setUser();
        System.out.println("Sisa saldo sebesar Rp " + user.getSaldo());
    }

    
}

