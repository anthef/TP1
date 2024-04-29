package assignments.assignment3.payment;
public class Menu {
    //Data Field
    private String namaMakanan;
    private double harga; 

    //Constructor
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    //Getter
    public double getHarga() {
        return harga;
    }
    public String getNamaMakanan() {
        return namaMakanan;
    }
}