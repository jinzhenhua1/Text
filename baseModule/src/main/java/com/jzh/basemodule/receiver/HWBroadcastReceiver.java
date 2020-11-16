package com.jzh.basemodule.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.jzh.basemodule.callback.IonBroadcastResultListenner;
import com.jzh.basemodule.config.Eenum;
import com.jzh.basemodule.utils.LogUtils;
import com.jzh.basemodule.utils.PhoneUtil;

import java.util.Objects;


/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/4 16:55
 */
public class HWBroadcastReceiver extends BroadcastReceiver {
    /**
     * 热点打开或者关闭时回发出的广播，并同时监听 ConnectivityManager.CONNECTIVITY_ACTION  网络状态改变的广播
     */
    public static final String WIFI_AP_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";
    public static final int WIFI_AP_CLOSEING = 10;
    public static final int WIFI_AP_CLOSE_SUCCESS = 11;
    public static final int WIFI_AP_OPENING = 12;
    public static final int WIFI_AP_OPEN_SUCCESS = 13;
    private static final int NETWORK_DISCONECT = 0;
    public static final int NETWORK_CONNECT = 1;
    private static final String TAG = HWBroadcastReceiver.class.getSimpleName();
    private TelephonyManager telephonyManager;
    /**
     * 回调
     */
    private IonBroadcastResultListenner mIonBroadcastResultListenner;

    public void setIonBroadcastResultListenner(IonBroadcastResultListenner mIonBroadcastResultListenner) {
        this.mIonBroadcastResultListenner = mIonBroadcastResultListenner;
    }

    /**
     * @param context ApplicationContext
     * @return
     */
    public static HWBroadcastReceiver getInstance(Context context) {
        if (hwBroadcastReceiver == null) {
            hwBroadcastReceiver = new HWBroadcastReceiver(context);
            return hwBroadcastReceiver;
        }
        return hwBroadcastReceiver;
    }

    private static HWBroadcastReceiver hwBroadcastReceiver;

    private HWBroadcastReceiver(Context context) {
        getPhoneState(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("broadcastTag", "HwBroadcastReceiver，收到广播:" + action);
//        getPhoneState(context);
        //获取网络状态改变的action
        if (Objects.equals(action, ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert mConnectivityManager != null;
            //需要权限 ACCESS_NETWORK_STATE
            NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
            Log.i(TAG, "info---->>>>" + info);
            if (info != null && info.isAvailable() && info.getState() == NetworkInfo.State.CONNECTED) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    if (mIonBroadcastResultListenner != null) {
                        mIonBroadcastResultListenner.onBroadcastResult(Eenum.BroadcastType.NETWORK_STATE, NETWORK_CONNECT, ConnectivityManager.TYPE_WIFI);
                    }
                }
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (mIonBroadcastResultListenner != null) {
                        mIonBroadcastResultListenner.onBroadcastResult(Eenum.BroadcastType.NETWORK_STATE, NETWORK_CONNECT, ConnectivityManager.TYPE_MOBILE);
                    }
                }
            } else {
                if (mIonBroadcastResultListenner != null) {
                    mIonBroadcastResultListenner.onBroadcastResult(Eenum.BroadcastType.NETWORK_STATE, NETWORK_DISCONECT, -1);
                }
            }
        }

        if (Objects.equals(action, WIFI_AP_ACTION) && mIonBroadcastResultListenner != null) {
            int state = intent.getIntExtra("wifi_state", 0);
            Log.i("broadcastTag", "HwBroadcastReceiver，wifi热点状态改变广播，状态为：" + state);
            mIonBroadcastResultListenner.onBroadcastResult(Eenum.BroadcastType.WIFI_AP_STATE, state, -1);
        }
    }


    private void getPhoneState(final Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener phoneStateListener = new PhoneStateListener() {

            @Override
            //获取对应网络的ID，这个方法在这个程序中没什么用处
            public void onCellLocationChanged(CellLocation location) {
                LogUtils.e(TAG, "onCellLocationChange");
            }

            //系统自带的服务监听器，实时监听网络状态
            @Override
            public void onServiceStateChanged(ServiceState serviceState) {
                LogUtils.e(TAG, "onServiceStateChanged");
            }

            //这个是我们的主角，就是获取对应网络信号强度
            @SuppressLint("NewApi")
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                LogUtils.e(TAG, "onSignalStrengthsChanged :" + signalStrength.getLevel());
                //这个ltedbm 是4G信号的值
                String signalinfo = signalStrength.toString();
                String[] parts = signalinfo.split(" ");
                int ltedbm = Integer.parseInt(parts[9]);

                // 信号强度和信号类别
                int level = 0;
                int type = 4;
                int signal = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    level = signalStrength.getLevel();
                }
                if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        level = signalStrength.getLevel();
//                    } else {
//                        if (ltedbm > -75) {
//                            level = 4;
//                        } else if (ltedbm > -85) {
//                            level = 3;
//                        } else if (ltedbm > -95) {
//                            level = 2;
//                        } else if (ltedbm > -100) {
//                            level = 1;
//                        } else {
//                            level = -1;
//                        }
//                    }
                    signal = ltedbm;

                    type = 4;
                } else if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSUPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS) {

                    //3G网络最佳范围  >-90dBm  越大越好  ps:中国移动3G获取不到  返回的无效dbm值是正数（85dbm）
                    //在这个范围的已经确定是3G，但不同运营商的3G有不同的获取方法，故在此需做判断 判断运营商与网络类型的工具类在最下方
                    String yys = PhoneUtil.getOperator(context.getApplicationContext());//获取当前运营商
                    if (yys == "中国移动") {
                        signal = 0;
                    } else if (yys == "中国联通") {
                        signal = signalStrength.getCdmaDbm();
                    } else if (yys == "中国电信") {
                        signal = signalStrength.getEvdoDbm();
                    }

//                    if (dbm > -75) {
//                        level = 4;
//                    } else if (dbm > -85) {
//                        level = 3;
//                    } else if (dbm > -95) {
//                        level = 2;
//                    } else if (dbm > -100) {
//                        level = 1;
//                    } else {
//                        level = -1;
//                    }
                    type = 3;

                } else {
                    //2G网络最佳范围>-90dBm 越大越好
                    int asu = signalStrength.getGsmSignalStrength();
                    int dbm = -113 + 2 * asu;
                    signal = dbm;
//                    if (asu < 0 || asu >= 99) {
//                        level = -1;
//                    } else if (asu >= 16) {
//                        level = 4;
//                    } else if (asu >= 8) {
//                        level = 3;
//                    } else if (asu >= 4) {
//                        level = 2;
//                    } else {
//                        level = 1;
//                    }
                    type = 2;
                }
                int state = telephonyManager.getSimState();
                if (state == TelephonyManager.SIM_STATE_ABSENT || state == TelephonyManager.SIM_STATE_UNKNOWN) {
                    if (mIonBroadcastResultListenner != null) {
                        mIonBroadcastResultListenner.onMobileChange(-1, 0, 0);
                    }
                } else {
                    if (mIonBroadcastResultListenner != null) {
                        mIonBroadcastResultListenner.onMobileChange(level, signal, type);
                    }
                }
                super.onSignalStrengthsChanged(signalStrength);
            }

        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }
}