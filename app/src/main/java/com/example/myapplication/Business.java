package com.example.myapplication;

import java.util.LinkedList;
import java.util.ArrayList;

/**
 * this class represent a business user in Billbox app
 */
public class Business extends User {

    private String business_name;
    private String address;
    private LinkedList<String> documents; //TODO change it to firebase.
    private ArrayList<String> items; //TODO change it to firebase.

    /**
     * Constructor
     * @param name the name of the business owner.
     * @param email the email of the business.
     * @param password the password chosen by the user.
     * @param business_name the business name.
     * @param address the address of the business.
     */
    public Business(String name, String last_name, String email, String password,  String business_name, String address, String phoneNumber){
        super(name, last_name, email, password, phoneNumber);
        this.business_name = business_name;
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public String getBusiness_name(){
        return this.business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
