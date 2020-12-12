package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * This is an activity class which operate the business-main screen.
 */
public class Business_HomeActivity extends AppCompatActivity implements View.OnClickListener {

    CardView inventory_bt;
    CardView new_doc_bt;
    CardView docs_bt;
    CardView info_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);

        initActivity();
    }

    /**
     * this function initialize the views in the screen
     */
    private void initActivity() {
        inventory_bt = (CardView) findViewById(R.id.inventoryManage);
        docs_bt = (CardView) findViewById(R.id.docs_bt);
        new_doc_bt = (CardView) findViewById(R.id.newDoc_bt);
        info_bt = (CardView) findViewById(R.id.info_bt);

        inventory_bt.setOnClickListener(this);
        docs_bt.setOnClickListener(this);
        new_doc_bt.setOnClickListener(this);
        info_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == inventory_bt.getId()){
            startActivity(new Intent(Business_HomeActivity.this, InventoryManageActivity.class));
        }
        if(v.getId() == docs_bt.getId()){}
        if(v.getId() == new_doc_bt.getId()){
            startActivity(new Intent(Business_HomeActivity.this, ChooseDocsActivity.class));
        }
        if(v.getId() == info_bt.getId()){}
    }
}