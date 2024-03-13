package main.java.assignments.assignment2;

public class Order {
    private String orderID;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private ArrayList<Menu> items = new ArrayList<Menu>();
    private Boolean orderFinished;

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items){
        this.orderID = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkosKirim = ongkir;
        this.restaurant = resto;
        for(Menu itm : items){
            this.items.add(itm);
        }
    }

    public String getOrderID(){
        return orderID;
    }
    public String 
}
