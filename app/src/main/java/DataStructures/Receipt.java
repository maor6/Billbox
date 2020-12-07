package DataStructures;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is Billbox DataStructures.Receipt format data type.
 */
public class Receipt implements Parcelable {//extends document{ TODO why the extends coolapse the app???

    private ArrayList<String> items;
    private double total_price;
    private double left_over;
    private int total_items;
    private int four_digits;
     private enum paying_method {CREDIT ,CASH ,TRANSFER ,CHEQUE};
    private String business;
    private String date;

    /**
     * Constructor
     * @param business name as string
     * @param customer name as string
     * @param date purchase date
     * @param total_price total deal cost as double
     * @param left_over left over in double in case of cash.
     * @param total_items number of items that was purchased
     * @param four_digits last 4 digits of credit card in case of credit.
     */
    //TODO Insert paying method to constructor as enum.
  public Receipt(String business, String customer, String date, double total_price, double left_over , int total_items, int four_digits)
  {
      //super(business, customer, date);
      this.four_digits = four_digits;
      this.total_items = total_items;
      this.left_over = left_over;
      this.business = business;
      this.date = date;
      this.total_price = total_price;
  }

  public Receipt(String business, String date, double total_price) {
      this.business = business;
      this.date = date;
      this.total_price = total_price;
  }


    public Receipt(String rec) { // constructor with String TODO set all the variables
        String[] s = rec.split(",");
        //this.items = s[0];
        this.total_price = Double.parseDouble(s[1]);
        this.business = s[5];
        this.date = s[6];
    }

//    /**
//     * Constructor with DataStructures.document
//     * @param document  basic DataStructures.document fields
//     * @param total_price total deal cost as double
//     * @param left_over left over in double in case of cash.
//     * @param total_items number of items that was purchased
//     * @param four_digits last 4 digits of credit card in case of credit
//     */


    protected Receipt(Parcel in) {
        items = in.createStringArrayList();
        total_price = in.readDouble();
        left_over = in.readDouble();
        total_items = in.readInt();
        four_digits = in.readInt();
        business = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(items);
        dest.writeDouble(total_price);
        dest.writeDouble(left_over);
        dest.writeInt(total_items);
        dest.writeInt(four_digits);
        dest.writeString(business);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Receipt> CREATOR = new Creator<Receipt>() {
        @Override
        public Receipt createFromParcel(Parcel in) {
            return new Receipt(in);
        }

        @Override
        public Receipt[] newArray(int size) {
            return new Receipt[size];
        }
    };




  /*
  ---------------Getters and Setters---------------
   */
    public String getBusiness() {
        return business;
    }

    public String getDate() {
        return date;
    }

    public Receipt() {
        super();
    }

    public double getTotal_price() {
        return total_price;
    }

    public double getLeft_over() {
        return left_over;
    }

    public int getTotal_items() {
        return total_items;
    }

    public int getFour_digits() {
        return four_digits;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setLeft_over(double left_over) {
        this.left_over = left_over;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    public void setFour_digits(int four_digits) {
        this.four_digits = four_digits;
    }

    @Override
    public String toString() {
        return "" +// items +
                "," + total_price +
                "," + left_over +
                "," + total_items +
                "," + four_digits +
                "," + business +
                "," + date;
    }
}


