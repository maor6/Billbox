package DataStructures;

import java.util.Date;
/**
 * This class represent DataStructures.replacement DataStructures.document.
 */
public class Replacement extends Refund{

    private String new_item;             //TODO change it to item data type.

    public Replacement(String new_item,Date original_deal_date, String business, String customer, Date date, double total_price, double left_over, int total_items, int four_digits) {
        super(original_deal_date, business, customer, date, total_price, left_over, total_items, four_digits);
        this.new_item = new_item;
    }

    public Replacement(String new_item, Date original_deal_date, Document document, double total_price, double left_over, int total_items, int four_digits) {
        super(original_deal_date, document, total_price, left_over, total_items, four_digits);
        this.new_item = new_item;
    }

    public String getNew_item() {
        return new_item;
    }

    public void setNew_item(String new_item) {
        this.new_item = "" + new_item;
    }
}
