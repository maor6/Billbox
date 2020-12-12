package DataStructures;

import java.util.LinkedList;
import java.util.ArrayList;

import DataStructures.User;

/**
 * this class represent a business user in Billbox app
 */
public class Business extends User {

    private String business_name;
    private String address;
    private String billNotes;
    private ArrayList<String> items;
    private LinkedList<String> documents;


    /**
     * Constructor
     * @param name the name of the business owner.
     * @param email the email of the business.
     * @param password the password chosen by the user.
     * @param business_name the business name.
     * @param address the address of the business.
     */
    public Business(String name, String last_name, String email, String password, String business_name, String address, String phoneNumber){
        super(name, last_name, email, password, phoneNumber);
        this.business_name = business_name;
        this.address = address;
        this.billNotes= " ";
    }

    public Business() {
        super();
    }

    /**
     * option to the user to add a notes to documents
     * @param notes
     */
    public void addNotes(String notes){
       this.billNotes = notes;
    }

    /*--------------Getters and Setters--------------*/

    public String getAddress(){
        return this.address;
    }

    public LinkedList<String> getDocuments() {
        return documents;
    }

    public void setDocuments(LinkedList<String> documents) {
        this.documents = documents;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public String getBillNotes() {
        return billNotes;
    }

    public void setBillNotes(String billNotes) {
        this.billNotes = billNotes;
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
