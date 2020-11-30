package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.SearchView;
import com.example.myapplication.ProductAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import DataStructures.Product;

public class SearchProductActivity extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    ArrayList<Product> products;
    RecyclerView recyclerView;
    SearchView searchView;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Stock").child(firebaseAuth.getUid());
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<Product>();
        //searchView = findViewById(R.id.searchProduct); // here is the problem
        
        adapt();

//        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(int position) {
//
//            }
//        });
//        recyclerView.setAdapter(productAdapter);
//
//        if(ref != null) {
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.exists()){
//                        for(DataSnapshot ds : dataSnapshot.getChildren()){
//                            products.add(ds.getValue(Product.class));
//                        }
//                        productAdapter.notifyDataSetChanged();
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(SearchProductActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

    }

    private void adapt() {
        adapter = new ProductAdapter(this, products);
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product product = dataSnapshot1.getValue(Product.class);
                    products.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
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
//        ProductAdapter productAdapter = new ProductAdapter(this ,myProducts);
//        recyclerView.setAdapter(productAdapter);
//    }
}