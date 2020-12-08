package DataStructures;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

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

    protected Product(Parcel in) {
        name = in.readString();
        barCode = in.readInt();
        price = in.readDouble();
        amount = in.readInt();
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