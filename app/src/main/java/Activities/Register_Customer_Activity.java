package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import DataStructures.Customer;
import DataStructures.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class Register_Customer_Activity extends AppCompatActivity {


    FirebaseAuth mauth;
    ProgressBar progressBar;
    Button buttonReg;
    EditText editTextName;
    EditText editTextLastName;
    EditText editTextEmail;
    EditText editTextPass;
    EditText editTextVerPass;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        buttonReg = (Button) findViewById(R.id.continue_reg);
        editTextName = (EditText) findViewById(R.id.editText);
        editTextLastName = (EditText) findViewById(R.id.editText2);
        editTextEmail = (EditText) findViewById(R.id.editText3);
        editTextPass = (EditText) findViewById(R.id.editText5);
        phoneNumber = (EditText) findViewById(R.id.editText4);
        editTextVerPass = (EditText) findViewById(R.id.editText6);
        progressBar = (ProgressBar) findViewById(R.id.progressBarReg);
        mauth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.GONE);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextEmail.getText().toString())) { // mail is empty
                    return;
                }

                if (editTextPass.getText().toString().length() < 6) { // password lenght need to be more then 6
                    editTextPass.setError("Password at least 6 Characters");
                    return;
                }

                if (!editTextPass.getText().toString().equals(editTextVerPass.getText().toString())) { // the passswords do not match
                    Toast.makeText(Register_Customer_Activity.this, "the Passwords do not Match",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                createAccount();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mauth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void createAccount() {
        //FirebaseDatabaseHelper help = new FirebaseDatabaseHelper();
        mauth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPass.getText().toString())
                .addOnCompleteListener(Register_Customer_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new Customer(editTextName.getText().toString(), editTextLastName.getText().toString()
                                    , editTextEmail.getText().toString(), editTextPass.getText().toString()
                                    ,phoneNumber.getText().toString() );

                            FirebaseDatabase.getInstance().getReference("Users").child("Customer") // upload customer to database
                                    .child(mauth.getCurrentUser().getUid()) // get the user uniqe id
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register_Customer_Activity.this, "Successfully registered",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    else {

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register_Customer_Activity.this, "Registration Error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
