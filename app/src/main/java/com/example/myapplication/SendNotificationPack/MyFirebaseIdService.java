package com.example.myapplication.SendNotificationPack;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token)
    {
        super.onNewToken(token);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            updateToken(token);
        }
    }

    private void updateToken(String refreshToken){
        Token token1 = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).setValue(token1);
    }
}
