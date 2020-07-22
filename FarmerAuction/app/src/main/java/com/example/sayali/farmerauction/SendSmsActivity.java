package com.example.sayali.farmerauction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SendSmsActivity extends AppCompatActivity {

    TextView tvTitle, tvBody,tvUsername;
    Button btnSend;
    public String uID="",userphone="",pname="",name="",username="",farmname="",farmadd="";
    String token="",title="",body="", combine="",productprice="",saveCurrentDate="",saveCurrentTime="",productRandomKey="";
    DatabaseReference winnerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);






        tvTitle=findViewById(R.id.tvTitle);
        tvBody=findViewById(R.id.tvBody);
        tvUsername=findViewById(R.id.tvUsername);
        btnSend=findViewById(R.id.btnSend);

        uID = getIntent().getStringExtra("uid");
        userphone=getIntent().getStringExtra("userphone");
        pname=getIntent().getStringExtra("pname");
        username=getIntent().getStringExtra("username");
        productprice=getIntent().getStringExtra("productprice");


        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calendar.getTime());

        productRandomKey =saveCurrentDate + saveCurrentTime;

        winnerRef=FirebaseDatabase.getInstance().getReference().child("WinnerList").child(userphone);

        DatabaseReference tokenRef;
        tokenRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(userphone);

        tokenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    token = dataSnapshot.child("phoneOrder").getValue().toString();
                    name=dataSnapshot.child("name").getValue().toString();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference farmRef;
        farmRef = FirebaseDatabase.getInstance().getReference().child("Farmer").child(Prevalent.currentOnlineUser.getPhone());

        farmRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    farmname = dataSnapshot.child("name").getValue().toString();
                    farmadd=dataSnapshot.child("address").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });








        tvUsername.setText("Send to : "+ username);
        tvTitle.setText("Congrats !!!");
        tvBody.setText("You win the bid of "+pname+".");

        combine=tvTitle.getText().toString()+
                "\n"+tvBody.getText().toString();



        int res=ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);
        if(res== PackageManager.PERMISSION_GRANTED){
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(token,null,combine,null,null);
                    Toast.makeText(SendSmsActivity.this, "Message send Successfully", Toast.LENGTH_SHORT).show();

                    HashMap<String, Object> winMap = new HashMap<>();

                    winMap.put("pname", pname);
                    winMap.put("winname", username);
                    winMap.put("farmname", farmname);
                    winMap.put("city", farmadd);
                    winMap.put("winprice",productprice);
                    winMap.put("productRandomKey",productRandomKey);
                    // productMap.put("bidstarttime",pStartbt);
                    winnerRef.child(productRandomKey).updateChildren(winMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Intent a= new Intent(SendSmsActivity.this,FarmerOrdersActivity.class);
                                startActivity(a);
                                finish();

                               // Toast.makeText(SendSmsActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SendSmsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Intent a = new Intent(SendSmsActivity.this, FarmerHomeActivity.class);
                    // a.putExtra("pid",productRandomKey);
                    startActivity(a);

                    finish();


                }
            });
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},123);
        }




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(token,null,combine,null,null);
                    Toast.makeText(SendSmsActivity.this, "Message send Succcessfully", Toast.LENGTH_SHORT).show();

                    HashMap<String, Object> winMap = new HashMap<>();

                    winMap.put("pname", pname);
                    winMap.put("winname", username);
                    winMap.put("farmname", farmname);
                    winMap.put("city", farmadd);
                    winMap.put("winprice",productprice);
                    winMap.put("productRandomKey",productRandomKey);
                    // productMap.put("bidstarttime",pStartbt);
                    winnerRef.child(productRandomKey).updateChildren(winMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent a= new Intent(SendSmsActivity.this,FarmerOrdersActivity.class);
                                startActivity(a);
                                finish();


                              //  Toast.makeText(SendSmsActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SendSmsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Intent a = new Intent(SendSmsActivity.this, FarmerHomeActivity.class);
                    // a.putExtra("pid",productRandomKey);
                    startActivity(a);

                    finish();



                }

            });
        }
    }
}
