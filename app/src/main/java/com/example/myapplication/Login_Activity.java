package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
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

        registerbt.setOnClickListener(this);
        loginbt.setOnClickListener(this);
        cardGoogle.setOnClickListener(this);

        //TODO if the current user already signin
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
            startActivity(new Intent(Login_Activity.this,ChooseUserActivity.class));
        }

        if (view.getId() == loginbt.getId()) { // press the login button
            String email = editEmail.getText().toString();
            String pass = editPass.getText().toString();
            if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)) { // not entered email or password
                Toast.makeText(Login_Activity.this, "Email or Password invalid.",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
                LogIn(email,pass); // sign in with firebase
            }
        }

        if (view.getId() == cardGoogle.getId()) { // sign in with google

        }
    }

    private void LogIn (String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(Login_Activity.this,  new OnCompleteListener<AuthResult>() {
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
                            // ...
                        }
                    }


                    // ...

                });
    }
}