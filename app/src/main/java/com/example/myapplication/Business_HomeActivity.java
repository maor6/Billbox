package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


/**
 * This is an activity class which operate the business screen.
 */
public class Business_HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView inventory_bt;
    private ImageView new_doc_bt;
    private ImageView docs_bt;
    private ImageView info_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);

        inventory_bt = (ImageView) findViewById(R.id.inventory_bt);
        docs_bt = (ImageView) findViewById(R.id.docs_bt);
        new_doc_bt = (ImageView) findViewById(R.id.newDoc_bt);
        info_bt = (ImageView) findViewById(R.id.info_bt);

        inventory_bt.setOnClickListener(this);
        docs_bt.setOnClickListener(this);
        new_doc_bt.setOnClickListener(this);
        info_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == inventory_bt.getId()){}
        if(v.getId() == docs_bt.getId()){}
        if(v.getId() == new_doc_bt.getId()){
            startActivity(new Intent(Business_HomeActivity.this, CreateDocsActivity.class));
        }
        if(v.getId() == info_bt.getId()){}

    }
}