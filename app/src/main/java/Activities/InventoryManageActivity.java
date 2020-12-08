package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.ProductAdapter;
import com.example.myapplication.ProductsListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DataStructures.Product;

public class InventoryManageActivity extends AppCompatActivity {

    Button addProduct;
    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_manage);

        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Stock").child(firebaseAuth.getUid());
        recyclerView = findViewById(R.id.inventoryRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<Product>();
        addProduct = (Button) findViewById(R.id.addProduct);
        adapt();
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    public void showAddDialog() {
        final Dialog dialog = new Dialog(InventoryManageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); //able to cancel the dialog by clicking outside the dialog
        dialog.setContentView(R.layout.dialog_add_inventory);

        final EditText productName = dialog.findViewById(R.id.insertNameProduct);
        final EditText productBarcode = dialog.findViewById(R.id.insertBarcodProduct);
        final EditText productPrice = dialog.findViewById(R.id.insertPriceOfProduct);
        Button addBt = dialog.findViewById(R.id.addFromDialog);

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = productName.getText().toString();
                int barcode = Integer.parseInt(productBarcode.getText().toString());
                double price = Double.parseDouble(productPrice.getText().toString());
                addProduct(name, barcode, price);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void addProduct(String name, int barcode, double price) {
        Product product = new Product(name, barcode, price, 1);
        ref.push().setValue(product);
//        products.add(product);\
//       productAdapter.notifyDataSetChanged();
    }

    private void adapt() {
        productAdapter = new ProductAdapter(this, products);
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