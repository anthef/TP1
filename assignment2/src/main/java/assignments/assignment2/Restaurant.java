package assignments.assignment2;
import java.util.ArrayList;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

public class Restaurant {
    //Atribut
    private String nama;
    private ArrayList<Menu> menu = new ArrayList<Menu>();

    //Constructor
    public Restaurant(String nama){
        this.nama = nama;
    }

    //Getter
    public String getNama(){
        return nama;
    }

    public ArrayList<Menu> getMenuList() {
        return this.menu;
    }

    //Setter
    public void setNama(String nama){
        this.nama = nama;
    }

    public void addMenu(Menu newMenu){
        menu.add(newMenu);
    }
    
}
