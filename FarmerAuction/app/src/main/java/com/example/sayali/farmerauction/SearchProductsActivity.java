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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import com.example.sayali.farmerauction.Model.CustomerProduct;
import com.example.sayali.farmerauction.Model.Products;
import com.example.sayali.farmerauction.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchProductsActivity extends AppCompatActivity implements Filterable {

    private Button SearchBtn;
    private com.rey.material.widget.EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;
    FirebaseRecyclerAdapter<CustomerProduct, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        inputText = findViewById(R.id.search_product_name);
        SearchBtn = findViewById(R.id.search_btn);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   SearchInput = inputText.getText().toString();

                 onStart();
            }
        });
    }

         @Override
        protected void onStart()
         {
         super.onStart();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("CustomerProduct");

        FirebaseRecyclerOptions<CustomerProduct> options =
                new FirebaseRecyclerOptions.Builder<CustomerProduct>()
                        .setQuery(reference.orderByChild("name").startAt(SearchInput).endAt(SearchInput + "\uf8ff"), CustomerProduct.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<CustomerProduct, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final CustomerProduct model) {
                        holder.product_name.setText(model.getName());
                        holder.fname.setText("Farmer Name :"+model.getUsername() +" from "+ model.getUseradd());
                        holder.pedate.setText("Bid End Date :" + model.getBidenddate());
                        holder.petime.setText("Bid End Time :" + model.getBidendtime());
                        holder.product_price.setText("Price : " + model.getValue());
                        holder.product_quantity.setText("Quantity :" + model.getQuantity());
                        Picasso.get().load(model.getImage()).into(holder.product_image);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent a = new Intent(SearchProductsActivity.this, ProductDetailsActivity.class);
                                a.putExtra("pid", model.getPid());
                                a.putExtra("phone", model.getPhone());
                                a.putExtra("value", model.getValue());
                                startActivity(a);
                                finish();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        searchList.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
