package DataStructures;

import java.util.ArrayList;

/**
 * This class represent DataStructures.warranty DataStructures.document.
 */
public class Warranty extends Document {


    private String item;  //TODO change it to item data type.
    private String expiration;
    private ArrayList<String> rules;

    /**
     * Constructor
     * @param businessName name as string
     * @param date purchase date
     * @param item name as string
     * @param expiration date which the item loses its DataStructures.warranty
     * @param rules paragraph which set the DataStructures.warranty rules as array list of string.
     */
    public Warranty(String businessName, String date, String item, String expiration, ArrayList<String> rules)
    {
        super(businessName, date);
        this.expiration = expiration;
        this.item = item;
        this.rules = rules;
    }
    /*---------------Getters and Setters---------------*/

    public String getItem() {
        return item;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
