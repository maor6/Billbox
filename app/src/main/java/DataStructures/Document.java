package DataStructures;

import java.io.Serializable;

/**
 * This class represent a standard requirements in any Billbox DataStructures.document.
 */
public abstract class Document implements Serializable {

    private String businessName;
    private String date;
    private String businessAddress;
    private String businessPhone;

    public Document(String businessName, String businessAddress, String businessPhone, String date)
    {
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessPhone = businessPhone;
        this.date = date;
    }

    public Document() {}

    //
//    /**
//     * Copy constructor
//     * @param document
//     */
//    public Document(Document document)
//    {
//       this.date = document.date;
//       this.business = document.business;
//    }

    /*--------------Getters and Setters--------------*/

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDate() { return this.date; }

    public void setDate(String date) {
        this.date = date;
    }
}



