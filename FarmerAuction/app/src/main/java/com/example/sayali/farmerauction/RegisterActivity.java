package com.example.sayali.farmerauction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegisterName,etRegisterPhone,etRegisterPass,etadd;
    Button btnRegisterCreate;
    ProgressDialog loadingBar;
    TextView tvlogin;
    RadioGroup rgMember;
    public String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etRegisterName=findViewById(R.id.etRegisterName);
        etRegisterPhone=findViewById(R.id.etRegisterPhone);
        etRegisterPass=findViewById(R.id.etRegisterPass);
        btnRegisterCreate=findViewById(R.id.btnRegisterCreate);
        rgMember=findViewById(R.id.rgMember);
        etadd=findViewById(R.id.etadd);
        loadingBar= new ProgressDialog(this);
        tvlogin=findViewById(R.id.tvLogin);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {

                            token = task.getResult().getToken();
                            //  saveToken(token);
                        } else {

                        }
                    }
                });


        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(a);
                finish();
            }
        });


        btnRegisterCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();

                    }
                });
            }

    private void createAccount() {
        String name=etRegisterName.getText().toString();
        String phone=etRegisterPhone.getText().toString();
        String pass=etRegisterPass.getText().toString();
        String add=etadd.getText().toString();
        if(TextUtils.isEmpty(name))
        {
            etRegisterName.setError("Name field is Empty");
        }
        else if(TextUtils.isEmpty(add))
        {
            etadd.setError("Address field is Empty");
        }
        else if (TextUtils.isEmpty(phone) || phone.length()>10 || phone.length()<10)
        {
            etRegisterPhone.setError("Email field is Empty");
        }else if(TextUtils.isEmpty(pass))
        {
            etRegisterPass.setError("Password field is Empty");
        }

        else
        {
            loadingBar.setTitle("create Account");
            loadingBar.setMessage("please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatePhoneNumber(name,phone,pass,add);
        }

    }

    private void validatePhoneNumber(final String name, final String phone, final String pass,final String add) {

        final DatabaseReference rootRef;
        rootRef=FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override


            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int id=rgMember.getCheckedRadioButtonId();
                RadioButton radioButton=findViewById(id);
                String member=radioButton.getText().toString();
                if(!(dataSnapshot.child(member).child(phone).exists()))
                {
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("pass",pass);
                    userdataMap.put("name",name);
                    userdataMap.put("phoneOrder",phone);
                    userdataMap.put("address",add);
                   // userdataMap.put("token",token);
                    rootRef.child(member).child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent a=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(a);
                            }
                            else{
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network Error: Please try again...", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                else{
                    Toast.makeText(RegisterActivity.this, "This"+phone +"already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again...", Toast.LENGTH_SHORT).show();
                    Intent a= new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(a);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}

