package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchProductActivity extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    ArrayList<Product> products;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Stock").child(firebaseAuth.getUid());
        recyclerView = findViewById(R.id.recycler);
        searchView = findViewById(R.id.searchView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        products = new ArrayList<Product>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            products.add(ds.getValue(Product.class));
                        }
                        ProductAdapter productAdapter = new ProductAdapter(products);
                        recyclerView.setAdapter(productAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SearchProductActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchView != null){
            searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            }));
        }
    }

    private void search(String s){
        ArrayList<Product> myProducts = new ArrayList<Product>();
        for(Product p : products){
            if(p.getName().toLowerCase().contains(s.toLowerCase())){
                myProducts.add(p);
            }
        }
        ProductAdapter productAdapter = new ProductAdapter(myProducts);
        recyclerView.setAdapter(productAdapter);
    }
}