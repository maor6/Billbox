package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.ProductsListAdapter;
import com.example.myapplication.ReceiptAdapter;
import com.example.myapplication.WarrantiesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import DataStructures.Receipt;
import DataStructures.Warranty;

/**
 *This is an activity class is to watch the warranties by customer user.
 */
public class WarrantiesCustomerActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    WarrantiesAdapter warrantiesAdapter;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Warranty> warranties;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warranties_customer);

        initActivity();
        getWarranties();

    }

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        progressBar = (ProgressBar) findViewById(R.id.progressBarWarranties);
        databaseReference = firebaseDatabase.getReference()
                .child("Documents").child("Warranty")
                .child(Objects.requireNonNull(firebaseAuth.getUid()));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_warranties);
    }

    /**
     * retrieve the warranties from DB
     */
    private void getWarranties() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        warranties = new ArrayList<>();
        warrantiesAdapter = new WarrantiesAdapter(this, warranties);
        warrantiesAdapter.setOnItemClickListener(new WarrantiesAdapter.OnItemClickListener() { // listen when clicked on wa
            public void OnItemClick(int position) {
                openBillDialog(warranties.get(position));
            }
        });
        recyclerView.setAdapter(warrantiesAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() { //TODO needed to be handle by FirebaseDatabaseHelper
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                warranties.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Warranty warranty = (Warranty) dataSnapshot1.getValue(Warranty.class);
                    warranties.add(warranty);
                }
                warrantiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void openBillDialog(Warranty warranty) {
        final Dialog dialog = new Dialog(WarrantiesCustomerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); //able to cancel the dialog by clicking outside the dialog
        dialog.setContentView(R.layout.dialog_full_warranty);

        TextView businessName = dialog.findViewById(R.id.businessNameWarrantyDialog);
        TextView businessAddress = dialog.findViewById(R.id.businessAddressWarranty);
        TextView businessPhone = dialog.findViewById(R.id.businessPhoneWarranty);
        TextView businessTime = dialog.findViewById(R.id.timeWarranty);
        TextView notes = dialog.findViewById(R.id.notesWarranty);
        TextView description = dialog.findViewById(R.id.description_warranty_dialog);
        TextView barcode = dialog.findViewById(R.id.barcode_warranty_dialog);
        TextView validityUntil = dialog.findViewById(R.id.expiry_date_dialog);

        businessName.setText(warranty.getBusinessName());
        businessAddress.setText(warranty.getBusinessAddress());
        businessPhone.setText(warranty.getBusinessPhone());
        businessTime.setText(warranty.getDate());
        notes.setText("הערות: "+warranty.getRules());
        description.setText(warranty.getProduct().getName());
        barcode.setText(String.valueOf(warranty.getProduct().getBarCode()));
        validityUntil.setText(warranty.getExpiryDate());
        dialog.show();
    }


}