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
import DataStructures.Warranty;

/**
 * This class assigned to fit a visual list of receipt to the screen
 */
public class WarrantiesAdapter extends RecyclerView.Adapter<WarrantiesAdapter.MyViewHolder> {

    ArrayList<Warranty> warranties;
    Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public WarrantiesAdapter(Context context, ArrayList<Warranty> warranties) {
        this.warranties = warranties;
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
        Warranty warranty = warranties.get(position);
        holder.expiryDate.setText(warranty.getExpiryDate());
        holder.purchaseDate.setText(warranty.getDate());
        holder.businessName.setText(warranty.getBusinessName());
    }

    @Override
    public int getItemCount() { return warranties.size(); }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView businessName, purchaseDate, expiryDate;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            businessName = (TextView) itemView.findViewById(R.id.receiptbussinesname);
            purchaseDate = (TextView) itemView.findViewById(R.id.receiptDate);
            expiryDate = (TextView) itemView.findViewById(R.id.recieptPrice);

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
