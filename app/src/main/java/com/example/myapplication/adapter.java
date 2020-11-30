package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import DataStructures.Product;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {

    ArrayList<Product> products;
    Context context;
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.barcode.setText(product.getBarCode()+ "");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public adapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, barcode;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            barcode = (TextView) itemView.findViewById(R.id.barcode);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });


        }
    }



}
