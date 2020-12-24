package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.ProductsListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import DataStructures.Business;
import DataStructures.Receipt;
import DataStructures.User;

/**
 * This is an activity class to watch ot edit the business information.
 */
public class BusinessProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Business business;
    TextView phoneNumber;
    TextView email;
    TextView location;
    TextView businessName;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    Button saveBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        initActivity();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                business = (Business) dataSnapshot.getValue(Business.class);
                phoneNumber.setText(business.getPhoneNumber());
                email.setText(business.getEmail());
                location.setText(business.getAddress());
                businessName.setText(business.getBusiness_name());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        phoneNumber = (TextView) findViewById(R.id.phoneNumberProfile);
        phoneNumber.setOnClickListener(this);
        email = (TextView) findViewById(R.id.mailProfile);
        location = (TextView) findViewById(R.id.locationProfile);
        location.setOnClickListener(this);
        businessName = (TextView) findViewById(R.id.businessNameProfile);
        businessName.setOnClickListener(this);
        databaseReference = firebaseDatabase.getReference().child("Users").child("Bussines").child(Objects.requireNonNull(firebaseAuth.getUid()));
        saveBt = (Button) findViewById(R.id.saveBt);
        saveBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == phoneNumber){
            phoneNumber.setCursorVisible(true);
            phoneNumber.setFocusableInTouchMode(true);
            phoneNumber.setInputType(InputType.TYPE_CLASS_TEXT);
            phoneNumber.requestFocus();
            saveBt.setVisibility(View.VISIBLE);
        }
        if (view == location){
            location.setCursorVisible(true);
            location.setFocusableInTouchMode(true);
            location.setInputType(InputType.TYPE_CLASS_TEXT);
            location.requestFocus();
            saveBt.setVisibility(View.VISIBLE);
        }
        if (view == businessName){
            businessName.setCursorVisible(true);
            businessName.setFocusableInTouchMode(true);
            businessName.setInputType(InputType.TYPE_CLASS_TEXT);
            businessName.requestFocus();
            saveBt.setVisibility(View.VISIBLE);
        }
        if(view == saveBt){
            databaseReference.child("address").setValue(location.getText().toString());
            databaseReference.child("business_name").setValue(businessName.getText().toString());
            databaseReference.child("phoneNumber").setValue(phoneNumber.getText().toString());
            finish();
        }

    }
}