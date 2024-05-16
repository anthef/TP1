package assignments.assignment1;  //File termasuk ke package assignment

import java.time.LocalDate; //import built in library untuk input tanggal
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in); //Untuk input data

    public static void showMenu(){  //function show menu
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.err.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
    }

    public static void repMenu(){
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
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
        char compare = ' '; //Mengambil 4 karakter pertamac
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
    public static int calculateDeliveryCost(String location) {
        switch (location) {
            case "P":
                return 10000;
            case "U":
                return 20000;
            case "T":
                return 35000;
            case "S":
                return 40000;
            case "B":
                return 60000;
            default:
                return 0;
        }
    }
    public static String generateBill(String OrderID, String lokasi){
        String lokasiPrint = lokasi.toUpperCase();
        String biayaOngkosKirim = "0"; 
        String tanggalOrder = "";
        switch(lokasiPrint){
            case "P" :
                biayaOngkosKirim = "10.000";
                break;
            case "U" :
                biayaOngkosKirim = "20.000";
                break;
            case "T" :
                biayaOngkosKirim = "35.000";
                break;
            case "S" :
                biayaOngkosKirim = "40.000";
                break;
            case "B" :
                biayaOngkosKirim = "60.000";
                break;
        }
        for(int s = 4; s<12;s++){
            if(s==5 || s==7){
                tanggalOrder+=OrderID.charAt(s) + "/"; //Membuat output untuk tanggal order
            }
            else {
                tanggalOrder+=OrderID.charAt(s);
            }
        }
        String bill = "Bill:\n" + //Membuat output
                    "Order ID: " + OrderID + "\n" +
                    "Tanggal Pemesanan: " + tanggalOrder + "\n" +
                    "Lokasi Pengiriman: " + lokasiPrint + "\n" +
                    "Biaya Ongkos Kirim: Rp " + biayaOngkosKirim +"\n";

        return bill;
    }

    public static boolean validateDate(String date) {
        String[] parts = date.split("/");
        if (parts.length != 3) {
            return false;
        }

        for (String part : parts) {
            if (!part.chars().allMatch(Character::isDigit)) {
                return false;
            }
        }

        return parts[0].length() == 2 && parts[1].length() == 2 && parts[2].length() == 4;
    }

    
    public static void main(String[] args) {
        Boolean exitMenu = true;
        showMenu();
        while (exitMenu) {
            repMenu();
            System.out.printf("Pilihan Menu: ");  //Melakukan input pilihan menu
            int pilihMenu = input.nextInt();
            input.nextLine();
            Boolean rturn = false;

            if(pilihMenu==1){
                System.out.printf("Nama Restoran: ");
                String tempNamaRestoran = input.nextLine().trim(); //Menghilangkan spasi di bagian depan/belakang

                if(tempNamaRestoran.length()<4){ //Melakukan validasi input
                    System.out.printf("Nama Restoran tidak valid!\n\n");
                    continue;
                }

                System.out.printf("Tanggal Pemesanan: "); //Melakukan input tanggal pemesanan
                String tanggalOrder = input.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate tanggal = LocalDate.parse(tanggalOrder, formatter);
                } catch (Exception e) {
                    System.out.printf("Tanggal Pemesanan dalam format DD/MM/YYYY!\n\n"); //Validasi input
                    continue;
                }

                System.out.printf("No. Telpon: "); //Melakukan input nomor telepon 
                String tempNomorTelepon = input.nextLine();
                String noTelepon = "";
                for(int i=0; i<tempNomorTelepon.length();i++){
                    if(Character.isDigit(tempNomorTelepon.charAt(i))){
                        noTelepon+=tempNomorTelepon.charAt(i);
                    }
                    else{
                        System.out.printf("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.\n\n"); //Validasi input untuk nomor telepon
                        rturn = true;
                        break;
                    }
                }
                if(rturn){
                    continue;
                }

                System.out.println("Order ID " + generateOrderID(tempNamaRestoran, tanggalOrder,noTelepon) + " diterima!"); //Output apabila ketentuan input semua nya terpenuhi
                System.out.println("--------------------------------------------");

            }
            else if(pilihMenu==2){
                String lokasi = "";
                System.out.printf("Order ID: ");
                String orderID = input.nextLine();
                
                 //Validasi input untuk order ID
                while(orderID.length()!=16){
                    System.out.println("Order ID minimal 16 karakter\n");
                    System.out.printf("Order ID: ");
                    orderID = input.nextLine();
                }

                String valOrder = orderID.substring(0, 14);
                int totalCheckSum1val = 0;
                int totalCheckSum2val = 0;
                for(int s = 0; s<valOrder.length();s++){
                    if(s%2==0){
                        totalCheckSum1val += convertCTV(valOrder.charAt(s)); //Membuat checksum index genap
                    }
                    else{
                        totalCheckSum2val += convertCTV(valOrder.charAt(s)); //Membuat checksum index ganjil
                    }
                }
        
                int checkSum1 = totalCheckSum1val%36;
                int checkSum2 = totalCheckSum2val%36;
        
                char check1 = convertVTC(checkSum1);
                char check2 = convertVTC(checkSum2);
                String  newValid = "";
                newValid += valOrder+check1+check2;

                while(!newValid.equals(orderID)){
                    System.out.println("Silahkan masukkan Order ID yang valid!\n");
                    System.out.printf("Order ID: ");
                    orderID = input.nextLine();
                }

                if(newValid.equals(orderID)){ //Melakukan validasi apakah input sudah ada di array list order ID atau belum
                    System.out.printf("Lokasi Pengiriman: ");
                    lokasi = input.nextLine().toUpperCase();
                    boolean validasi = false;
                    if(lokasi.equals("P")|lokasi.equals("U")|lokasi.equals("T")|lokasi.equals("S")|lokasi.equals("B")){
                        validasi = false;
                    }
                    else{
                        validasi = true;
                    }
                    while(validasi){ //validasi lokasi
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!\n");
                        System.out.printf("Order ID: ");
                        orderID = input.nextLine();
                        System.out.printf("Lokasi Pengiriman: ");
                        lokasi = input.nextLine().toUpperCase();
                        if(lokasi.equals("P")|lokasi.equals("U")|lokasi.equals("T")|lokasi.equals("S")|lokasi.equals("B")){
                            validasi = false;
                        }
                        else{
                            validasi = true;
                        }
                    }

                    System.out.printf("\n"+generateBill(orderID, lokasi));
                    System.out.println("--------------------------------------------");
                }
            }

            else if(pilihMenu==3){ //Exit apabila memasukkan input 3
                System.out.printf("Terima kasih telah menggunakan DepeFood!");
                exitMenu=false;
            }
        }
    } 
}
