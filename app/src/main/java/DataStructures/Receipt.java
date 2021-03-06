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
    private String notes;
    public Boolean isManual;
    private String imageID;
    public static String id;

    /**
     * Constructor
     * @param businessName
     * @param date purchase date
     * @param total_price total deal cost as double
     * @param businessAddress
     * @param businessPhone
     * @param notes
     * @param id
     */
  public Receipt(String businessName, String date, double total_price, String businessAddress, String businessPhone, String notes, String id) {
      super(businessName, businessAddress, businessPhone, date);
      this.total_price = total_price;
      this.notes = notes;
      this.id = id;
      this.isManual = false;
  }

  public Receipt(String businessName, String date, double total_price, String imageID){
        super(businessName, date);
        this.total_price = total_price;
        this.imageID = imageID;
        this.isManual = true;
  }

  public Receipt() {super();}

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

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

}


