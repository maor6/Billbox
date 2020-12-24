package DataStructures;

import java.io.Serializable;

/**
 * this class represent a product in business inventory
 */
public class Product implements Serializable {
    String name;
    int barCode;
    double price;
    int amount;

    public Product(){}

    public Product(String name, int barCode, double price, int amount){
        this.name = name;
        this.barCode = barCode;
        this.price = price;
        this.amount = amount;
    }

    public Product(String name, int barCode){
        this.name = name;
        this.barCode = barCode;
    }


    /*--------------Getters and Setters--------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}