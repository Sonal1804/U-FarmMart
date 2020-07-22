package com.example.sayali.farmerauction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class AdminButtonsActivity extends AppCompatActivity {

    Button btnALogout, btnCheckOrders;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_buttons);

        btnALogout = findViewById(R.id.btnALogout);
        btnCheckOrders = findViewById(R.id.btnCheckOrders);
        loadingBar = new ProgressDialog(this);

        btnALogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(AdminButtonsActivity.this,MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                finish();
            }
        });

        btnCheckOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(AdminButtonsActivity.this, AdminProductApprovedActivity.class);
                startActivity(a);
            }
        });

    }
}
