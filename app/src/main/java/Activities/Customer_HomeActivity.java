package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myapplication.MyAddapter;
import com.example.myapplication.R;
import DataStructures.receipt;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Customer_HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    MyAddapter myAddapter;
    ArrayList<receipt> receipts;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Documents").child("Receipt").child(firebaseAuth.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__home);

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

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


        //TODO use this ->FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_reciepts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        receipts = new ArrayList<>();
        myAddapter = new MyAddapter(this, receipts);
        myAddapter.setOnItemClickListener(new MyAddapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //TODO open activity that open the full receipt
                startActivity(new Intent(Customer_HomeActivity.this, Business_HomeActivity.class));
            }
        });
        recyclerView.setAdapter(myAddapter);

        databaseReference.addValueEventListener(new ValueEventListener() { //TODO needed to be handle by FirebaseDatabaseHelper
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receipts.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    receipt receipt = dataSnapshot1.getValue(receipt.class);
                    receipts.add(receipt);
                }
                myAddapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
