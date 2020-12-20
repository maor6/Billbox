package com.example.myapplication.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAL5VJ9i8:APA91bFpD_7O9GGkKHUO9qGrC4ZbZdp5n_vrScQq5ZYTL6IXj_HzT9Lsp5aRtw1auBIJwlk0dn-G-wBWQmhi-zQXkJw4vR0Z0FpUlVwOptLX3K_ZtlB3cU1IDC_hYzOOFc9q-lyA92cX"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

