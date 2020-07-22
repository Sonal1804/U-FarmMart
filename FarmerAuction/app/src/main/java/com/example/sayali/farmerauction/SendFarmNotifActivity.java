package com.example.sayali.farmerauction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sayali.farmerauction.Interface.Api;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendFarmNotifActivity extends AppCompatActivity {

    TextView tvTitle, tvBody,tvUsername;
    Button btnSend;
    private String uID="",userphone="",pname="",username="";
    String token="",title="",body="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_farm_notif);


        tvTitle=findViewById(R.id.tvTitle);
        tvBody=findViewById(R.id.tvBody);
        tvUsername=findViewById(R.id.tvUsername);
        btnSend=findViewById(R.id.btnSend);

        uID = getIntent().getStringExtra("uid");
        userphone=getIntent().getStringExtra("userphone");
        pname=getIntent().getStringExtra("pname");
        username=getIntent().getStringExtra("username");
        tvUsername.setText("Send to : "+ userphone);
        tvTitle.setText("Congrats !!!");
        tvBody.setText("You win the bid of "+pname+".");


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNofiy();
            }
        });
    }

    private void sendNofiy() {

        title=tvTitle.getText().toString();
        body=tvBody.getText().toString();

        CheckOrderState();

       // username.getToken();

    }


    private void CheckOrderState()


    {
        DatabaseReference tokenRef;
        tokenRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(userphone);

        tokenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                   token = dataSnapshot.child("token").getValue().toString();



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://farmeracution.firebaseapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<ResponseBody> call = api.sendNotification(token, title, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Toast.makeText(SendFarmNotifActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}

