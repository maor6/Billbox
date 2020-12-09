package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import DataStructures.Business;
import DataStructures.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Register_Bussines_Activity extends AppCompatActivity {

    EditText name;
    EditText lastName;
    EditText bussinesName;
    EditText adrress;
    EditText email;
    EditText phoneNumber;
    EditText pass;
    EditText verPass;
    Button continuebt;
    FirebaseAuth mauth;
    ProgressBar loading;
    CountryCodePicker countryCodePicker;
    String verificationId;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__bussines);

       init();

        continuebt.setOnClickListener(new View.OnClickListener() { // button continue pressed
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) { // mail is empty
                    email.setError("Enter valid email");
                    email.requestFocus();
                    return;
                }

                if (pass.getText().toString().length() < 6) { // password lenght need to be more then 6
                    pass.setError("Password at least 6 Characters");
                    pass.requestFocus();
                    return;
                }

                if (!pass.getText().toString().equals(verPass.getText().toString())) { // the passswords do not match
                    verPass.setError("Password don't match");
                    verPass.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(bussinesName.getText().toString())) { // bussines name empty
                    bussinesName.setError("Must enter bussines name");
                    bussinesName.requestFocus();
                    return;
                }

                loading.setVisibility(View.VISIBLE);

                openVerificationDialog();
                String number = countryCodePicker.getSelectedCountryCodeWithPlus() + phoneNumber.getText().toString();//full phoneNumber
                sendVerificationCode(number); // send sms to full phoneNumber
            }
        });
    }


    /**
     * this method will initialize variables
     */

    private void init() {
        name = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        bussinesName = (EditText) findViewById(R.id.bussinesname);
        adrress = (EditText) findViewById(R.id.adrress);
        email = (EditText) findViewById(R.id.email);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        pass = (EditText) findViewById(R.id.password);
        verPass = (EditText) findViewById(R.id.verPassword);
        loading = (ProgressBar) findViewById(R.id.progressBar2);
        loading.setVisibility(View.GONE);
        mauth = FirebaseAuth.getInstance();
        continuebt = (Button) findViewById(R.id.continue_reg);
        countryCodePicker = (CountryCodePicker) findViewById(R.id.cpp);
        verificationId = null;
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
     * this method creat Account with email and password on DB
     */

    public void createAccount() {
        mauth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(Register_Bussines_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new Business(name.getText().toString(), lastName.getText().toString()
                                    , email.getText().toString(), pass.getText().toString(),
                                    bussinesName.getText().toString(), adrress.getText().toString()
                                    ,countryCodePicker.getSelectedCountryCodeWithPlus()
                                    + phoneNumber.getText().toString());

                            FirebaseDatabase.getInstance().getReference("Users").child("Bussines")
                                    .child(mauth.getCurrentUser().getUid()) // get the user unique id
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register_Bussines_Activity.this, "Successfully registered",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(Register_Bussines_Activity.this, "Registration Error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Toast.makeText(Register_Bussines_Activity.this, "verifyCode", Toast.LENGTH_LONG).show();

        //signIN
        //start new activity to verify the code (suppose to do automatically)
        // intent set flag with NEW_taks | CLEAR_TASK will empty the stack of activity
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
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) { // activate when complete
            EditText codeText = (EditText) dialog.findViewById(R.id.verificationCode);
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                codeText.setText(code);
                //verifyCode(code);
                createAccount();
                Intent intent = new Intent(Register_Bussines_Activity.this, Login_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear the activity stack
                startActivity(intent);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Register_Bussines_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}


