package com.example.sayali.farmerauction;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sayali.farmerauction.Model.CustomerProduct;
import com.example.sayali.farmerauction.Model.Products;
import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.example.sayali.farmerauction.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference productRef;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        productRef= FirebaseDatabase.getInstance().getReference().child("CustomerProduct");

        recyclerView=findViewById(R.id.scrollMenus);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        Paper.init(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(HomeActivity.this,CartActivity.class);
                startActivity(a);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        TextView usernameTextView=headerView.findViewById(R.id.tvUsernameHeader);
        CircleImageView profileImageView=headerView.findViewById(R.id.ivImageHeader);

        usernameTextView.setText(Prevalent.currentOnlineUser.getName());
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CustomerProduct> options=new FirebaseRecyclerOptions.Builder<CustomerProduct>().
                setQuery(productRef,CustomerProduct.class).build();


        FirebaseRecyclerAdapter<CustomerProduct,ProductViewHolder> adapter=new FirebaseRecyclerAdapter<CustomerProduct, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final CustomerProduct model) {

                holder.product_name.setText(model.getName());
                holder.fname.setText("Farmer Name :"+model.getUsername() +" from "+ model.getUseradd());

                holder.pedate.setText("Bid End Date :"+model.getBidenddate());
                holder.petime.setText("Bid End Time :"+model.getBidendtime());
                holder.product_price.setText("Price : " +model.getValue());
                holder.product_quantity.setText("Quantity :"+model.getQuantity());
                Picasso.get().load(model.getImage()).into(holder.product_image);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a=new Intent(HomeActivity.this,ProductDetailsActivity.class);
                        a.putExtra("pid",model.getPid());
                        a.putExtra("phone",model.getPhone());
                        a.putExtra("value",model.getValue());
                        startActivity(a);

                    }
                });
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navCart) {
            // Handle the camera action
            Intent a=new Intent(HomeActivity.this,CartActivity.class);
            startActivity(a);


        } else if (id == R.id.navSearch) {
            Intent a=new Intent(HomeActivity.this,SearchProductsActivity.class);
            startActivity(a);



        } else if (id == R.id.navWinner) {
            Intent a=new Intent(HomeActivity.this,CustWinnerActivity.class);
            startActivity(a);



        } else if (id == R.id.navHelp) {
            Intent a=new Intent(HomeActivity.this,CustomerHelpActivity.class);
            startActivity(a);



        } else if (id == R.id.navSettings) {

            Intent a=new Intent(HomeActivity.this,SettinsActivity.class);
            startActivity(a);


        } else if (id == R.id.navLogout) {

            Paper.book().destroy();
            Intent a= new Intent(HomeActivity.this,MainActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(a);
            finish();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
