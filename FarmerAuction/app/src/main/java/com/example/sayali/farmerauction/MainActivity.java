package com.example.sayali.farmerauction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sayali.farmerauction.Model.Users;
import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button btnJoinNow, btnLogin;
    ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJoinNow = findViewById(R.id.btnJoinNow);
        btnLogin = findViewById(R.id.btnLogin);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(a);

            }
        });

        btnJoinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(a);


            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPassKey = Paper.book().read(Prevalent.UserPassKey);
        if (UserPhoneKey != "" && UserPassKey != "") {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPassKey)) {

                AllowAccessToAccount(UserPhoneKey, UserPassKey);
                loadingBar.setTitle("Already Logged In");
                loadingBar.setMessage("please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }

    private void AllowAccessToAccount(final String phone, final String pass) {

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Farmer").child(phone).exists()) {
                    Users userdata = dataSnapshot.child("Farmer").child(phone).getValue(Users.class);
                    if (userdata.getPhone().equals(phone)) {
                        if (userdata.getPass().equals(pass)) {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Logged in Successfully....", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = userdata;
                            startActivity(a);
                            finish();
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect...", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Account with this" + phone + "number does not exits", Toast.LENGTH_SHORT).show();
                }
                if (dataSnapshot.child("Customer").child(phone).exists()) {
                    Users userdata = dataSnapshot.child("Customer").child(phone).getValue(Users.class);
                    if (userdata.getPhone().equals(phone)) {
                        if (userdata.getPass().equals(pass)) {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Logged in Successfully....", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = userdata;
                            startActivity(a);
                            finish();
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect...", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Account with this" + phone + "number does not exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
