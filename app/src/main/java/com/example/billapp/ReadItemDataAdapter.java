package com.example.billapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReadItemDataAdapter extends RecyclerView.Adapter<ReadItemDataAdapter.ReadItemDataHolder> {
    Products products;
    ArrayList<ItemModel> dataList;

    public ReadItemDataAdapter(Products products, ArrayList<ItemModel> dataList) {
        this.products = products;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ReadItemDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReadItemDataHolder(LayoutInflater.from(products).inflate(R.layout.item_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReadItemDataHolder holder, int position) {
        holder.itemName.setText(dataList.get(position).getPname());
        holder.price.setText(dataList.get(position).getPrice());
        holder.itemDesc.setText(dataList.get(position).getPdesc());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ReadItemDataHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemDesc, price;

        public ReadItemDataHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemHead);
            itemDesc = itemView.findViewById(R.id.descHead);
            price = itemView.findViewById(R.id.priceHead);
        }
    }
}
