package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Activities.R;
import DataStructures.receipt;

public class RecyclerView_Config {
    private Context context;
    private docAddapter docAddapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<receipt> receipts) {
        this.context = context;
        docAddapter = new docAddapter(receipts);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(docAddapter);
    }

    class ReceiptItemView extends RecyclerView.ViewHolder {
        TextView bussines_name;
        TextView date;
        TextView price;

        public ReceiptItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.reciepts_list_item, parent, false));
            bussines_name = (TextView) itemView.findViewById(R.id.bussinesname);
            date = (TextView) itemView.findViewById(R.id.receiptDate);
            price = (TextView) itemView.findViewById(R.id.recieptPrice);
        }

        public void bind(receipt doc) {
            this.bussines_name.setText(doc.getBusiness());
            this.date.setText(doc.getBusiness());
            this.price.setText(price + "");
        }
    }

    class docAddapter extends RecyclerView.Adapter<ReceiptItemView> {
        List<receipt> documentList;
        public docAddapter(List<receipt> documentList) {
            this.documentList = documentList;
        }

        @NonNull
        @Override
        public ReceiptItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ReceiptItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ReceiptItemView holder, int position) {
            holder.bind(documentList.get(position));
        }

        @Override
        public int getItemCount() {
            return documentList.size();
        }
    }
}
