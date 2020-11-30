package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Activities.NFCBussinesActivity;
import Activities.SearchProductActivity;
import DataStructures.Product;
import DataStructures.receipt;


public class CreateBillActivity extends AppCompatActivity {

    CardView search;
    ArrayList<Product> products;
    Button finishBt;
    receipt receipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        search = (CardView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateBillActivity.this, SearchProductActivity.class);
                startActivity(intent);
            }
        });
        finishBt = (Button) findViewById(R.id.finishbt);
        finishBt.setOnClickListener(new View.OnClickListener() { // on "סיים" clicked
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateBillActivity.this, NFCBussinesActivity.class);
                DataStructures.receipt reciept = new receipt();
                //TODO change the reciept fields by the textViews
                Log.d("mytag", "i got here");
                intent.putExtra("receipt", reciept.toString());
                startActivity(intent);
            }
        });
    }
}