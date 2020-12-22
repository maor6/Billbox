package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *  This is an activity class to choose the type of document to create
 */
public class ChooseDocsActivity extends AppCompatActivity implements View.OnClickListener {

    CardView receiptBtn;
    CardView warrantyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_docs);

        initActivity();
    }

    /**
     * this function initialize the views in the screen
     */
    private void initActivity() {
        receiptBtn = (CardView) findViewById(R.id.receiptChoose);
        receiptBtn.setOnClickListener(this);
        warrantyBtn = (CardView) findViewById(R.id.warrantyChoose);
        warrantyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == receiptBtn.getId()) {
            startActivity(new Intent(this, CreateBillActivity.class));
        }
        if(v.getId() == warrantyBtn.getId()){
            startActivity(new Intent(this, WarrantyActivity.class));
        }
    }
}