package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import DataStructures.receipt;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mReferenceUser;
    private DatabaseReference mReferenceDoc;
    private List<receipt> receipts = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<receipt> receipt);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.mReferenceDoc = mDatabase.getReference("Documents");
    }

    public void readReciepts(final DataStatus dataStatus) {
        mReferenceDoc.child("Receipt").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receipts.clear();
                for (DataSnapshot key : dataSnapshot.getChildren()) { //TODO fix the null in the list
                    receipt doc = key.getValue(receipt.class);
                    receipts.add(doc);
                }
                dataStatus.DataIsLoaded(receipts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

