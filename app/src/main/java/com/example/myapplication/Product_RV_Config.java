package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Activities.R;
import DataStructures.Product;

import java.util.List;

public class Product_RV_Config {

    private Context context;
    private ProAdapter proAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Product> products) {
        this.context = context;
        proAdapter = new ProAdapter(products);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(proAdapter);
    }

    class ProductItemView extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView barcode;

        public ProductItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false));
            product_name = (TextView) itemView.findViewById(R.id.productName);
            barcode = (TextView) itemView.findViewById(R.id.barcode);
        }

        public void bind(Product p) {
            this.product_name.setText(p.getName());
            this.barcode.setText(p.getBarCode());
        }
    }

    class ProAdapter extends RecyclerView.Adapter<ProductItemView> {
        List<Product> productsList;
        public ProAdapter(List<Product> productsList) {
            this.productsList = productsList;
        }

        @NonNull
        @Override
        public Product_RV_Config.ProductItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Product_RV_Config.ProductItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Product_RV_Config.ProductItemView holder, int position) {
            holder.bind(productsList.get(position));
        }

        @Override
        public int getItemCount() {
            return productsList.size();
        }
    }
}
