package com.example.sayali.farmerauction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sayali.farmerauction.Model.Users;
import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginPhone,etLoginPass;
    Button btnLoginLog;
    ProgressDialog loadingBar;
    CheckBox cbRememberMe;
    TextView tvAdminPanelLink, tvNotAdminPanelLink,tvadmin;
    String parentDbName="Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginPhone = findViewById(R.id.etLoginPhone);
        etLoginPass = findViewById(R.id.etLoginPass);
        btnLoginLog = findViewById(R.id.btnLoginLog);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        tvAdminPanelLink = findViewById(R.id.tvAdminPanelLink);
        tvNotAdminPanelLink = findViewById(R.id.tvNotAdminPanelLink);
     //   tvadmin=findViewById(R.id.tvadmin);
        Paper.init(this);
        loadingBar = new ProgressDialog(this);

        btnLoginLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

     /*   tvadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(LoginActivity.this,AdminHomeActivity.class);
                startActivity(a);
            }
        });*/

        tvAdminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoginLog.setText("Farmer Login");
                tvAdminPanelLink.setVisibility(View.INVISIBLE);
                tvNotAdminPanelLink.setVisibility(View.VISIBLE);
                cbRememberMe.setVisibility(View.INVISIBLE);
                parentDbName = "Farmer";
            }
        });

        tvNotAdminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoginLog.setText("Customer Login");
                tvAdminPanelLink.setVisibility(View.VISIBLE);
                cbRememberMe.setVisibility(View.VISIBLE);
                tvNotAdminPanelLink.setVisibility(View.INVISIBLE);
                parentDbName = "Customer";

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

        if(cbRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPassKey,pass);

        }

        final DatabaseReference rootRef;
        rootRef=FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users userdata=dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if(userdata.getPhone().equals(phone))
                    {
                        if(userdata.getPass().equals(pass))
                        {
                            if(parentDbName.equals("Farmer"))
                            {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Logged in Successfully....", Toast.LENGTH_SHORT).show();
                                Intent a= new Intent(LoginActivity.this,FarmerHomeActivity.class);
                                Prevalent.currentOnlineUser=userdata;
                                startActivity(a);

                        }else if (parentDbName.equals("Customer"))
                            {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Logged in Successfully....", Toast.LENGTH_SHORT).show();
                                Intent a= new Intent(LoginActivity.this,HomeActivity.class);
                                Prevalent.currentOnlineUser=userdata;
                                startActivity(a);
                            }

                            }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect...", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Account with this"+ phone +"number does not exits", Toast.LENGTH_SHORT).show();
                }
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
