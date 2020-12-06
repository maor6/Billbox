package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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
//    SearchView searchView;
    EditText searchView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Stock").child(firebaseAuth.getUid());
        recyclerView = findViewById(R.id.productsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<Product>();
        searchView = findViewById(R.id.search); // here is the problem
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        adapt();
    }

    private void filter(String s){
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product item : products){
            if(item.getName().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(item);
            }
        }
        productAdapter.filterList(filteredList);
    }

    /*
        this function get all the products from the database
        and put it to recycler view
    */
    private void adapt() {
        productAdapter = new ProductAdapter(this, products);
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(int position) {

            }
        });
        recyclerView.setAdapter(productAdapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product product = dataSnapshot1.getValue(Product.class);
                    products.add(product);
                }
                productAdapter.notifyDataSetChanged();
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