package Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.myapplication.ProductsListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import DataStructures.Business;
import DataStructures.Product;
import DataStructures.Receipt;


public class CreateBillActivity extends AppCompatActivity {

    CardView search;
    Button finishBt;
    ArrayList<Product> products;
    ListView itemsList;
    ProductsListAdapter productsListAdapter;
    TextView totalPriceView;
    double totalToPay;
    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    Business business;
    public static int receiptID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Bussines");
        products = new ArrayList<>();
        itemsList = (ListView) findViewById(R.id.productsList);
        totalToPay = 0;
        totalPriceView = (TextView) findViewById(R.id.totalToPay);
        search = (CardView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CreateBillActivity.this, SearchProductActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                business = (Business) dataSnapshot.child(firebaseAuth.getUid()).getValue(Business.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        finishBt = (Button) findViewById(R.id.finishbt);
        finishBt.setOnClickListener(new View.OnClickListener() { // on "סיים" clicked

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
                LocalDate localDate = LocalDate.now();
                Intent intent = new Intent(CreateBillActivity.this, NFCBussinesActivity.class);
                Receipt receipt = new Receipt(business.getBusiness_name(), dtf.format(localDate), totalToPay,
                        business.getAddress(), business.getPhoneNumber(), business.getBillNotes(), String.valueOf(receiptID));
                receiptID++;
                receipt.setItems(products);
                intent.putExtra("receipt", receipt);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // activate when return to this activity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                Product product = (Product) data.getSerializableExtra("product");
                this.products.add(product);
                totalToPay += product.getPrice();
                totalPriceView.setText("סה\"כ לתשלום: " + String.format("%.2f", totalToPay)+"₪");
                productsListAdapter = new ProductsListAdapter(this, R.layout.bill_products_list, products);
                itemsList.setAdapter(productsListAdapter);
            }
        }
    }
}