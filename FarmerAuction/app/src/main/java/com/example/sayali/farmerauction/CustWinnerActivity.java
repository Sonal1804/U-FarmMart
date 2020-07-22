package com.example.sayali.farmerauction;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sayali.farmerauction.Model.WinCust;
import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustWinnerActivity extends AppCompatActivity {

    RecyclerView winList;
    DatabaseReference winRef;
    private Button SearchBtn;
    private com.rey.material.widget.EditText inputText;
    private String searchInput="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cust_winner);


        inputText = findViewById(R.id.search_product_name);
        SearchBtn = findViewById(R.id.search_btn);
        winRef = FirebaseDatabase.getInstance().getReference().child("WinnerList").child(Prevalent.currentOnlineUser.getPhone());

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = inputText.getText().toString();


                searchMethod(searchInput);
            }
        });


        winList = findViewById(R.id.winner_list);
        winList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void searchMethod(String searchInput) {

        FirebaseRecyclerOptions<WinCust> options =
                new FirebaseRecyclerOptions.Builder<WinCust>()
                        .setQuery(winRef.orderByChild("pname").startAt(searchInput).endAt(searchInput + "\uf8ff"), WinCust.class)
                        .build();

        FirebaseRecyclerAdapter<WinCust, CustWinnerActivity.AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<WinCust, CustWinnerActivity.AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CustWinnerActivity.AdminOrdersViewHolder holder, final int position, @NonNull final WinCust model)
                    {



                        holder.userName.setText("Dear " + model.getWinname()+" ,");
                        holder.winproname.setText("You won the bid of " + model.getPname());
                        holder.userTotalPrice.setText("Total Amount =  $" + model.getWinprice());
                        holder.farmname.setText("Farmer Name: " + model.getFarmname() + " from  " + model.getCity());


                        holder.btnpayment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                // String uID = getRef(position).getKey();

                                Intent intent = new Intent(CustWinnerActivity.this,PaymentActivity.class);

                                startActivity(intent);
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public CustWinnerActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.winner_layout, parent, false);
                        return new CustWinnerActivity.AdminOrdersViewHolder(view);
                    }
                };

        winList.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<WinCust> options =
                new FirebaseRecyclerOptions.Builder<WinCust>()
                        .setQuery(winRef, WinCust.class)
                        .build();

        FirebaseRecyclerAdapter<WinCust, CustWinnerActivity.AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<WinCust, CustWinnerActivity.AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CustWinnerActivity.AdminOrdersViewHolder holder, final int position, @NonNull final WinCust model)
                    {




                        holder.userName.setText("Dear " + model.getWinname()+" ,");
                        holder.winproname.setText("You won the bid of " + model.getPname());
                        holder.userTotalPrice.setText("Total Amount =  $" + model.getWinprice());
                        holder.farmname.setText("Farmer Name: " + model.getFarmname() + " from  " + model.getCity());


                        holder.btnpayment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                //String uID = getRef(position).getKey();
                                //Toast.makeText(CustWinnerActivity.this, ""+uID, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(CustWinnerActivity.this,PaymentActivity.class);

                                startActivity(intent);

                            }
                        });


                    }

                    @NonNull
                    @Override
                    public CustWinnerActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.winner_layout, parent, false);
                        return new CustWinnerActivity.AdminOrdersViewHolder(view);
                    }
                };

        winList.setAdapter(adapter);
        adapter.startListening();
    }




    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, farmname, userTotalPrice, winproname;
        public Button btnpayment;


        public AdminOrdersViewHolder(View itemView)
        {
            super(itemView);


            userName = itemView.findViewById(R.id.winner_Customer_name);
            farmname = itemView.findViewById(R.id.winner_name_address_city);
            userTotalPrice = itemView.findViewById(R.id.winner_product_price);
            winproname = itemView.findViewById(R.id.winner_product_name);

            btnpayment = itemView.findViewById(R.id.btn_payment);
        }
    }




   // private void RemoverOrder(String uID)
  //  {
      //  ordersRef.child(uID).removeValue();
  //  }
}

