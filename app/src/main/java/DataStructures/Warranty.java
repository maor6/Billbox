package DataStructures;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represent DataStructures.warranty DataStructures.document.
 */
public class Warranty extends Document {


    private String item;  //TODO change it to item data type.
    private Date expiration;
    private ArrayList<String> rules;

    /**
     * Constructor
     * @param business name as string
     * @param customer name as string
     * @param date purchase date
     * @param item name as string
     * @param expiration date which the item loses its DataStructures.warranty
     * @param rules paragraph which set the DataStructures.warranty rules as array list of string.
     */
    public Warranty(String business, String customer, Date date, String item, Date expiration, ArrayList<String> rules)
    {
        super(business, customer, date);
        this.expiration = expiration;
        this.item = item;
        this.rules = rules;
    }

    public String getItem() {
        return item;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
