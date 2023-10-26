package com.example.billapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReadDataAdapter extends RecyclerView.Adapter<ReadDataAdapter.ReadDataHolder> {
    CustomerVendor customerVendor;
    ArrayList<CustVendModel> dataList;

    public ReadDataAdapter(CustomerVendor customerVendor, ArrayList<CustVendModel> dataList) {
        this.customerVendor = customerVendor;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ReadDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReadDataHolder(LayoutInflater.from(customerVendor).inflate(R.layout.item_data_cust, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReadDataHolder holder, int position) {
        holder.comname.setText(dataList.get(position).getCname());
        holder.cno.setText(dataList.get(position).getCno());
        holder.email.setText(dataList.get(position).getEmail());
        holder.address.setText(dataList.get(position).getAddress());
        holder.note.setText(dataList.get(position).getNote());
        holder.actype.setText(dataList.get(position).getAcType());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ReadDataHolder extends RecyclerView.ViewHolder {

        TextView comname, email, cno, address, note, actype;

        public ReadDataHolder(@NonNull View itemView) {
            super(itemView);
            comname = itemView.findViewById(R.id.cHeading);
            email = itemView.findViewById(R.id.emailHead);
            cno = itemView.findViewById(R.id.cnoHead);
            address = itemView.findViewById(R.id.addressHead);
            note = itemView.findViewById(R.id.noteHead);
            actype = itemView.findViewById(R.id.acTypeHead);
        }
    }
}
