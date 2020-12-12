package DataStructures;

import java.util.Date;
/**
 * This class represent DataStructures.refund DataStructures.document.
 */
public class Refund extends Receipt {

   private Date original_deal_date;
   private enum reason{};              //TODO add reasons

    /**
     * Constructor
     * @param original_deal_date
     * @param business
     * @param customer
     * @param date
     * @param total_price
     * @param left_over
     * @param total_items
     * @param four_digits
     */
    public Refund(Date original_deal_date, String business, String customer, Date date, double total_price, double left_over, int total_items, int four_digits) {
        //super(business, customer, date, total_price, left_over, total_items, four_digits);
        this.original_deal_date = original_deal_date;
    }

    public Refund(Date original_deal_date, Document document, double total_price, double left_over, int total_items, int four_digits) {
        //super(document, total_price, left_over, total_items, four_digits);
        this.original_deal_date = original_deal_date;
    }

    /*---------------Getters and Setters---------------*/

    public Date getOriginal_deal_date() {
        return original_deal_date;
    }

    public void setOriginal_deal_date(Date original_deal_date) {
        this.original_deal_date = original_deal_date;
    }
}
