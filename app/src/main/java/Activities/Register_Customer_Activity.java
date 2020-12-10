package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import DataStructures.Customer;
import DataStructures.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Register_Customer_Activity extends AppCompatActivity {

    FirebaseAuth mauth;
    ProgressBar progressBar;
    Button buttonReg;
    EditText name;
    EditText lastName;
    EditText email;
    EditText pass;
    EditText verPass;
    EditText phoneNumber;
    CountryCodePicker countryCodePicker;
    String verificationId;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        init();

        buttonReg.setOnClickListener(new View.OnClickListener() { // activate when clicked המשך
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) { // mail is empty
                    email.setError("Enter valid email");
                    email.requestFocus();
                    return;
                }

                if (pass.getText().toString().length() < 6) { // password length need to be more then 6
                    pass.setError("Password at least 6 Characters");
                    pass.requestFocus();
                    return;
                }

                if (!pass.getText().toString().equals(verPass.getText().toString())) { // the passwords do not match
                    verPass.setError("Password at least 6 Characters");
                    verPass.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                openVerificationDialog();
                String number = countryCodePicker.getSelectedCountryCodeWithPlus() + phoneNumber.getText().toString();
                sendVerificationCode(number); // send sms to full phoneNumber
            }
        });
    }


    /**
     * this method will initialize variables
     */

    private void init() {
        buttonReg = (Button) findViewById(R.id.continue_reg);
        name = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        pass = (EditText) findViewById(R.id.password);
        verPass = (EditText) findViewById(R.id.verPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBarReg);
        mauth = FirebaseAuth.getInstance();
        countryCodePicker = (CountryCodePicker) findViewById(R.id.cpp);
        verificationId = null;
        progressBar.setVisibility(View.GONE);
    }


    /**
     * this method responsible the dialog
     */

    private void openVerificationDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_verification_sms);

        Button done = (Button) dialog.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }


    /**
     * this method create Account with email and password on DB
     */

    public void createAccount() {
        mauth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(Register_Customer_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new Customer(name.getText().toString(), lastName.getText().toString(),
                                    email.getText().toString(), pass.getText().toString(),
                                    countryCodePicker.getSelectedCountryCodeWithPlus()
                                            + phoneNumber.getText().toString());

                            DatabaseReference referenceCustomer = FirebaseDatabase.getInstance().getReference();
                            referenceCustomer.child("Users")
                                    .child("Customer").child(mauth.getUid()).setValue(user); // get the reference of the correct customer
                            Toast.makeText(Register_Customer_Activity.this, "Successfully registered",
                                                Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(Register_Customer_Activity.this, Login_Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear the activity stack
                            startActivity(intent);

                        } else {
                            Toast.makeText(Register_Customer_Activity.this, "Registration Error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) { // send the SMS
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            EditText codeText = (EditText) dialog.findViewById(R.id.verificationCode);
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                codeText.setText(code);
                createAccount();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Register_Customer_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

}
