package Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.ProductsListAdapter;
import com.example.myapplication.ReceiptAdapter;
import com.example.myapplication.ReceiptBusinessAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import DataStructures.Receipt;

public class AllDocumentsBusinessActivity extends AppCompatActivity {

    ReceiptBusinessAdapter receiptBusinessAdapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ProductsListAdapter productsListAdapter;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Receipt> receipts;
    Button searchDocument;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_documents_business);

        initActivity();
        getReceipts();
        searchDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO open the filter screen
            }
        });
    }

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        searchDocument = (Button) findViewById(R.id.filter_document);
        databaseReference = firebaseDatabase.getReference().child("Documents")
                .child("Receipt")
                .child(Objects.requireNonNull(firebaseAuth.getUid()));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_receipts);

    }

    /**
     this method will get the receipt from the database
     and put it in the recycler view
     */
    private void getReceipts() {
        progressBar = (ProgressBar) findViewById(R.id.progressBarAllDocuments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        receipts = new ArrayList<>();
        receiptBusinessAdapter = new ReceiptBusinessAdapter(this, receipts);
        receiptBusinessAdapter.setOnItemClickListener(new ReceiptBusinessAdapter.OnItemClickListener() { // listen when clicked on receipt
            public void OnItemClick(int position) {
                openBillDialog(receipts.get(position));
            }
        });
        recyclerView.setAdapter(receiptBusinessAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() { //TODO needed to be handle by FirebaseDatabaseHelper
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receipts.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Receipt receipt = (Receipt) dataSnapshot1.getValue(Receipt.class);
                    receipts.add(receipt);
                }
                receiptBusinessAdapter.notifyDataSetChanged();
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
        final Dialog dialog = new Dialog(AllDocumentsBusinessActivity.this);
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
        TextView notes = dialog.findViewById(R.id.notes);
        ListView productsList = dialog.findViewById(R.id.productsListBill);

        businessName.setText(receipt.getBusinessName());
        businessAddress.setText(receipt.getBusinessAddress());
        businessPhone.setText(receipt.getBusinessPhone());
        businessTime.setText(receipt.getDate());
        receiptId.setText("מספר-"+receipt.getId());
        taxes.setText("מע\"מ 17%: " + String.format("%.2f", (receipt.getTotal_price()*0.17)));
        total.setText("סה\"כ לתשלום: " + String.format("%.2f", receipt.getTotal_price()));
        notes.setText(  "הערות: " + receipt.getNotes());
        productsListAdapter = new ProductsListAdapter(this, R.layout.bill_products_list, receipt.getItems());
        productsList.setAdapter(productsListAdapter);

        dialog.show();
    }

}