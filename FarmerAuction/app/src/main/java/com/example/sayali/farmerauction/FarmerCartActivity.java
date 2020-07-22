package com.example.sayali.farmerauction;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sayali.farmerauction.Model.CustomerProduct;
import com.example.sayali.farmerauction.Model.Products;
import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.example.sayali.farmerauction.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class FarmerCartActivity extends AppCompatActivity {

   /* ListView lvData;
    ArrayList<Products> b=new ArrayList<>();
    List<String> productid=new ArrayList();
    ArrayAdapter<Products> ad;
    String productrandomKey;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_cart);


       // productrandomKey=getIntent().getExtras().get("pid").toString();

        lvData=findViewById(R.id.lvData);
        myRef=FirebaseDatabase.getInstance().getReference().child("Products").child(Prevalent.currentOnlineUser.getPhone());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productid.clear();
                for(DataSnapshot child:dataSnapshot.getChildren()){


                    String sm=child.getKey();
                    //timelist.add(sm);
                    // Time time=postSnapshot.getValue(Time.class);
                    productid.add(sm);


                }
                 for (int i=0;i<productid.size();i++) {

                     myRef.child(productid.get(i)).addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             b.clear();
                             for (DataSnapshot d : dataSnapshot.getChildren()) {
                                 Products data = d.getValue(Products.class);
                                 b.add(data);

                             }
                             ad=new ArrayAdapter<Products>(FarmerCartActivity.this,android.R.layout.simple_list_item_1,b);
                             lvData.setAdapter(ad);
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });







                 }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }*/


    DatabaseReference productRef;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_cart);

        productRef= FirebaseDatabase.getInstance().getReference().child("Products").child(Prevalent.currentOnlineUser.getPhone());

        recyclerView=findViewById(R.id.scrollMenus);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        Paper.init(this);




    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CustomerProduct> options=new FirebaseRecyclerOptions.Builder<CustomerProduct>().setQuery(productRef,CustomerProduct.class).build();


        FirebaseRecyclerAdapter<CustomerProduct,ProductViewHolder> adapter=new FirebaseRecyclerAdapter<CustomerProduct, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final CustomerProduct model) {

                holder.product_name.setText(model.getName());
                holder.product_price.setText("Price : " +model.getValue());
                holder.product_quantity.setText("Quantity :"+model.getQuantity());
                holder.pedate.setText("Bid End Date :"+model.getBidenddate());
                holder.petime.setText("Bid End Time :"+model.getBidendtime());
                Picasso.get().load(model.getImage()).into(holder.product_image);

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}


















