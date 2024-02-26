package assignments.assignment1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<String> orderIDs = new ArrayList<>();
    private static HashMap<String, String> ongkir = new HashMap<>();

    public static void showMenu(){
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

    public static int convertCharToValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 10;
        } else {
            return -1;
        }
    }

    public static char convertValueToChar(int value) {
        if (value >= 0 && value <= 9) {
            return (char) (value + '0');
        } else if (value >= 10 && value <= 35) {
            return (char) (value - 10 + 'A');
        } else {
            return '?';
        }
    }
    

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String namaRes = namaRestoran.substring(0,4);
        String output = "";
        output+= namaRes.toUpperCase();

        String [] tanggalSplit = tanggalOrder.split("/");
        for(String tggl : tanggalSplit){
            output += tggl;
        }
        
        int totalDigitNoTelp = 0;
        for(int k = 0; k<noTelepon.length();k++){
            totalDigitNoTelp += Character.getNumericValue(noTelepon.charAt(k));
        }
        int digitNoTelp = totalDigitNoTelp%100;
        output += String.valueOf(digitNoTelp);

        int totalCheckSum1 = 0;
        int totalCheckSum2 = 0;
        for(int l = 0; l<output.length();l++){
            if(l%2==0){
                totalCheckSum1 += convertCharToValue(output.charAt(l));
            }
            else{
                totalCheckSum2 += convertCharToValue(output.charAt(l));
            }
        }

        int checkSum1 = totalCheckSum1%36;
        int checkSum2 = totalCheckSum2%36;

        char check1 = convertValueToChar(checkSum1);
        char check2 = convertValueToChar(checkSum2);

        output += check1;
        output+= check2;

        orderIDs.add(output);
        return output;
    }

    public static String generateBill(String OrderID, String lokasi){
        ongkir.put("P", "10.000");
        ongkir.put("U", "20.000");
        ongkir.put("T", "35.000");
        ongkir.put("S", "40.000");
        ongkir.put("B", "60.000");
        String biayaOngkosKirim = ongkir.get(lokasi.toUpperCase());
        String tanggalOrder = "";
        String lokasiPrint = lokasi.toUpperCase();

        if (biayaOngkosKirim == null) {
            return "Lokasi pengiriman tidak valid.";
        }
        for(int s = 4; s<12;s++){
            if(s==5 || s==7){
                tanggalOrder+=OrderID.charAt(s) + "/";
            }
            else {
                tanggalOrder+=OrderID.charAt(s);
            }
        }
        String bill = "Bill:\n" +
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
            System.out.printf("Pilihan Menu: ");   
            int pilihMenu = input.nextInt();
            input.nextLine();

            if(pilihMenu==1){
                System.out.printf("Nama Restoran: ");
                String tempNamaRestoran = input.nextLine().trim();

                if(tempNamaRestoran.length()<4){
                    System.out.printf("Masukkan nama dengan panjang lebih dari 3 karakter\n\n");
                    continue;
                }
                String namaRestoran = tempNamaRestoran.substring(0, 4);
                if(namaRestoran.length()==4){
                    for(int j=0; j<namaRestoran.length();j++){
                        if(!Character.isAlphabetic(namaRestoran.charAt(j))){
                            System.out.printf("Masukkan nama dengan panjang lebih dari 2 karakter\n\n");
                            break;
                        }
                    }
                }


                System.out.printf("Tanggal Pemesanan: ");
                String tanggalOrder = input.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate tanggal = LocalDate.parse(tanggalOrder, formatter);
                } catch (Exception e) {
                    System.out.printf("Format tanggal tidak valid. Masukkan tanggal dalam format DD/MM/YYYY.\n\n");
                    continue;
                }

                System.out.printf("No. Telpon: ");
                String tempNomorTelepon = input.nextLine();
                String noTelepon = "";
                for(int i=0; i<tempNomorTelepon.length();i++){
                    if(Character.isDigit(tempNomorTelepon.charAt(i))){
                        noTelepon+=tempNomorTelepon.charAt(i);
                    }
                    else{
                        System.out.printf("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.\n\n");
                        break;
                    }
                }

                System.out.println("Order ID " + generateOrderID(namaRestoran, tanggalOrder,noTelepon) + " diterima!");

            }
            else if(pilihMenu==2){
                System.out.printf("Order ID: ");
                String orderID = input.nextLine();
                if(orderIDs.contains(orderID)){
                    System.out.printf("Lokasi Pengiriman: ");
                    String lokasi = input.nextLine().toUpperCase();
                    if(ongkir.containsKey(lokasi)){
                        System.out.println(generateBill(orderID, lokasi));
                    }
                    while(!ongkir.containsKey(lokasi)){
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                        System.out.printf("Lokasi Pengiriman: ");
                        lokasi = input.nextLine().toUpperCase();
                    }

                }
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

            else if(pilihMenu==3){
                System.out.printf("Terima kasih telah menggunakan DepeFood!");
                exitMenu=false;
            }
        }
    } 
}
