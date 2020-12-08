package DataStructures;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
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

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(barCode);
        parcel.writeDouble(price);
        parcel.writeInt(amount);
    }
}