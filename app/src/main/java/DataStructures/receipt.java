package DataStructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is Billbox DataStructures.receipt format data type.
 */
public class receipt {//extends document{ TODO why the extends coolapse the app???
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
  public receipt(String business, String customer, String date, double total_price, double left_over , int total_items, int four_digits)
  {
      //super(business, customer, date);
      this.four_digits = four_digits;
      this.total_items = total_items;
      this.left_over = left_over;
      this.business = business;
      this.date = date;
      this.total_price = total_price;
  }

  public receipt(String business, String date, double total_price) {
      this.business = business;
      this.date = date;
      this.total_price = total_price;
  }


    public receipt(String rec) { // constructor with String TODO set all the variables
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


    public String getBusiness() {
        return business;
    }


    public String getDate() {
        return date;
    }

    public receipt() {
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
        return "" + items +
                "," + total_price +
                "," + left_over +
                "," + total_items +
                "," + four_digits +
                "," + business +
                "," + date;
    }
}


