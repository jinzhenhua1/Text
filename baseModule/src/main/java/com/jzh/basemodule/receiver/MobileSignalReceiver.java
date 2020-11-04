package com.jzh.basemodule.receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.jzh.basemodule.callback.IonBroadcastResultListenner;
import com.jzh.basemodule.utils.LogUtils;
import com.jzh.basemodule.utils.PhoneUtil;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/4 11:48
 */
public class MobileSignalReceiver {
    private static final String TAG = MobileSignalReceiver.class.getSimpleName();
    private TelephonyManager telephonyManager;
    /**
     * 回调
     */
    private IonBroadcastResultListenner mIonBroadcastResultListenner;

    public void setIonBroadcastResultListenner(IonBroadcastResultListenner mIonBroadcastResultListenner) {
        this.mIonBroadcastResultListenner = mIonBroadcastResultListenner;
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
                int level = -1;
                int type = 4;
                //具体的信号值
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
                        mIonBroadcastResultListenner.onMobileChange(0, signal, 0);
                    }
                } else {
                    if (mIonBroadcastResultListenner != null) {
                        mIonBroadcastResultListenner.onMobileChange(level, signal, type);
                    }
                }
                super.onSignalStrengthsChanged(signalStrength);
            }

        };
        //监听信号改变
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }
}
