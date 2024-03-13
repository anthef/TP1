package main.java.assignments.assignment2;

public class User {
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    static String role;
    private ArrayList<Order> orderHistory = new ArrayList<Order>();
    
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
    }

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

    
}
