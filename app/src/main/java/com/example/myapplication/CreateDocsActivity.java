package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateDocsActivity extends AppCompatActivity implements View.OnClickListener {

    Button receiptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_docs);

        receiptBtn = (Button) findViewById(R.id.receipt_btn);
        receiptBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == receiptBtn.getId()) {
            startActivity(new Intent(this, CreateBillActivity.class));
        }
    }
}