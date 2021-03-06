package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.SendNotificationPack.Token;
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
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

/**
 * This is an activity class to login the app by insert email+password
 */
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

        initActivity();

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // button to remember the user
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
        });

        checkCheckBox(); // listen to checkbox
    }

    private void updateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            String refreshToken= FirebaseInstanceId.getInstance().getToken();
            FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid()).setValue(new Token(refreshToken));
        }

    }

    /**
     * this function initialize the variables in the activity
     */
    private void initActivity() {
        mAuth = FirebaseAuth.getInstance();
        registerbt = (TextView) findViewById(R.id.registerbt);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPass = (EditText) findViewById(R.id.editTextPass);
        loginbt = (CardView) findViewById(R.id.cardLogin);
        cardGoogle = (CardView) findViewById(R.id.cardGoogle);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        rememberMe = (CheckBox) findViewById(R.id.rememberMELogin);
        registerbt.setOnClickListener(this);
        loginbt.setOnClickListener(this);
        cardGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == registerbt.getId()){ // press the register button
            startActivity(new Intent(Login_Activity.this, ChooseUserActivity.class));
        }
        if (view.getId() == loginbt.getId()) { // press the login button
            updateToken();
            String email = editEmail.getText().toString();
            String pass = editPass.getText().toString();
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(Login_Activity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
            else {
                LogIn(email,pass); // login with firebase
            }
        }
        if (view.getId() == cardGoogle.getId()) {
            //TODO implement sign in with google
        }
    }

    /**
     this method will check if the "remember me" CheckBox is on,
     and logIn automatically
     **/
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

    /**
        this method will log in the system'
        and check which activity to open-business or customer
     **/
    private void LogIn (String email, String pass) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Intent intent;
                                    if (dataSnapshot.child("Users").child("Bussines").
                                            hasChild(Objects.requireNonNull(mAuth.getUid()))) {
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
                        } else { // sign in fails, display a message to the user.
                            Toast.makeText(Login_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}