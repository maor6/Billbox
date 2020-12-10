package DataStructures;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class is Billbox DataStructures.Receipt format data type.
 */
public class Receipt implements Serializable {//extends document{ TODO why the extends coolapse the app???

    private ArrayList<Product> items;
    private double total_price;
    private double left_over;
    private int total_items;
    private int four_digits;
    private enum paying_method {CREDIT ,CASH ,TRANSFER ,CHEQUE};
    private String business;
    private String date;
    private String businessAddress;
    private String businessPhone;
    private String notes;
    private String id;

    /**
     * Constructor
     * @param business name as string
     * @param date purchase date
     * @param total_price total deal cost as double
     * @param left_over left over in double in case of cash.
     * @param total_items number of items that was purchased
     * @param four_digits last 4 digits of credit card in case of credit.
     */
    //TODO Insert paying method to constructor as enum.
  public Receipt(String business, String date, double total_price, double left_over, int total_items, int four_digits) {
      //super(business, customer, date);
      this.four_digits = four_digits;
      this.total_items = total_items;
      this.left_over = left_over;
      this.business = business;
      this.date = date;
      this.total_price = total_price;
  }

    /**
     * Constructor
     * @param business
     * @param date
     * @param total_price
     * @param total_items
     * @param businessAddress
     * @param businessPhone
     * @param notes
     * @param id
     */
  public Receipt(String business, String date, double total_price, int total_items, String businessAddress, String businessPhone, String notes, String id) {
      DecimalFormat df = new DecimalFormat("#.##");
      this.business = business;
      this.date = date;
      this.total_price = total_price;
      this.total_items = total_items;
      this.businessAddress = businessAddress;
      this.businessPhone = businessPhone;
      this.notes = notes;
      this.id = id;
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




  /*---------------Getters and Setters---------------*/

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

    public void setBusiness(String business) {
        this.business = business;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<Product> getItems() { return items; }

    public void setItems(ArrayList<Product> items) { this.items = items; }

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


