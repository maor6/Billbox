package DataStructures;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is Billbox Receipt format data type.
 */
public class Receipt extends Document implements Serializable {

    private ArrayList<Product> items;
    private double total_price;
    private double left_over;
    private int four_digits;
    private enum paying_method {CREDIT ,CASH ,TRANSFER ,CHEQUE};
    private String businessAddress;
    private String businessPhone;
    private String notes;
    public static String id;

    /**
     * Constructor
     * @param businessName name as string
     * @param date purchase date
     * @param total_price total deal cost as double
     * @param left_over left over in double in case of cash.
     * @param four_digits last 4 digits of credit card in case of credit.
     */
    //TODO Insert paying method to constructor as enum.
  public Receipt(String businessName, String date, double total_price, double left_over, int four_digits) {
      super(businessName, date);
      this.four_digits = four_digits;
      this.left_over = left_over;
      this.total_price = total_price;
  }

  public Receipt() {super();}

    /**
     * Constructor
     * @param businessName
     * @param date
     * @param total_price
     * @param businessAddress
     * @param businessPhone
     * @param notes
     * @param id
     */
  public Receipt(String businessName, String date, double total_price, String businessAddress, String businessPhone, String notes, String id) {
      super(businessName, date);
      this.total_price = total_price;
      this.businessAddress = businessAddress;
      this.businessPhone = businessPhone;
      this.notes = notes;
      this.id = id;
  }

  /*---------------Getters and Setters---------------*/

    public double getTotal_price() {
        return total_price;
    }

    public double getLeft_over() {
        return left_over;
    }

    public int getFour_digits() {
        return four_digits;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) { this.businessAddress = businessAddress; }

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

    public void setFour_digits(int four_digits) {
        this.four_digits = four_digits;
    }

    public ArrayList<Product> getItems() { return items; }

    public void setItems(ArrayList<Product> items) { this.items = items; }

}


