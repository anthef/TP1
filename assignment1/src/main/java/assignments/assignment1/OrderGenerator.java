package assignments.assignment1;  //File termasuk ke package assignment

import java.time.LocalDate; //import built in library untuk input tanggal
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in); //Untuk input data
    private static ArrayList<String> orderIDs = new ArrayList<>(); //Array list of order ID
    private static HashMap<String, String> ongkir = new HashMap<>(); //Hash Map untuk lokasi dan biaya pengirimannya

    public static void showMenu(){  //function show menu
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.err.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
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
        output += String.valueOf(digitNoTelp);

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

        orderIDs.add(output);
        return output;
    }

    public static String generateBill(String OrderID, String lokasi){
        ongkir.put("P", "10.000"); //Memasukkan value pada hashmap
        ongkir.put("U", "20.000");
        ongkir.put("T", "35.000");
        ongkir.put("S", "40.000");
        ongkir.put("B", "60.000");

        String biayaOngkosKirim = ongkir.get(lokasi.toUpperCase()); //mendapatkan harga pengiriman berdasarkan key hashmap
        String tanggalOrder = "";
        String lokasiPrint = lokasi.toUpperCase();

        if (biayaOngkosKirim == null) {
            return "Lokasi pengiriman tidak valid.";
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

    public static void main(String[] args) {
        Boolean exitMenu = true;

        while (exitMenu) {
            showMenu();
            System.out.printf("Pilihan Menu: ");  //Melakukan input pilihan menu
            int pilihMenu = input.nextInt();
            input.nextLine();
            Boolean rturn = false;

            if(pilihMenu==1){
                System.out.printf("Nama Restoran: ");
                String tempNamaRestoran = input.nextLine().trim(); //Menghilangkan spasi di bagian depan/belakang

                if(tempNamaRestoran.length()<4){ //Melakukan validasi input
                    System.out.printf("Masukkan nama dengan panjang lebih dari 3 karakter\n\n");
                    continue;
                }
                String namaRestoran = tempNamaRestoran.substring(0, 4);

                System.out.printf("Tanggal Pemesanan: "); //Melakukan input tanggal pemesanan
                String tanggalOrder = input.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate tanggal = LocalDate.parse(tanggalOrder, formatter);
                } catch (Exception e) {
                    System.out.printf("Format tanggal tidak valid. Masukkan tanggal dalam format DD/MM/YYYY.\n\n"); //Validasi input
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

                System.out.println("Order ID " + generateOrderID(namaRestoran, tanggalOrder,noTelepon) + " diterima!"); //Output apabila ketentuan input semua nya terpenuhi

            }
            else if(pilihMenu==2){
                System.out.printf("Order ID: ");
                String orderID = input.nextLine();
                if(orderIDs.contains(orderID)){ //Melakukan validasi apakah input sudah ada di array list order ID atau belum
                    System.out.printf("Lokasi Pengiriman: ");
                    String lokasi = input.nextLine().toUpperCase();
                    if(ongkir.containsKey(lokasi)){ //Melakukan pengecekan untuk lokasi  
                        System.out.println(generateBill(orderID, lokasi));
                    }
                    while(!ongkir.containsKey(lokasi)){
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                        System.out.printf("Lokasi Pengiriman: ");
                        lokasi = input.nextLine().toUpperCase();
                    }

                }
                //Validasi input untuk order ID
                while(orderID.length()!=16){
                    System.out.println("Order ID minimal 16 karakter");
                    System.out.printf("Order ID: ");
                    orderID = input.nextLine();
                }
                
                while(!orderIDs.contains(orderID)){
                    System.out.println("Silahkan masukkan Order ID yang valid!");
                    System.out.printf("Order ID: ");
                    orderID = input.nextLine();
                }
            }

            else if(pilihMenu==3){ //Exit apabila memasukkan input 3
                System.out.printf("Terima kasih telah menggunakan DepeFood!");
                exitMenu=false;
            }
        }
    } 
}
