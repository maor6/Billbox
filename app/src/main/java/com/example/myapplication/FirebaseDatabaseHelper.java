package com.example.myapplication;

/*
    this class manage our database
    read, write and create account
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Activities.Business_HomeActivity;
import Activities.Customer_HomeActivity;
import DataStructures.Receipt;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private static FirebaseAuth mAuth;
    private static DatabaseReference mReferenceUser;
    private DatabaseReference mReferenceDoc;
    private List<Receipt> receipts = new ArrayList<>();
    Context context;
    static Intent  intent;

    public interface DataStatus {
        void DataIsLoaded(List<Receipt> receipt);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper(Context context, FirebaseAuth firebaseAuth ) {
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mAuth = firebaseAuth;
        this.mReferenceDoc = mDatabase.getReference("Documents");
        this.context = context;
    }

    public void readReciepts(final DataStatus dataStatus) {
        mReferenceDoc.child("Receipt").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receipts.clear();
                for (DataSnapshot key : dataSnapshot.getChildren()) { //TODO fix the null in the list
                    Receipt doc = key.getValue(Receipt.class);
                    receipts.add(doc);
                }
                dataStatus.DataIsLoaded(receipts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Intent logIN(String email, String pass, Context context) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("mytag", mAuth.getUid().toString());
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // updateUI(user);

                            mReferenceUser = FirebaseDatabase.getInstance().getReference();
                            mReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.child("Users").child("Bussines").hasChild(mAuth.getUid())) {
                                        intent = new Intent(context, Business_HomeActivity.class);
                                    }
                                    else {
                                        intent = new Intent(context, Customer_HomeActivity.class);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("NotGood", "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });

        return intent;
    }
}
