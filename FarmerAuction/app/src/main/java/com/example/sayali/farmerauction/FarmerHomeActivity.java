package com.example.sayali.farmerauction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sayali.farmerauction.Model.FarmOrder;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class FarmerHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView cereals ,vegetables;
    ImageView fruits,pulses;
    ImageView spices;
    String name="";
    DatabaseReference productsRef,timedateref,productdel;
    List<String> timelist=new ArrayList();
    String saveCurrentDat,time24;
    private static final String CHANNEL_ID ="Simplified_Coding";
    private static final String CHANNEL_NAME="Simplified Coding";
    private static final String CHANNEL_DESC ="Simplified Coding Notification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        cereals=findViewById(R.id.cereals);
        vegetables=findViewById(R.id.vegetables);
        fruits=findViewById(R.id.fruits);
        pulses=findViewById(R.id.pulses);
        spices=findViewById(R.id.spices);

        timedateref=FirebaseDatabase.getInstance().getReference().child("Time").child(Prevalent.currentOnlineUser.getPhone());
        productdel=FirebaseDatabase.getInstance().getReference().child("CustomerProduct");

        cereals.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent a=new Intent(FarmerHomeActivity.this,AdminAddNPActivity.class);
        a.putExtra("category","cereals");
        startActivity(a);
        }
        });
        vegetables.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent a=new Intent(FarmerHomeActivity.this,AdminAddNPActivity.class);
        a.putExtra("category","vegetables");
        startActivity(a);
        }
        });
        fruits.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent a=new Intent(FarmerHomeActivity.this,AdminAddNPActivity.class);
        a.putExtra("category","fruits");
        startActivity(a);
        }
        });
        pulses.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent a=new Intent(FarmerHomeActivity.this,AdminAddNPActivity.class);
        a.putExtra("category","pulses");
        startActivity(a);
        }
        });
        spices.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent a=new Intent(FarmerHomeActivity.this,AdminAddNPActivity.class);
        a.putExtra("category","spices");
        startActivity(a);
        }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent a=new Intent(FarmerHomeActivity.this,FarmerCartActivity.class);
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


    private void displayNotification()
    {

        Intent intent = new Intent(FarmerHomeActivity.this, FarmerOrdersActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("U-FARM MART...")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setContentText("The time of "+name+" bid is up")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationMgr = NotificationManagerCompat.from(this);
        notificationMgr.notify(1,builder.build());


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
        getMenuInflater().inflate(R.menu.farmer_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.action_settings) {
          //  return true;
       // }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navCart) {
            // Handle the camera action
          Intent a=new Intent(FarmerHomeActivity.this,FarmerCartActivity.class);
           startActivity(a);

        } else if (id == R.id.navOrders) {

            Intent a=new Intent(FarmerHomeActivity.this,FarmerOrdersActivity.class);
            startActivity(a);

        } else if (id == R.id.navHelp) {
            Intent a = new Intent(FarmerHomeActivity.this, FarmerHelpActivity.class);
            startActivity(a);
        

        } else if (id == R.id.navSettings) {

            Intent a=new Intent(FarmerHomeActivity.this,SettingsFarmerActivity.class);
            startActivity(a);

        } else if (id == R.id.navLogout) {

            Paper.book().destroy();
            Intent a= new Intent(FarmerHomeActivity.this,MainActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(a);
            finish();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        productdel.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {


                    String sm=child.getKey();
                    timelist.add(sm);
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if (timelist.size()>0) {

            for (int i = 0; i < timelist.size(); i++) {


                Calendar calenda=Calendar.getInstance();

                SimpleDateFormat currentDat=new SimpleDateFormat("yyyy-MM-dd")  ;
                saveCurrentDat =currentDat.format(calenda.getTime());



                try {
                    String now = new SimpleDateFormat("hh:mm aa").format(new java.util.Date().getTime());

                    SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
                    SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
                    time24 = outFormat.format(inFormat.parse(now));


                } catch (Exception e) {
                    Toast.makeText(FarmerHomeActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }

                final String combinedatetime= saveCurrentDat+"T"+time24;


                int ans=timelist.get(i).compareTo(combinedatetime);
                if ( ans<=0)


                {
                    DatabaseReference tokenRef;
                    tokenRef = FirebaseDatabase.getInstance().getReference().child("Products").child(Prevalent.currentOnlineUser.getPhone())
                    .child(timelist.get(i));

                    tokenRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                name = dataSnapshot.child("name").getValue().toString();



                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    productdel.child(timelist.get(i))
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {

                                       // Toast.makeText(FarmerHomeActivity.this, "Item removed successfully.", Toast.LENGTH_SHORT).show();
                                        displayNotification();

                                    }
                                }
                            });



                  //  Toast.makeText(FarmerHomeActivity.this, "woahhhhhhhhhhhhhhhhhhh", Toast.LENGTH_LONG).show();
                }
            }
        }
        timelist.clear();
     //   Toast.makeText(FarmerHomeActivity.this, ""+ timelist, Toast.LENGTH_SHORT).show();
    }
}




