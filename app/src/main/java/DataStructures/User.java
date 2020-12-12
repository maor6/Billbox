package DataStructures;

/**
 * This class represent a user in the Billbox app
 */
public abstract class User {

    private String name;
    private String last_name;
    private String email;
    private String password;
    private String phoneNumber;

    /**
     * @param name the name of the user.
     * @param email the email of the user.
     * @param password the password chosen by the user.
     */
    public User(String name, String last_name, String email, String password, String phoneNumber){
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(){}

    /*---------------Getters and Setters---------------*/

    public String getName(){
        return this.name;
    }

    public  String getLast_name() { return this.last_name;}

    public String getEmail(){
        return this.email;
    }

    public  String getPassword(){
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() { return phoneNumber;}
}

