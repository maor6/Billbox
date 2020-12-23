package com.example.myapplication.SendNotificationPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * API that sent HTTP POST
 * @author Maor
 */

public interface ApiInterface {
    @Headers(
                {
                    "Authorization: key=" + "SERVER_KEY",
                    "Content-Type:application/json"
                }
            )
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body RootModel root);
}