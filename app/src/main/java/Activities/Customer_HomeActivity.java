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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import DataStructures.Receipt;
import com.bumptech.glide.Glide;
import com.example.myapplication.ProductsListAdapter;
import com.example.myapplication.ReceiptAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This is an activity class which operate the customer-main screen.
 */
public class Customer_HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView helloUser;
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

        initActivity();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.NavigationDrawerOpen,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        DatabaseReference referenceForName = firebaseDatabase.getReference().child("Users")
                .child("Customer").child(Objects.requireNonNull(firebaseAuth.getUid())).child("name");
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

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        databaseReference = firebaseDatabase.getReference().child("Documents").child("Receipt").child(Objects.requireNonNull(firebaseAuth.getUid()));
        helloUser = (TextView) findViewById(R.id.helloUser); //to put the user name
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_reciepts);
    }

    /**
     this method will get the receipt from the database
     and put it in the recycler view
     */
    private void getReceipts() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        receipts = new ArrayList<>();
        receiptAdapter = new ReceiptAdapter(this, receipts);
        receiptAdapter.setOnItemClickListener(new ReceiptAdapter.OnItemClickListener() { // listen when clicked on receipt
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
                    Receipt receipt = (Receipt) dataSnapshot1.getValue(Receipt.class);
                    receipts.add(receipt);
                }
                receiptAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * this function open the full receipt data
     * @param receipt the receipt to open
     */
    private void openBillDialog(Receipt receipt) {
        final Dialog dialog = new Dialog(Customer_HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); //able to cancel the dialog by clicking outside the dialog

        if(receipt.isManual) {
            dialog.setContentView(R.layout.dialog_manual_receipt);
            ImageView image = dialog.findViewById(R.id.receiptPictureView);
            try{
                StorageReference storageReference =  FirebaseStorage.getInstance().getReference().child("images/"+firebaseAuth.getUid()+"/"+receipt.getImageID());
                Glide.with(Customer_HomeActivity.this)
                        .load(storageReference)
                        .into(image);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "failed to show picture" , Toast.LENGTH_SHORT).show();
            }
        }
        else {
            dialog.setContentView(R.layout.dialog_full_reciept);
            TextView businessName = dialog.findViewById(R.id.businessNameBill);
            TextView businessAddress = dialog.findViewById(R.id.businessAddressBill);
            TextView businessPhone = dialog.findViewById(R.id.businessPhoneBill);
            TextView businessTime = dialog.findViewById(R.id.timeBill);
            TextView receiptId = dialog.findViewById(R.id.billNumber);
            TextView taxes = dialog.findViewById(R.id.taxesBill);
            TextView total = dialog.findViewById(R.id.totalWithTaxesBill);
            TextView notes = dialog.findViewById(R.id.notes);
            ListView productsList = dialog.findViewById(R.id.productsListBill);

            businessName.setText(receipt.getBusinessName());
            businessAddress.setText(receipt.getBusinessAddress());
            businessPhone.setText(receipt.getBusinessPhone());
            businessTime.setText(receipt.getDate());
            receiptId.setText("מספר-" + receipt.getId());
            taxes.setText("מע\"מ 17%: " + String.format("%.2f", (receipt.getTotal_price() * 0.17)));
            total.setText("סה\"כ לתשלום: " + String.format("%.2f", receipt.getTotal_price()));
            notes.setText("הערות: " + receipt.getNotes());
            productsListAdapter = new ProductsListAdapter(this, R.layout.bill_products_list, receipt.getItems());
            productsList.setAdapter(productsListAdapter);
        }
        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.searchDocumentCustomerMenu:
                startActivity(new Intent(Customer_HomeActivity.this, AllDocumentsCostumerActivity.class));
                return true;
            case R.id.createDocumentCustomerMenu:
                startActivity(new Intent(Customer_HomeActivity.this, CreateManualBillActivity.class));
                return true;
            case R.id.warranties:
                startActivity(new Intent(Customer_HomeActivity.this, WarrantiesCustomerActivity.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
