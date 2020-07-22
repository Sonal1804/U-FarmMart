package com.example.sayali.farmerauction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FarmerHelpActivity extends AppCompatActivity {

    TextView tvfarmhelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_help);


        tvfarmhelp=findViewById(R.id.tvfarmhelp);

        String text="1.Choose the suitable category and click on that. Fill out the details and submit the product. Once the product uploaded successfully you will get the toast message.\n" +
                "2.You can check your product uploaded history in the cart section.\n" +
                "3.When the time will up of your particular uploaded product, you will get the notification. Note- for notification display, you should have to login.After successful login you have to press the lock button. If there would any product whose bid time is up, so by doing these method you will get the notification.\n" +
                "4.So after that, you will have to go to the order section. Search that particular product and check whose bid price is highest. Click on that particular product and press the send button. So that the winner will get the SMS and the payment process will be open for the winner.\n" +
                "5.In the setting section, user can update his/her information like phone number, name, address and also can set profile image.\n" +
                "6.In the logout section, your account will be logout from this app.\n";

        tvfarmhelp.setText(text);

    }
}
