package assignments.assignment2;
import java.util.ArrayList;

public class Order {
    private String orderID;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private ArrayList<Menu> items = new ArrayList<Menu>();
    private Boolean orderFinished = false;

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
    public String getTanggalPemesanan(){
        return tanggalPemesanan;
    }
    public String getRestaurantName() {
        return restaurant.getNama();
    }
    public Boolean getOrderFinished(){
        return orderFinished;
    }
    public void setOrderFinished(Boolean finished){
        this.orderFinished=finished;
    }
    public ArrayList<Menu> getItemsList() {
        return this.items;
    }
    
    
}
