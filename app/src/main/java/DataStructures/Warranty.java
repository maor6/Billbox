package DataStructures;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent DataStructures.warranty DataStructures.document.
 */
public class Warranty extends Document implements Serializable {

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

    public Warranty(){}

    /*---------------Getters and Setters---------------*/

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
