package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Activities.R;
import DataStructures.Product;


/**
 * This class assigned to fit a visual list of Product to the screen
 */
public class ProductsListBillAdapter extends RecyclerView.Adapter<ProductsListBillAdapter.MyViewHolder> {

    ArrayList<Product> products;
    Context context;
    private OnItemClickListener mListener;
    Product mRecentlyDeletedProduct;
    int mRecentlyDeletedProductIndex;

    public Context getContext() {
        return this.context;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductsListBillAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_products_list, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.price.setText(String.format("%.2f", product.getPrice()));
        //holder.amount.setText(product.getAmount());
        holder.productName.setText(product.getName());
        holder.total_price.setText(String.format("%.2f", product.getPrice() * product.getAmount()));
    }

    @Override
    public int getItemCount() { return products.size(); }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, price, amount, total_price;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            price = (TextView) itemView.findViewById(R.id.product_price);
            //amount = (TextView) itemView.findViewById(R.id.choose_amount);
            total_price = (TextView) itemView.findViewById(R.id.total_price);

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

    /**
     * delete item in the recyclerview
     * @param position
     */
    public void deleteItem(int position) {
        mRecentlyDeletedProduct = products.get(position);
        mRecentlyDeletedProductIndex = position;
        products.remove(position);
        notifyItemRemoved(position);
    }

}
