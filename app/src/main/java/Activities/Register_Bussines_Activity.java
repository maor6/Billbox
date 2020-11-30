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

import DataStructures.Business;
import DataStructures.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__bussines);


        name = (EditText) findViewById(R.id.editText);
        lastName = (EditText) findViewById(R.id.editText2);
        bussinesName = (EditText) findViewById(R.id.bussinesname);
        adrress = (EditText) findViewById(R.id.adrress);
        email = (EditText) findViewById(R.id.editText3);
        phoneNumber = (EditText) findViewById(R.id.editText4);
        pass = (EditText) findViewById(R.id.editText5);
        verPass = (EditText) findViewById(R.id.editText6);
        loading = (ProgressBar) findViewById(R.id.progressBar2);

        mauth = FirebaseAuth.getInstance();
        loading.setVisibility(View.GONE);
        continuebt = (Button) findViewById(R.id.continue_reg);

        continuebt.setOnClickListener(new View.OnClickListener() { // button continue pressed
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) { // mail is empty
                    return;
                }

                if (pass.getText().toString().length() < 6) { // password lenght need to be more then 6
                    pass.setError("Password at least 6 Characters");
                    return;
                }

                if (!pass.getText().toString().equals(verPass.getText().toString())) { // the passswords do not match
                    Toast.makeText(Register_Bussines_Activity.this, "the Passwords do not Match",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(bussinesName.getText().toString())) { // bussines name empty
                    Toast.makeText(Register_Bussines_Activity.this, "Must enter bussines name",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                loading.setVisibility(View.VISIBLE);
                createAccount();
            }
        });
    }

    public void createAccount() {
        mauth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(Register_Bussines_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new Business(name.getText().toString(), lastName.getText().toString()
                                    , email.getText().toString(), pass.getText().toString(),
                                    bussinesName.getText().toString(), adrress.getText().toString()
                                    , phoneNumber.getText().toString()); //true for bussines

                            FirebaseDatabase.getInstance().getReference("Users").child("Bussines")
                                    .child(mauth.getCurrentUser().getUid()) // get the user uniqe id
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register_Bussines_Activity.this, "Successfully registered",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    else {

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
}


