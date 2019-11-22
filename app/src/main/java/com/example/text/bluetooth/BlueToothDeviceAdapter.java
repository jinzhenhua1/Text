package com.example.text.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.text.R;

import java.util.List;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/22 12:07
 */
public class BlueToothDeviceAdapter extends BaseAdapter {
    protected Context context;
    protected List<BluetoothDevice> list;
    private int selectPos = -1;
    private int connectPos = -1;

    public BlueToothDeviceAdapter(Context context,List<BluetoothDevice> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_bluetooth_device,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.textViewDeviceName);
            viewHolder.imageView = view.findViewById(R.id.imageViewLink);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        BluetoothDevice bluetoothDevice = list.get(position);
        viewHolder.textView.setText((bluetoothDevice.getName()!=null?bluetoothDevice.getName():"T7 BT Printer")+"["+bluetoothDevice.getAddress()+"]");

        if(connectPos == position){
            viewHolder.imageView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imageView.setVisibility(View.GONE);
        }

        if(selectPos == position){
            view.setPressed(true);
            view.setBackgroundColor(context.getResources().getColor(R.color.color_click_blue));
        }else {
            view.setPressed(false);
            view.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        return view;
    }

    class ViewHolder{
        public TextView textView;
        public ImageView imageView;
    }


    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public int getConnectPos() {
        return connectPos;
    }

    public void setConnectPos(int connectPos) {
        this.connectPos = connectPos;
    }
}
