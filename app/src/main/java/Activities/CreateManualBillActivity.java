package Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;
import DataStructures.Receipt;

public class CreateManualBillActivity extends AppCompatActivity {

    ImageView receiptPic;
    TextView takePic;
    Bitmap bitmap;
    EditText businessName;
    EditText paid;
    TextView purchaseDate;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Button finishBt;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_manual_bill);

        initActivity();
        //request for camera run time permission
        if(ContextCompat.checkSelfPermission(CreateManualBillActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CreateManualBillActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        takePic.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,100);
        });
        purchaseDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(CreateManualBillActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
        dateSetListener = (datePicker, year, month, day) -> {
            month = month+1;
            String date = day+"/"+month+"/"+year;
            purchaseDate.setText(date);
        };
        finishBt.setOnClickListener(view -> {
            pushReceipt(); // push to DB
        });
    }

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        receiptPic = findViewById(R.id.receiptPicture);
        finishBt = findViewById(R.id.finishBtManualReceipt);
        takePic = findViewById(R.id.takeReceiptPic);
        purchaseDate = findViewById(R.id.purchaseDatePickerManual);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        businessName = findViewById(R.id.businessNameManualBill);
        paid = findViewById(R.id.totalPriceManual);
    }

    /**
     * to get the picture that the user take
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            assert data != null;
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            receiptPic.setImageBitmap(bitmap);
            takePic.setText("התמונה עלתה");
        }
    }

    /**
     * push receipt to the DB
     */
    public void pushReceipt() {
        if (TextUtils.isEmpty(businessName.getText().toString())) { // business is empty
            businessName.setError("הכנס שם עסק");
            businessName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(paid.getText().toString())) { // total paid is empty
            paid.setError("הכנס סכום ששולם");
            paid.requestFocus();
            return;
        }
        String imageKey =  uploadReceiptPicture(bitmap); // upload to DB
        Receipt receipt = new Receipt(businessName.getText().toString(), purchaseDate.getText().toString(),
                Double.parseDouble(paid.getText().toString()), imageKey);
        DatabaseReference referenceDoc = FirebaseDatabase.getInstance().getReference("Documents")
                .child("Receipt");
        referenceDoc.child(Objects.requireNonNull(firebaseAuth.getUid())).push().setValue(receipt);

    }

    /**
     * upload receipt picture to storage in DB
     * @param bitmap the picture in bitmap
     * @return the imageKey
     */
    private String uploadReceiptPicture(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        final String imageKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+firebaseAuth.getUid()+"/"+imageKey);
        byte[] bytes = stream.toByteArray();
        riversRef.putBytes(bytes)
                .addOnSuccessListener(taskSnapshot -> {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                    Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().addOnSuccessListener(uri -> {
                    });
                    Toast.makeText(getApplicationContext(), "save completed", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                    Toast.makeText(getApplicationContext(), "failed to save picture" , Toast.LENGTH_SHORT).show();
                });
        return imageKey;
    }
}