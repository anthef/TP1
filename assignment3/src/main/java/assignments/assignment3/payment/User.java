package assignments.assignment3.payment;
import java.util.ArrayList;

import assignments.assignment3.payment.DepeFoodPaymentSystem;

public class User {
    //Datafield
    private String nama;
    private String nomorTelepon;
    private String email;
    private ArrayList<Order> orderHistory;
    public String role;
    private DepeFoodPaymentSystem payment;
    private long saldo;
    private String lokasi;

    //Constructor
    public User(String nama, String nomorTelepon, String email, String lokasi, String role, DepeFoodPaymentSystem payment, long saldo){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.saldo = saldo;
        this.payment = payment;
        orderHistory = new ArrayList<>();
    }
    //Getter Setter
    public String getEmail() {
        return email;
    }
    public String getNama() {
        return nama;
    }
    public String getLokasi() {
        return lokasi;
    }
    public String getNomorTelepon() {
        return nomorTelepon;
    }
    public void addOrderHistory(Order order){
        orderHistory.add(order);
    }
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
    public long getSaldo(){
        return saldo;
    }
    public void setSaldo(long saldo){
        this.saldo = saldo;
    }

    public DepeFoodPaymentSystem getPayment(){
        return payment;
    }

    public void setPayment(DepeFoodPaymentSystem payment){
        this.payment = payment;
    }

    public boolean isOrderBelongsToUser(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return String.format("User dengan nama %s dan nomor telepon %s", nama, nomorTelepon);
    }

    //Get ongkir
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
