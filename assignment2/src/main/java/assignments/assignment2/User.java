package assignments.assignment2;
import java.util.ArrayList;
public class User {
    //Atribut
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    String role;
    private ArrayList<Order> orderHistory = new ArrayList<Order>();
    
    //Constructor
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
    }

    //Getter
    public String getNama(){
        return nama;
    }
    public String getNomorTelepon(){
        return nomorTelepon;
    }
    public String getEmail(){
        return email;
    }
    public String getLokasi(){
        return lokasi;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
    //Setter
    public void addOrderHistory(Order newOrder){
        orderHistory.add(newOrder);
    }

    //Ongkir method
    public int getOngkir(String lokasi){
        int biayaOngkosKirim = 0;
        switch(lokasi){
            case "P" :
                biayaOngkosKirim = 10000;
                break;
            case "U" :
                biayaOngkosKirim = 20000;
                break;
            case "T" :
                biayaOngkosKirim = 35000;
                break;
            case "S" :
                biayaOngkosKirim = 40000;
                break;
            case "B" :
                biayaOngkosKirim = 60000;
                break;
        }
        return biayaOngkosKirim;
    }

    
}
