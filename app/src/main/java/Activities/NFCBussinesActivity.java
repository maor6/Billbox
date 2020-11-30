package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import DataStructures.receipt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NFCBussinesActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    DataStructures.receipt receipt;
    TextView mEditText;
    String rec;
    Button enter;
    EditText phoneNumber;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("Customer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_bussines);
        phoneNumber = (EditText) findViewById(R.id.NFCBussinesPhoneNumber);
        enter = (Button) findViewById(R.id.NFC_bussines_continue);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushReceipt(phoneNumber.getText().toString());
            }
        });
        Bundle extras = getIntent().getExtras(); // get the receipt
        if (extras != null) {
            rec = extras.getString("receipt");
            receipt = new receipt(rec);
        }
        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            mEditText.setText("Sorry this device does not have NFC.");
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        mAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) { // call when android beam invoked
        String message = mEditText.getText().toString();
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", rec.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }


    public void pushReceipt(String phone) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = "";
                String currentPhone = "";
                boolean found = false;
                for (DataSnapshot id : dataSnapshot.getChildren()) {
                    uid = id.getKey(); // get the UID of the customer
                    currentPhone = id.child("phoneNumber").getValue().toString();
                    if (phone.equals(phoneNumber)) { // found the currect customer by phone
                        found = true;
                        break;
                    }
                }
                if (found) { // Add the receipt to the currect customer
                    DatabaseReference ReferenceCustomer = firebaseDatabase.getReference("Documents")
                            .child("Receipt").child(uid); // get the referense of thr currect customer to push hin the receipt
                    receipt = new receipt(" ,1000, , , ,pizza,25/11/2020");
                    ReferenceCustomer.push().setValue(receipt);
                    Toast.makeText(NFCBussinesActivity.this, "Send Complete.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(NFCBussinesActivity.this, "Send Faild.",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}