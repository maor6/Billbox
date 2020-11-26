package com.example.myapplication;

import android.content.Context;
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
    Context context;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.product_name.setText(product.getName());
        holder.barcode.setText(product.getBarCode() + "");
    }

    @Override
    public int getItemCount() { return products.size(); }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, barcode;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.Pname);
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