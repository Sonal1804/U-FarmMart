package com.example.sayali.farmerauction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class AdminHomeActivity extends AppCompatActivity {

    EditText etLoginPhone,etLoginPass;
    Button btnLoginLog;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        etLoginPhone = findViewById(R.id.etLogiPhone);
        etLoginPass = findViewById(R.id.etLogiPass);
        btnLoginLog = findViewById(R.id.btnLogiLog);
        loadingBar = new ProgressDialog(this);

        btnLoginLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {

        String phone=etLoginPhone.getText().toString();
        String pass=etLoginPass.getText().toString();
        if(TextUtils.isEmpty(phone)|| phone.length()>10 || phone.length()<10)
        {
            etLoginPhone.setError("Please enter the phone number");
        }else if (TextUtils.isEmpty(pass))
        {
            etLoginPass.setError("Please enter the password");
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,pass);
        }
    }


    private void AllowAccessToAccount(final String phone, final String pass) {


        final DatabaseReference rootRef;
        rootRef=FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Admins").child(phone).exists())
                {
                    Users userdata=dataSnapshot.child("Admins").child(phone).getValue(Users.class);
                    if(userdata.getPhone().equals(phone))
                    {
                        if(userdata.getPass().equals(pass))
                        {

                                loadingBar.dismiss();
                                Toast.makeText(AdminHomeActivity.this, "Logged in Successfully....", Toast.LENGTH_SHORT).show();
                                Intent a= new Intent(AdminHomeActivity.this,AdminButtonsActivity.class);
                                Prevalent.currentOnlineUser=userdata;
                                startActivity(a);

                            }

                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(AdminHomeActivity.this, "Password is incorrect...", Toast.LENGTH_SHORT).show();
                        }
                    }

                else {
                    loadingBar.dismiss();
                    Toast.makeText(AdminHomeActivity.this, "Account with this"+ phone +"number does not exits", Toast.LENGTH_SHORT).show();
                }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}





    }

