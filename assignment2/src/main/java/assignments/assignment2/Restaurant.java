package main.java.assignments.assignment2;

public class Restaurant {
    private String nama;
    private ArrayList<Menu> menu = new ArrayList<Menu>();

    public Restaurant(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public void addMenu(Menu newMenu){
        menu.add(newMenu);
    }
    
    // TODO: tambahkan methods yang diperlukan untuk class ini
}
