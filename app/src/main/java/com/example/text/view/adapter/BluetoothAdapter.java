package com.example.text.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.text.R;

import java.util.List;

public class BluetoothAdapter extends RecyclerView.Adapter<BluetoothAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> bluetoothDevices;

    public BluetoothAdapter(Context context, List<String> bluetoothDevices){
        this.mContext = context;
        this.mInflater=LayoutInflater.from(context);
        this.bluetoothDevices = bluetoothDevices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyclerview,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item_tv.setText(bluetoothDevices.get(position));
    }

    @Override
    public int getItemCount() {
        return bluetoothDevices == null ? 0 : bluetoothDevices.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public ViewHolder(View view){
            super(view);
            item_tv = (TextView)view.findViewById(R.id.item_tv_name);
        }
    }

    public void refresh(List<String> bluetoothDevices){
        this.bluetoothDevices = bluetoothDevices;
        notifyDataSetChanged();
    }
}
