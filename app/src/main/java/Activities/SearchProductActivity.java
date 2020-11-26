package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import DataStructures.Product;
import com.example.myapplication.ProductAdapter;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchProductActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    ProductAdapter productAdapter;
    ArrayList<Product> products;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Stock").child(firebaseAuth.getUid());
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.searchView);

        productAdapter = new ProductAdapter(this, products);
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
//                //TODO back to CreatBIillActivity with the product
//                startActivity(new Intent(SearchProductActivity.this, CreateBillActivity.class));
            }
        });
        recyclerView.setAdapter(productAdapter);


        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    products.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Log.d("mytag", dataSnapshot.getValue().toString());
                        Product product = dataSnapshot1.getValue(Product.class);
                        products.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(SearchProductActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(ref != null){
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    products.clear();
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        Product product = dataSnapshot1.getValue(Product.class);
//                        products.add(product);
//                    }
//                    productAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
////                    Toast.makeText(SearchProductActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        if(searchView != null){
//            searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String s) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String s) {
//                    search(s);
//                    return true;
//                }
//            }));
//        }
//    }

//    private void search(String s){
//        ArrayList<Product> myProducts = new ArrayList<Product>();
//        for(Product p : products){
//            if(p.getName().toLowerCase().contains(s.toLowerCase())){
//                myProducts.add(p);
//            }
//        }
//        productAdapter.notifyDataSetChanged();
//        recyclerView.setAdapter(productAdapter);
//    }
}