package com.example.sayali.farmerauction;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sayali.farmerauction.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.zone.ZoneOffsetTransitionRule;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AdminAddNPActivity extends AppCompatActivity {

    String categoryName,combinedatetime,time24, pDate,pName,pQuantity,pValue,pEndbt,saveCurrentDat, saveCurrentDate,saveCurrentTime,productRandomKey ,timeRandomKey,downloadImgUri, pTime;
    ImageView selectProductImage;
    EditText productName, productValue, productQuantity;
    TextView productEbt,productTime, productDate;
    Button addNewProduct;
    Uri imageUri;
    private static final int galleryPic=1;
    StorageReference productImagesRef;
    DatabaseReference productsRef,timedateref,customerproductRef;
    ProgressDialog loadingBar;
    Calendar mCurrentDate;
    int day,month,year;
    String username="",useradd="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_np);

        //categoryName=getIntent().getExtras().get("category").toString();

        selectProductImage=findViewById(R.id.selectProductImage);
        productName=findViewById(R.id.productName);
        productQuantity=findViewById(R.id.productQuantity);
        productEbt=findViewById(R.id.productEbt);
        productDate=findViewById(R.id.productDate);
        productTime=findViewById(R.id.productTime);
        productValue=findViewById(R.id.productValue);
        addNewProduct=findViewById(R.id.addNewProduct);
        productImagesRef=FirebaseStorage.getInstance().getReference().child("Product Images");
        productsRef=FirebaseDatabase.getInstance().getReference().child("Products").child(Prevalent.currentOnlineUser.getPhone());
        timedateref=FirebaseDatabase.getInstance().getReference().child("Time").child(Prevalent.currentOnlineUser.getPhone());
        customerproductRef=FirebaseDatabase.getInstance().getReference().child("CustomerProduct");
        loadingBar=new ProgressDialog(this);



        DatabaseReference tokenRef;
        tokenRef = FirebaseDatabase.getInstance().getReference().child("Farmer").child(Prevalent.currentOnlineUser.getPhone());

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


        mCurrentDate=Calendar.getInstance();

        final int hour=mCurrentDate.get(Calendar.HOUR);
        final int minute=mCurrentDate.get(Calendar.MINUTE);
        final Context context=this;
        final String[] d = new String[1];
        final String[] t = new String[1];

        day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month=mCurrentDate.get(Calendar.MONTH);
        year=mCurrentDate.get(Calendar.YEAR);

        month+=1;
        // d[0] =day+"/"+month+"/"+year;

        productEbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        t[0] =hourOfDay+":"+minute;
                        productTime.setText(t[0]);

                    }
                },hour,minute,true);
                timePickerDialog.show();


               DatePickerDialog datePickerDialog = new DatePickerDialog(AdminAddNPActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                       // d[0] = dayOfMonth + "-" + monthOfYear + "-" + year;
                       // String date=String.format("%02d-%02d-%d",dayOfMonth,monthOfYear,year);
                        String date=String.format("%d-%02d-%02d",year,monthOfYear,dayOfMonth);
                        productDate.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();

            }



        });



        selectProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });



    }





    private void openGallery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==galleryPic && resultCode==RESULT_OK && data!=null)
        {
            imageUri=data.getData();
            selectProductImage.setImageURI(imageUri);
        }


    }

    private void validateProductData() {

        pName=productName.getText().toString();
        pQuantity=productQuantity.getText().toString();
        pValue=productValue.getText().toString();
        pDate=productDate.getText().toString();
        pTime=productTime.getText().toString();

        if(imageUri==null)
        {
            Toast.makeText(this, "Product Image is Mandatory", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(pName))
        {
            Toast.makeText(this, "Please Enter the Product Name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pQuantity))
        {
            Toast.makeText(this, "Please Enter the Product Quantity", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(pValue))
        {
            Toast.makeText(this, "Please Enter the Product Value", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(pDate))
        {
            Toast.makeText(this, "Please Enter the Product Expiry Date", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pTime))
        {
            Toast.makeText(this, "Please Enter the Product Expiry Time", Toast.LENGTH_SHORT).show();
        }else
        {
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("please wait, while we are adding the new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calendar.getTime());

        productRandomKey =saveCurrentDate + saveCurrentTime;
        final StorageReference filePath=productImagesRef.child(imageUri.getLastPathSegment()+productRandomKey+".jpg");
        final UploadTask uploadTask=filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg=e.toString();
                Toast.makeText(AdminAddNPActivity.this, "Error :"+msg, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNPActivity.this, "Product Images Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if(!task.isSuccessful())
                       {
                           throw task.getException();
                       }

                       downloadImgUri=filePath.getDownloadUrl().toString();
                       return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            timeRandomKey = pDate + "T" + pTime;
                            downloadImgUri=task.getResult().toString();
                            Toast.makeText(AdminAddNPActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
                            saveProductInfoToDatabase();

                        }
                    }
                });

            }
        });
    }

    private void saveProductInfoToDatabase() {

        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid",timeRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("name",pName);
        productMap.put("quantity",pQuantity);
        productMap.put("value",pValue);
       // productMap.put("bidstarttime",pStartbt);
        productMap.put("bidenddate",pDate);
        productMap.put("bidendtime",pTime);
        productMap.put("image",downloadImgUri);
        productMap.put("phone",Prevalent.currentOnlineUser.getPhone());
        productMap.put("username",username);
        productMap.put("useradd",useradd);

        productsRef.child(timeRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    loadingBar.dismiss();
                    Toast.makeText(AdminAddNPActivity.this, "Product is Added Successfully...", Toast.LENGTH_SHORT).show();
                    HashMap<String, Object> time = new HashMap<>();
                    time.put("bidwalatime", pTime);
                    time.put("bidwaladate", pDate);
                    time.put("pid", timeRandomKey);

                    timedateref.child(timeRandomKey).updateChildren(time).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminAddNPActivity.this, "Congrats", Toast.LENGTH_SHORT).show();
                                HashMap<String, Object> productMap = new HashMap<>();
                                productMap.put("pid", timeRandomKey);
                                productMap.put("date", saveCurrentDate);
                                productMap.put("time", saveCurrentTime);
                                productMap.put("name", pName);
                                productMap.put("quantity", pQuantity);
                                productMap.put("value", pValue);
                                // productMap.put("bidstarttime",pStartbt);
                                productMap.put("bidenddate", pDate);
                                productMap.put("bidendtime", pTime);
                                productMap.put("image", downloadImgUri);
                                productMap.put("phone",Prevalent.currentOnlineUser.getPhone());
                                productMap.put("username",username);
                                productMap.put("useradd",useradd);

                                customerproductRef.child(timeRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                          //  Toast.makeText(AdminAddNPActivity.this, "wow", Toast.LENGTH_SHORT).show();

                                        } else {
                                           // Toast.makeText(AdminAddNPActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                Intent a = new Intent(AdminAddNPActivity.this, FarmerHomeActivity.class);
                                // a.putExtra("pid",productRandomKey);
                                startActivity(a);

                                finish();
                            } else {
                                loadingBar.dismiss();
                                String msg = task.getException().toString();
                                Toast.makeText(AdminAddNPActivity.this, "Error : " + msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
}


