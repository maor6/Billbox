package com.example.myapplication;

import java.util.*;

/**
 * This class represent a standard requirements in any Billbox com.example.myapplication.document.
 */
public abstract class document {

    private String business;
    private String customer;
    private Date date;

    public document(String business, String customer, Date date)
    {
        this.business = "" + business;
        this.customer = "" + customer;
        this.date = date;
    }

    /**
     * Copy constructor
     * @param document
     */
    public document(document document)
    {
       this.date = document.date;
       this.business = "" + document.business;
       this.customer = "" + document.customer;
    }

    public document() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
/*
    public String toString(){
        return "This com.example.myapplication.document is between "+this.business+" and "+this.customer+" at "+this.date);
    }*/
}



