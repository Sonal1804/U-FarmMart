package com.example.sayali.farmerauction;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sayali.farmerauction.Model.AdminOrders;
import com.example.sayali.farmerauction.Model.FarmOrder;
import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FarmerOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    private Button SearchBtn;
    private com.rey.material.widget.EditText inputText;
    private String searchInput="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_farmer_orders);


        inputText = findViewById(R.id.search_product_name);
        SearchBtn = findViewById(R.id.search_btn);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("FarmerView")
        .child(Prevalent.currentOnlineUser.getPhone());



                SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               searchInput = inputText.getText().toString();


                searchMethod(searchInput);
            }
        });


        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));



    }

    private void searchMethod(String searchInput) {

        FirebaseRecyclerOptions<FarmOrder> options =
                new FirebaseRecyclerOptions.Builder<FarmOrder>()
                        .setQuery(ordersRef.orderByChild("pname").startAt(searchInput).endAt(searchInput + "\uf8ff"), FarmOrder.class)
                        .build();

        FirebaseRecyclerAdapter<FarmOrder, FarmerOrdersActivity.AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<FarmOrder, FarmerOrdersActivity.AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FarmerOrdersActivity.AdminOrdersViewHolder holder, final int position, @NonNull final FarmOrder model)
                    {



                        holder.userName.setText("Name: " + model.getName() );
                        holder.userPhoneNumber.setText("Phone no: " + model.getUserphone());
                        holder.userTotalPrice.setText("Total Amount =  $" + model.getPrice());
                         holder.userDateTime.setText("Order of: " + model.getPname() + "  " );
                         holder.userShippingAddress.setText("Shipping Address: " + model.getAddress() );

                        holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(FarmerOrdersActivity.this,SendSmsActivity.class);
                                intent.putExtra("uid", uID);
                                intent.putExtra("username",model.getName());
                                intent.putExtra("save",model.getSaveCurrentdt());
                                intent.putExtra("userphone",model.getUserphone());
                                intent.putExtra("pname",model.getPname());
                                intent.putExtra("productprice",model.getPrice());
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(FarmerOrdersActivity.this);
                                builder.setTitle("Are you sure want to delete ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            String uID = getRef(position).getKey();

                                            RemoverOrder(uID);
                                        }
                                        else
                                        {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public FarmerOrdersActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new FarmerOrdersActivity.AdminOrdersViewHolder(view);
                    }
                };

        ordersList.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<FarmOrder> options =
                new FirebaseRecyclerOptions.Builder<FarmOrder>()
                        .setQuery(ordersRef, FarmOrder.class)
                        .build();

        FirebaseRecyclerAdapter<FarmOrder, FarmerOrdersActivity.AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<FarmOrder, FarmerOrdersActivity.AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FarmerOrdersActivity.AdminOrdersViewHolder holder, final int position, @NonNull final FarmOrder model)
                    {





                        holder.userName.setText("Name: " + model.getName());
                        holder.userPhoneNumber.setText("Phone: " + model.getUserphone());
                        holder.userTotalPrice.setText("Total Amount =  $" + model.getPrice());
                        holder.userDateTime.setText("Order of: " + model.getPname() + "  " );
                        holder.userShippingAddress.setText("Shipping Address: " + model.getAddress()  );

                        holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();
                                Toast.makeText(FarmerOrdersActivity.this, ""+uID, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(FarmerOrdersActivity.this,SendSmsActivity.class);
                                intent.putExtra("uid", uID);
                                intent.putExtra("username",model.getName());
                                intent.putExtra("userphone",model.getUserphone());
                                intent.putExtra("pname",model.getPname());
                                intent.putExtra("productprice",model.getPrice());
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(FarmerOrdersActivity.this);
                                builder.setTitle("Are you sure want to delete ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            String uID = getRef(position).getKey();

                                            RemoverOrder(uID);
                                        }
                                        else
                                        {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public FarmerOrdersActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new FarmerOrdersActivity.AdminOrdersViewHolder(view);
                    }
                };

        ordersList.setAdapter(adapter);
        adapter.startListening();
    }




    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        public Button ShowOrdersBtn;


        public AdminOrdersViewHolder(View itemView)
        {
            super(itemView);


            userName = itemView.findViewById(R.id.order_product_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_name);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            ShowOrdersBtn = itemView.findViewById(R.id.show_all_products_btn);
        }
    }




    private void RemoverOrder(String uID)
    {
        ordersRef.child(uID).removeValue();
    }
}

