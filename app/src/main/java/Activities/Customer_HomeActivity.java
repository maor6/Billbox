package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import DataStructures.Receipt;

import com.example.myapplication.ProductsListAdapter;
import com.example.myapplication.ReceiptAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer_HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ReceiptAdapter receiptAdapter;
    ArrayList<Receipt> receipts;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ProductsListAdapter productsListAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__home);

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        databaseReference = firebaseDatabase.getReference().child("Documents").child("Receipt").child(Objects.requireNonNull(firebaseAuth.getUid()));

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.NavigationDrawerOpen,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        TextView helloUser = (TextView) findViewById(R.id.helloUser); //to put yhe user name
        DatabaseReference referenceForName = firebaseDatabase.getReference().child("Users").child("Customer").child(firebaseAuth.getUid()).child("name");
        referenceForName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                helloUser.setText("שלום " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //TODO use this ->FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();

        getReceipts();

    }

    /*
    this method will get the receipt from the database
    and put it in the recycler view
     */

    private void getReceipts() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_reciepts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        receipts = new ArrayList<>();
        receiptAdapter = new ReceiptAdapter(this, receipts);
        receiptAdapter.setOnItemClickListener(new ReceiptAdapter.OnItemClickListener() { // listen when clicked on receipt
            @Override
            public void OnItemClick(int position) {
                openBillDialog(receipts.get(position));
            }
        });
        recyclerView.setAdapter(receiptAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() { //TODO needed to be handle by FirebaseDatabaseHelper
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receipts.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Receipt receipt = dataSnapshot1.getValue(Receipt.class);
                    receipts.add(receipt);
                }
                receiptAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void openBillDialog(Receipt receipt) {
        final Dialog dialog = new Dialog(Customer_HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); //able to cancel the dialog by clicking outside the dialog
        dialog.setContentView(R.layout.dialog_full_reciept);

        TextView businessName = dialog.findViewById(R.id.businessNameBill);
        TextView businessAddress = dialog.findViewById(R.id.businessAddressBill);
        TextView businessPhone = dialog.findViewById(R.id.businessPhoneBill);
        TextView businessTime = dialog.findViewById(R.id.timeBill);
        TextView receiptId = dialog.findViewById(R.id.billNumber);
        TextView taxes = dialog.findViewById(R.id.taxesBill);
        TextView total = dialog.findViewById(R.id.totalWithTaxesBill);
        TextView notes = dialog.findViewById(R.id.notesBill);
        ListView productsList = dialog.findViewById(R.id.productsListBill);

        businessName.setText(receipt.getBusiness());
        businessAddress.setText(receipt.getBusinessAddress());
        businessPhone.setText(receipt.getBusinessPhone());
        businessTime.setText(receipt.getDate());
        receiptId.setText(receipt.getId());
        taxes.setText(String.format("%.2f", (receipt.getTotal_price()*0.17)));
        total.setText(String.format("%.2f", receipt.getTotal_price()));
        notes.setText(receipt.getNotes());
        productsListAdapter = new ProductsListAdapter(this, R.layout.bill_products_list, receipt.getItems());
        productsList.setAdapter(productsListAdapter);

        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
