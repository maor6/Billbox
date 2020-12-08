package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.FirebaseDatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    TextView registerbt;
    EditText editEmail;
    EditText editPass;
    CardView loginbt;
    CardView cardGoogle;
    ProgressBar progressBar;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        registerbt = (TextView) findViewById(R.id.registerbt);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPass = (EditText) findViewById(R.id.editTextPass);
        loginbt = (CardView) findViewById(R.id.cardLogin);
        cardGoogle = (CardView) findViewById(R.id.cardGoogle);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        rememberMe = (CheckBox) findViewById(R.id.rememberMELogin);
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                if (buttonView.isChecked()) {
                    editor.putBoolean("rememberLogin", true);
                    editor.putString("mail", editEmail.getText().toString());
                    editor.putString("password", editPass.getText().toString());
                }
                else {
                    editor.putBoolean("rememberLogin", false);
                }
                editor.apply();
            }
        }); // listen to checkbox

        registerbt.setOnClickListener(this);
        loginbt.setOnClickListener(this);
        cardGoogle.setOnClickListener(this);

        checkCheckBox();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == registerbt.getId()){ // press the register button
            startActivity(new Intent(Login_Activity.this, ChooseUserActivity.class));
        }

        if (view.getId() == loginbt.getId()) { // press the login button
            String email = editEmail.getText().toString();
            String pass = editPass.getText().toString();
            if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)) { // not entered email or password
                Toast.makeText(Login_Activity.this, "Email or Password invalid.",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                LogIn(email,pass); // sign in with firebase
            }
        }

        //TODO implement sign in with google
        if (view.getId() == cardGoogle.getId()) { // sign in with google

        }
    }

       /*
        this method will check if the "remember me" CheckBox is on,
        and if it is, it will logIn automatically
     */

    private void checkCheckBox() {
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        if (preferences.getBoolean("rememberLogin", false)) {
            String mail = preferences.getString("mail", "");
            String password =  preferences.getString("password", "");
            editEmail.setText(mail);
            editPass.setText(password);
            rememberMe.setChecked(true);
            LogIn(mail, password);
        }
    }


    /*
        this method will log in the system'
        and check which activity to open business or customer
     */

    private void LogIn (String email, String pass) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // updateUI(user);
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Intent intent;
                                    if (dataSnapshot.child("Users").child("Bussines").hasChild(mAuth.getUid())) {
                                        intent = new Intent(Login_Activity.this, Business_HomeActivity.class);
                                    }
                                    else {
                                        intent = new Intent(Login_Activity.this, Customer_HomeActivity.class);
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("NotGood", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }


                    // ...

                });
    }
}