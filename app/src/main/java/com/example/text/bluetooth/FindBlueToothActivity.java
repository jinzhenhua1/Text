package com.example.text.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.text.R;
import com.example.text.helper.DialogHelper;

import java.util.ArrayList;

public class FindBlueToothActivity extends AppCompatActivity {
    public static final int REQUESTCODE_BLUETOOTH_ENABLE = 1;
    private BluetoothAdapter bluetoothAdapter;//蓝牙操作类
    private BlueToothDeviceAdapter blueToothDeviceAdapter;//蓝牙信息展示适配器
    private ListView activity_bule_tooth_lv;
    private Dialog loadingDialog;
    private ArrayList<BluetoothDevice> listDevice = new ArrayList();
    private Button activity_bule_tooth_btn_find;//搜索按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_blue_tooth);
        initView();
        initBluetooth();
    }

    private void initView(){
        activity_bule_tooth_lv = findViewById(R.id.activity_bule_tooth_lv);
        activity_bule_tooth_btn_find = findViewById(R.id.activity_bule_tooth_btn_find);
        activity_bule_tooth_btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDevice();
            }
        });
    }



    private void initBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        blueToothDeviceAdapter = new BlueToothDeviceAdapter(this,listDevice);
        loadingDialog =  DialogHelper.loadingDialog(this);
    }

    /**
     * 请求打开蓝牙
     */
    private void requestOpenBluetooth() {
        Intent it = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //请求打开蓝牙
        it.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); //设置蓝牙可见性
        startActivityForResult(it, REQUESTCODE_BLUETOOTH_ENABLE);//会以Dialog样式显示一个Activity ， 我们可以在onActivityResult()方法去处理返回值
    }
    private IntentFilter intentFilter = new IntentFilter();
    private DiscoverBlueToothReceiver discoverBlueToothReceiver = new DiscoverBlueToothReceiver();//广播
    private void findDevice(){
        if(!bluetoothAdapter.isEnabled()) {
            requestOpenBluetooth();
        } else {
            activity_bule_tooth_lv.setAdapter(blueToothDeviceAdapter);

            intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
            intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(discoverBlueToothReceiver,intentFilter);
            bluetoothAdapter.startDiscovery();//开启扫描
            loadingDialog.show();
//            baseFragmentHandler.sendMessage(baseFragmentHandler.obtainMessage(SHOW_PROGRESS,"正在查找打印机..."));
        }
    }

    private void updateParedDevices(BluetoothDevice bluetoothDevice){
        boolean isExist = false;
        for(BluetoothDevice device:listDevice){
            if(device.getAddress().equalsIgnoreCase(bluetoothDevice.getAddress())){
                isExist = true;
                continue;
            }
        }
        if(!isExist){
//            btDevice = bluetoothDevice;
            listDevice.add(bluetoothDevice);
//            if(bluetoothDevice.getAddress().equalsIgnoreCase(blueToothAddr)){
//                int size = listDevice.size();
//                blueToothDeviceAdapter.setConnectPos(size-1);
//            }
        }
        blueToothDeviceAdapter.notifyDataSetChanged();
    }

    /**
     *   ACTION_STATE_CHANGED                    蓝牙状态值发生改变
     *       ACTION_SCAN_MODE_CHANGED         蓝牙扫描状态(SCAN_MODE)发生改变
     *       ACTION_DISCOVERY_STARTED             蓝牙扫描过程开始
     *       ACTION_DISCOVERY_FINISHED             蓝牙扫描过程结束
     *       ACTION_LOCAL_NAME_CHANGED        蓝牙设备Name发生改变
     *       ACTION_REQUEST_DISCOVERABLE       请求用户选择是否使该蓝牙能被扫描
     *
     *       PS：如果蓝牙没有开启，用户点击确定后，会首先开启蓝牙，继而设置蓝牙能被扫描。
     *       ACTION_REQUEST_ENABLE                  请求用户选择是否打开蓝牙
     */
    class DiscoverBlueToothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(BluetoothDevice.ACTION_FOUND.equalsIgnoreCase(intent.getAction())){
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                updateParedDevices(bluetoothDevice);
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
                bluetoothAdapter.cancelDiscovery();
                context.unregisterReceiver(this);
                loadingDialog.dismiss();
            }
        }
    }
}
