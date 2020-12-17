package DataStructures;

import java.util.LinkedList;

import DataStructures.User;

/**
 * this class represent a customer user in Billbox app
 */
public class Customer extends User {

    private LinkedList<String> documents; //TODO change it to firebase.

    /**
     * Constructor
     * @param name the name of the customer.
     * @param email the email of the customer.
     * @param password the password chosen by the user.
     */
    public Customer( String name, String last_name, String email, String password, String phoneNumber){
        super(name, last_name, email, password, phoneNumber);
    }

    public Customer(){
        super();
    }

}
