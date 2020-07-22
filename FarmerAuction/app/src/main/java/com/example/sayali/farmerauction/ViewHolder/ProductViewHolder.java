package com.example.sayali.farmerauction.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sayali.farmerauction.Interface.ItemClickListener;
import com.example.sayali.farmerauction.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public  TextView product_name,product_price, product_quantity,pedate,petime,fname,fadd;
    public  ImageView product_image;
    public ItemClickListener listener;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        product_image=itemView.findViewById(R.id.product_image);
        product_quantity=itemView.findViewById(R.id.product_quantity);
        product_price=itemView.findViewById(R.id.product_price);
        product_name=itemView.findViewById(R.id.product_name);
        pedate=itemView.findViewById(R.id.pedate);
        petime=itemView.findViewById(R.id.petime);

        fname=itemView.findViewById(R.id.fname);
    }

    public void setItemClickListener(ItemClickListener listener )
    {

        this.listener=listener;
    }
    @Override
    public void onClick(View v) {

        listener.onClick(v,getAdapterPosition(),false);

    }
}
