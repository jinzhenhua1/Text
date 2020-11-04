package com.jzh.basemodule.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.jzh.basemodule.utils.LogUtils;

/**
 * <p>电量监听广播</p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/4 16:55
 */
public class BatteryReceiver extends BroadcastReceiver {
    private static final String TAG = BatteryReceiver.class.getSimpleName();
    private BatteryStateListener mBatteryStateListener;

    private int lastStatus = 99999;

    /**
     * 如果注册广播之前就在充电,则不会发出power_connect 广播
     * 所以需要监听一次电量改变的广播
     */
    private boolean isInit = false;


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        switch (action) {
            case Intent.ACTION_POWER_CONNECTED:
                isInit = true;
                if(mBatteryStateListener != null){
                    mBatteryStateListener.onStatePowerConnected();
                }
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                isInit = true;
                if(mBatteryStateListener != null){
                    mBatteryStateListener.onStatePowerDisconnected();
                }
                break;
            case Intent.ACTION_BATTERY_CHANGED:
                LogUtils.e(TAG,"收到电量改变广播");
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
                if(isInit || lastStatus == status){
                    return;
                }else{
                    lastStatus = status;
                    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
//                    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
                    //进入页面的时候,会收到一次不充电的广播,然后才会收到已充电的广播,
                    //未充电的广播在ACTION_POWER_DISCONNECTED 中处理便可,此处不在处理
                    if(isCharging){
                        isInit = true;
                        if(mBatteryStateListener != null){
                            mBatteryStateListener.onStatePowerConnected();
                        }
                    }else{
                    }
                }

                break;
            default:
                break;
        }


//        int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
//        int scale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
//        int levelPercent = (int)(((float)level / scale) * 100);

//        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
//
//        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
//        if(isCharging){
//            IHTts.getInstance().play("电量：" + levelPercent + "充电中");
//        }else{
//            IHTts.getInstance().play("电量：" + levelPercent + "未充电");
//        }

//        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
//        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
//        if(usbCharge){
//
//            IHTts.getInstance().play("电量：" + levelPercent + "usb");
//        }else if(acCharge){
//
//            IHTts.getInstance().play("电量：" + levelPercent + "ac");
//        }else{
//
//            IHTts.getInstance().play("电量：" + levelPercent + "un");
//        }

    }

    public void register(Activity mContext, BatteryStateListener listener) {
        mBatteryStateListener = listener;
        IntentFilter filter = new IntentFilter();
        //电量改变广播
        //如果在注册广播之前,设备就处于充电状态,那么就不会收到ACTION_POWER_CONNECTED 的广播,
        //但是会收到ACTION_BATTERY_CHANGED 的广播,该广播中即可获取充电状态
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);

        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        mContext.registerReceiver(this, filter);
    }

    public interface BatteryStateListener {

        public void onStatePowerConnected();

        public void onStatePowerDisconnected();
    }
}
