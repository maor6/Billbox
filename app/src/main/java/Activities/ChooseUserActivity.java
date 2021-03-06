package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This is an activity class to let user choose the user-type before register to app
 */
public class ChooseUserActivity extends AppCompatActivity implements View.OnClickListener {

    CardView imCus;
    CardView imBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        initActivity();
    }

    /**
     * this function initialize the views in the screen
     */
    private void initActivity() {
        imCus = (CardView) findViewById(R.id.customerbt);
        imBus = (CardView) findViewById(R.id.bussinesbt);

        imCus.setOnClickListener(this);
        imBus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == imCus) {
            Intent intent=new Intent(this, Register_Customer_Activity.class);
            startActivity(intent);
        }

        else if (view == imBus) {
            Intent intent = new Intent(this, Register_Bussines_Activity.class);
            startActivity(intent);
        }
    }
}