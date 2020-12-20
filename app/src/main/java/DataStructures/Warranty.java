package DataStructures;

import java.util.ArrayList;

/**
 * This class represent DataStructures.warranty DataStructures.document.
 */
public class Warranty extends Document {

    private Product product;
    private String expiryDate;
    private String rules;

    /**
     * Constructor
     * @param businessName name as string
     * @param date purchase date
     * @param product the product with the warranty
     * @param expiryDate which the item loses its DataStructures.warranty
     * @param rules paragraph which set the DataStructures.warranty rules as array list of string.
     */
    public Warranty(String businessName, String businessAddress, String businessPhone, String date, Product product, String expiryDate, String rules)
    {
        super(businessName, businessAddress, businessPhone, date);
        this.product = product;
        this.expiryDate = expiryDate;
        this.rules = rules;
    }
    /*---------------Getters and Setters---------------*/

    public Product getItem() {
        return product;
    }

    public String getExpiration() {
        return expiryDate;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public void setExpiration(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
