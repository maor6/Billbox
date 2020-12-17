package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import java.util.Objects;

import DataStructures.Product;

/**
 * This is an activity class to search and filter products from the inventory list
 */
public class SearchProductActivity extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    ArrayList<Product> products;
    RecyclerView recyclerView;
    EditText searchView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        initActivity();
        searchView.addTextChangedListener(new TextWatcher() { // filter the products list
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

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Stock").child(Objects.requireNonNull(firebaseAuth.getUid()));
        recyclerView = findViewById(R.id.productsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<Product>();
        searchView = findViewById(R.id.search); // here is the problem
    }

    /**
     * filter the products list
     * @param s the string to search
     */
    private void filter(String s){
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product item : products){
            if(item.getName().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(item);
            }
        }
        productAdapter.filterList(filteredList);
    }

    /**
     *this function get all the products from the database
     *and put it to recycler view
    */
    private void adapt() {
        productAdapter = new ProductAdapter(this, products);
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("product", products.get(position));
                setResult(RESULT_OK, intent);
                finish();
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
}