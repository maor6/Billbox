package com.example.myapplication;

import java.util.Date;
/**
 * This class represent com.example.myapplication.refund com.example.myapplication.document.
 */
public class refund extends receipt {

   private Date original_deal_date;
   private enum reason{};              //TODO add reasons

    public refund(Date original_deal_date, String business, String customer, Date date, double total_price, double left_over, int total_items, int four_digits) {
        //super(business, customer, date, total_price, left_over, total_items, four_digits);
        this.original_deal_date = original_deal_date;
    }

    public refund(Date original_deal_date, document document, double total_price, double left_over, int total_items, int four_digits) {
        //super(document, total_price, left_over, total_items, four_digits);
        this.original_deal_date = original_deal_date;
    }

    public Date getOriginal_deal_date() {
        return original_deal_date;
    }

    public void setOriginal_deal_date(Date original_deal_date) {
        this.original_deal_date = original_deal_date;
    }
}
