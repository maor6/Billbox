package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

/**
 * This is an activity class which operate the business-main screen.
 */
public class Business_HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    CardView inventory_bt;
    CardView new_doc_bt;
    CardView docs_bt;
    CardView info_bt;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);

        initActivity();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.NavigationDrawerOpen,
                R.string.closeNavDrawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * this function initialize the views in the screen
     */
    private void initActivity() {
        toolbar = findViewById(R.id.business_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.business_nav_view);
        inventory_bt = (CardView) findViewById(R.id.inventoryManage);
        docs_bt = (CardView) findViewById(R.id.docs_bt);
        new_doc_bt = (CardView) findViewById(R.id.newDoc_bt);
        info_bt = (CardView) findViewById(R.id.info_bt);
        inventory_bt.setOnClickListener(this);
        docs_bt.setOnClickListener(this);
        new_doc_bt.setOnClickListener(this);
        info_bt.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == inventory_bt.getId()){
            startActivity(new Intent(Business_HomeActivity.this, InventoryManageActivity.class));
        }
        if(v.getId() == docs_bt.getId()){
            startActivity(new Intent(Business_HomeActivity.this, AllDocumentsBusinessActivity.class));
        }
        if(v.getId() == new_doc_bt.getId()){
            startActivity(new Intent(Business_HomeActivity.this, ChooseDocsActivity.class));
        }
        if(v.getId() == info_bt.getId()){
            startActivity(new Intent(Business_HomeActivity.this, BusinessProfileActivity.class));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchDocumentBusinessMenu:
                startActivity(new Intent(Business_HomeActivity.this, AllDocumentsBusinessActivity.class));
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}