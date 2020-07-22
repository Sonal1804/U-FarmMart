package com.example.sayali.farmerauction;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sayali.farmerauction.Model.CustomerProduct;
import com.example.sayali.farmerauction.Model.Products;
import com.example.sayali.farmerauction.Model.Value;
import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    private Button addToCartButton;
    private ImageView productImage;
    TextView product_qantity_details;
    EditText product_bid;
    private TextView productName, pedate, petime;
    public String productID = "", productphone = "",productValue="",username="",useradd="", state = "Normal";
    public static int already;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");
        productphone = getIntent().getStringExtra("phone");
        productValue = getIntent().getStringExtra("value");






        product_qantity_details = findViewById(R.id.product_qantity_details);
        product_bid = findViewById(R.id.product_bid);
        pedate = findViewById(R.id.pedate);
        petime = findViewById(R.id.petime);
        addToCartButton = (Button) findViewById(R.id.pd_add_to_cart_button);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        productName = (TextView) findViewById(R.id.product_name_details);


        DatabaseReference tokenRef;
        tokenRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(Prevalent.currentOnlineUser.getPhone());

        tokenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    username = dataSnapshot.child("name").getValue().toString();
                    useradd=dataSnapshot.child("address").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        getProductDetails(productID);


        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (state.equals("Order Placed") || state.equals("Order Shipped"))
                {
                    Toast.makeText(ProductDetailsActivity.this, "you can add purchase more products, once your order is shipped or confirmed.", Toast.LENGTH_LONG).show();
                }*/

                {

                    addingToCartList();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        //CheckOrderState();
    }


    private void addingToCartList() {

        String currentValue = product_bid.getText().toString();

        already = Integer.parseInt(productValue);
        final int newone = Integer.parseInt(currentValue);

        if (newone > already)
        {

            String saveCurrentTime, saveCurrentDate;



                                    Calendar calForDate = Calendar.getInstance();
                                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                                    saveCurrentDate = currentDate.format(calForDate.getTime());

                                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                                    saveCurrentTime = currentTime.format(calForDate.getTime());

                                    final String save=saveCurrentDate+saveCurrentTime;

                                    final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");


                                    final HashMap<String, Object> cartMap = new HashMap<>();
                                    cartMap.put("pid", productphone);
                                    cartMap.put("pname", productName.getText().toString());
                                    cartMap.put("price", product_bid.getText().toString());
                                    cartMap.put("date", saveCurrentDate);
                                    cartMap.put("time", saveCurrentTime);
                                    cartMap.put("quantity", product_qantity_details.getText().toString());
                                    cartMap.put("discount", "");
                                    cartMap.put("productID", productID);
                                    cartMap.put("userphone",Prevalent.currentOnlineUser.getPhone());
                                    cartMap.put("saveCurrentdt",save);
                                    cartMap.put("name",username);
                                    cartMap.put("address",useradd);



                                    cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                                            .child("CustomerProduct").child(productID)
                                            .updateChildren(cartMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                       /* cartListRef.child("Farmer View").child(productphone)
                                                                .child("CustomerProduct").child(Prevalent.currentOnlineUser.getPhone())
                                                                .child(productID)*/
                                                        cartListRef.child("FarmerView").child(productphone).child(save)
                                                                .updateChildren(cartMap)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Toast.makeText(ProductDetailsActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();




                                                                            Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                                                            startActivity(intent);
                                                                            finish();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            });

                                }





        else

        {
            Toast.makeText(this, "Bidding Amount is less", Toast.LENGTH_LONG).show();

        }
    }




    private void getProductDetails(String productID)
    {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("CustomerProduct").child(productID);


        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    CustomerProduct product = dataSnapshot.getValue(CustomerProduct.class);

                    productName.setText(product.getName());
                    Picasso.get().load(product.getImage()).into(productImage);
                    pedate.setText(product.getBidenddate());
                    product_qantity_details.setText(product.getQuantity());
                    petime.setText(product.getBidendtime());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

  /*  private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("state").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                        state = "Order Shipped";
                    }
                    else if(shippingState.equals("not shipped"))
                    {
                        state = "Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}
