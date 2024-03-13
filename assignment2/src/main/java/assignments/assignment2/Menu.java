package assignments.assignment2;

public class Menu {
    private String namaMakanan; 
    private double harga;

    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    public String getNamaMakanan(){
        return namaMakanan;
    }
    public double getHarga(){
        return harga;
    }
    public void setNamaMakanan(String namaMakanan){
        this.namaMakanan = namaMakanan;
    }
    public void setHarga(Double harga){
        this.harga = harga;
    }
}
