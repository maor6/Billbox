package com.example.myapplication.SendNotificationPack;

public class NotificationSender {
    public Data data;
    public String sendTo;

    public NotificationSender(Data data, String sendTo) {
        this.data = data;
        this.sendTo = sendTo;
    }

    public NotificationSender() {
    }
}
