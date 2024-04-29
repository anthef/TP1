package assignments.assignment2;
//Class menu

public class Menu {
    private String namaMakanan; 
    private double harga;
    
    //Constructor
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    //Getter
    public String getNamaMakanan(){
        return namaMakanan;
    }
    public double getHarga(){
        return harga;
    }
    //Setter
    public void setNamaMakanan(String namaMakanan){
        this.namaMakanan = namaMakanan;
    }
    public void setHarga(Double harga){
        this.harga = harga;
    }
}
