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
 * This class assigned to fit a visual list of products to the screen
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    ArrayList<Product> products;
    Context context;
    private OnItemClickListener mListener;

    /**
     * Constructor
     * @param context
     * @param products
     */
    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
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
        holder.productName.setText(product.getName());
        holder.barcode.setText(product.getBarCode()+ "");
        holder.price.setText(product.getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void filterList(ArrayList<Product> filteredList){
        products = filteredList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, barcode, price;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            barcode = (TextView) itemView.findViewById(R.id.barcode);
            price = (TextView) itemView.findViewById(R.id.product_price);

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
