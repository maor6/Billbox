package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import Activities.R;
import DataStructures.Product;

/**
 * This class assigned to fit a list of products to receipt screen
 */
public class ProductsListAdapter extends ArrayAdapter<Product> {
    private Context context;
    int resource;

    /**
     * Constructor
     * @param context
     * @param resource
     * @param products
     */
    public ProductsListAdapter(@NonNull Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String price = getItem(position).getPrice()+"";
        int amount = 1;
        double total = amount*Double.parseDouble(price);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView productName = (TextView)convertView.findViewById(R.id.productName);
        TextView productPrice = (TextView)convertView.findViewById(R.id.product_price);
        TextView productTotal = (TextView) convertView.findViewById(R.id.total_price);
        EditText producrAmount = (EditText) convertView.findViewById(R.id.choose_amount);

        productName.setText(name);
        productPrice.setText(price);
        productTotal.setText(price);
//        producrAmount.setText(amount);

        return  convertView;
    }
}
