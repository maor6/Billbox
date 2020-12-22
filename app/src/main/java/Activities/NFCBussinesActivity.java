package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import DataStructures.Receipt;

import com.example.myapplication.SendNotificationPack.ApiInterface;
import com.example.myapplication.SendNotificationPack.Client;
import com.example.myapplication.SendNotificationPack.Data;
import com.example.myapplication.SendNotificationPack.MyNotification;
import com.example.myapplication.SendNotificationPack.RootModel;
import com.example.myapplication.SendNotificationPack.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * This is an activity class to sent a receipt to costumer-user //TODO sent it with NFC
 */

public class NFCBussinesActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    TextView mEditText;
    Button enter;
    EditText phoneNumber;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("Customer");
    Receipt receipt;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_bussines);

        initActivity();

        updateToken(FirebaseAuth.getInstance().getCurrentUser().getUid()); // update my token

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushReceipt(phoneNumber.getText().toString(), receipt);
            }
        });

        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            mEditText.setText("Sorry this device does not have NFC.");
            return;
        }

        if (!mAdapter.isEnabled()) { // go to NFC phone settings
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }

        mAdapter.setNdefPushMessageCallback(this, this);
    }

    /**
     * function that create/update a token in the DB
     */
    private void updateToken(String uid) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(uid).setValue(token);
    }

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        phoneNumber = (EditText) findViewById(R.id.NFCBussinesPhoneNumber);
        enter = (Button) findViewById(R.id.NFC_bussines_continue);
        mEditText = (TextView) findViewById(R.id.test1);
        receipt = (Receipt) getIntent().getSerializableExtra("receipt");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String message = mEditText.getText().toString();
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", receipt.toString().getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

    /**
     * this method search the correct customer from the DB
     * and push the receipt to the customer
     *
     * @param phoneToSearch
     * @param receipt
     */
    public void pushReceipt(String phoneToSearch, Receipt receipt) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = "";
                String currentPhone = "";
                boolean found = false;
                for (DataSnapshot id : dataSnapshot.getChildren()) { // search the correct user
                    uid = id.getKey(); // get the UID of the customer
                    currentPhone = Objects.requireNonNull(id.child("phoneNumber").getValue()).toString();
                    if (phoneToSearch.equals(currentPhone)) { // found the correct customer by phone
                        found = true;
                        break;
                    }
                }
                if (found) { // Add the receipt to the correct customer by phone number
                    DatabaseReference referenceCustomer = firebaseDatabase.getReference("Documents")
                            .child("Receipt"); // get the reference of the correct customer
                    referenceCustomer.child(uid).push().setValue(receipt);
                    Toast.makeText(NFCBussinesActivity.this, "Send Complete.",
                            Toast.LENGTH_SHORT).show();

                    // add the receipt also to the Business
                    referenceCustomer.child(Objects.requireNonNull(firebaseAuth.getUid())).push().setValue(receipt);

                    // send notification that the receipt is sent
                    FirebaseDatabase.getInstance().getReference().child("Tokens")
                            .child(uid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String usertoken = dataSnapshot.getValue(String.class);
                            sendNotificationToUser(usertoken);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    finish();

                } else { // we don't find the phoneNumber of the customer
                    Toast.makeText(NFCBussinesActivity.this, "Send Failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * function to send notification to specific user
     * @param token of the user that we want to sent it to
     */
    private void sendNotificationToUser(String token) {
        RootModel rootModel = new RootModel(token, new MyNotification("Title", "Body"),
                new Data("Name", "30"));
        ApiInterface apiService = Client.getClient().create(ApiInterface.class);
        retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendNotification(rootModel);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}