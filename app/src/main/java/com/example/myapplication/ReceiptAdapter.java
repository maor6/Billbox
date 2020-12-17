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
import DataStructures.Receipt;

/**
 * This class assigned to fit a visual list of receipt to the screen
 */
public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.MyViewHolder> {

    ArrayList<Receipt> receipts;
    Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ReceiptAdapter(Context context, ArrayList<Receipt> receipts) {
        this.receipts = receipts;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reciepts_list_item, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Receipt receipt = receipts.get(position);
        holder.price.setText(String.format("%.2f", receipt.getTotal_price())+"₪");
        holder.date.setText(receipt.getDate());
        holder.bussinesname.setText(receipt.getBusinessName());
    }

    @Override
    public int getItemCount() { return receipts.size(); }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bussinesname, date, price;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            bussinesname = (TextView) itemView.findViewById(R.id.receiptbussinesname);
            date = (TextView) itemView.findViewById(R.id.receiptDate);
            price = (TextView) itemView.findViewById(R.id.recieptPrice);

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
