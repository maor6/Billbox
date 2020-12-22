package com.example.myapplication.SendNotificationPack;

/**
 * The data we will sent with the message
 */

public class Data {

    private String businesssName;
    private String totalPay;

    public Data(String businesssName, String totalPay) {
        this.businesssName = businesssName;
        this.totalPay = totalPay;
    }

    public String getName() {
        return businesssName;
    }

    public void setName(String businesssName) {
        this.businesssName = businesssName;
    }

    public String getAge() {
        return totalPay;
    }

    public void setAge(String totalPay) {
        this.totalPay = totalPay;
    }
}