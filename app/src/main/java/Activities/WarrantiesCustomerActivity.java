package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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
    }


}