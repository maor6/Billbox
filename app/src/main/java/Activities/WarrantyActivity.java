package Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.ProductsListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;

import DataStructures.Business;
import DataStructures.Product;
import DataStructures.Receipt;
import DataStructures.Warranty;

public class WarrantyActivity extends AppCompatActivity {

    TextView searchProduct;
    TextView description;
    TextView barcode;
    TextView expiryDate;
    Button finishBt;
    DatePickerDialog.OnDateSetListener expiryDateSetListener;
    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    Business business;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warranty);

        initActivity();
        searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(WarrantyActivity.this, SearchProductActivity.class);
                startActivityForResult(intent, 2);
            }
        });
        expiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(WarrantyActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,expiryDateSetListener,year,month,day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        expiryDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                expiryDate.setText(date);

            }
        };
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                business = (Business) dataSnapshot.child(Objects.requireNonNull(firebaseAuth.getUid())).getValue(Business.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        finishBt.setOnClickListener(new View.OnClickListener() { // on "סיים" clicked
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
                LocalDate localDate = LocalDate.now();
                Intent intent = new Intent(WarrantyActivity.this, NFCBussinesActivity.class);
                Warranty warranty = new Warranty(business.getBusiness_name(), dtf.format(localDate),
                        business.getAddress(), business.getPhoneNumber(), product, expiryDate.toString(),"nothing");
                intent.putExtra("warranty", warranty);
                startActivity(intent);
            }
        });
    }

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        searchProduct = findViewById(R.id.searchToWarranty);
        description = findViewById(R.id.product_name_warranty);
        barcode =  findViewById(R.id.product_barcode_warranty);
        expiryDate = findViewById(R.id.expiryDatePicker);
        finishBt = findViewById(R.id.finishbtWarranty);
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Bussines");

    }

    /**
     * to get a product from the search-activity and add it to the list in the warranty
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // activate when return to this activity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                product = (Product) data.getSerializableExtra("product");
                assert product != null;
                this.description.setText(product.getName());
                this.barcode.setText(String.valueOf(product.getBarCode()));
            }
        }
    }
}