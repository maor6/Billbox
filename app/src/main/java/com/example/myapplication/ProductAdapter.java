package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;



public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    ArrayList<Product> products;

    public ProductAdapter(ArrayList<Product> products){
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getName());
        holder.barcode.setText(products.get(position).getBarCode());
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, barcode;


        public MyViewHolder(@NonNls View itemView){
            super(itemView);
            this.productName = itemView.findViewById(R.id.Pname);
            this.barcode = itemView.findViewById((R.id.barcode));
        }
    }
}