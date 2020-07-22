package com.example.sayali.farmerauction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CustomerHelpActivity extends AppCompatActivity {

    TextView tvcusthelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_help);

        tvcusthelp=findViewById(R.id.tvcusthelp);
        String text="1.Click on the product which you want to bid.\n" +
                "2.Enter the bid amount. Make sure that bid amount should be greater than the inital amount which was mention on the previous page.\n" +
                "3.Your bid history will be store in the cart section. You can delete the product of the cart section for that simply click on the product and choose the delete option.\n" +
                "4.In the search section, you can search the product according to the product name.\n" +
                "5.Once the bidding time will up, the highest bidder would get the SMS from farmer and the payment process would be open for the winner in the winner section.\n" +
                "6.In the setting section, user can update his/her information like phone number, name, address and also can set profile image.\n" +
                "7.In the logout section, your account will be logout from this app.\n";

        tvcusthelp.setText(text);



    }
}
