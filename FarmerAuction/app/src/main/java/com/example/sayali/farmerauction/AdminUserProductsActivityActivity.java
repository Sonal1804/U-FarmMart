package com.example.sayali.farmerauction;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sayali.farmerauction.Model.Cart;
import com.example.sayali.farmerauction.Model.FarmOrder;
import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.example.sayali.farmerauction.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProductsActivityActivity extends AppCompatActivity {

    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;

    private String userID = "", userphone="",save="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products_activity);
        userID = getIntent().getStringExtra("uid");
        userphone=getIntent().getStringExtra("userphone");
        save=getIntent().getStringExtra("save");


                productsList = findViewById(R.id.products_list);
                productsList.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this);
                productsList.setLayoutManager(layoutManager);


                cartListRef = FirebaseDatabase.getInstance().getReference()
                        .child("Cart List").child("FarmerView").
                                child(Prevalent.currentOnlineUser.getPhone()).child(save);
            }


            @Override
            protected void onStart()
            {
                super.onStart();


                FirebaseRecyclerOptions<FarmOrder> options =
                        new FirebaseRecyclerOptions.Builder<FarmOrder>()
                                .setQuery(cartListRef, FarmOrder.class)
                                .build();

                FirebaseRecyclerAdapter<FarmOrder, CartViewHolder> adapter = new FirebaseRecyclerAdapter<FarmOrder, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull FarmOrder model)
                    {
                       holder.txtProductQuantity.setText("Quantity = " + model.getPrice());
                        holder.txtProductPrice.setText("Price " + model.getPrice() + "$");
                        holder.txtProductName.setText(model.getPname());
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

                productsList.setAdapter(adapter);
                adapter.startListening();
            }
        }


