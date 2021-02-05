package com.jzh.basemodule.utils;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;


import androidx.annotation.RequiresPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 手机功能
 * Created by ousy on 2016/7/2.
 */

public class PhoneUtil {
    //<editor-fold desc="成员变量">
    private static final String TAG = PhoneUtil.class.getSimpleName();
    private Context mContext;
    private AudioManager mManager;
    private Vibrator mVibrator;
    private ConnectivityManager mConnManager;
    private TelephonyManager mTelephonyManager;
    //</editor-fold>
    public static String HAVAE_SIME_CARD = "有sim卡";
    public static String NO_SIME_CARD = "无sim卡";

    public static PhoneUtil getInstance(Context context) {
        if (phoneUtil == null) {
            phoneUtil = new PhoneUtil(context);
            return phoneUtil;
        }
        return phoneUtil;
    }

    private static PhoneUtil phoneUtil;

    private PhoneUtil(Context context) {
        mContext = context;
        mManager = (AudioManager) mContext.getSystemService(Service.AUDIO_SERVICE);
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mConnManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 手机震动
     *
     * @param sec 震动时间（毫秒）
     *            权限：android.permission.VIBRATE"
     */
    public void vibrate(int sec) {
        mVibrator.vibrate(sec);
    }

    // 当前音量
    // 测试按音量改变时：只改变了系统音量和通话音量，没有改变音乐音量 create by baiaj
    public int getVolume() {
        return mManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    }

    // 最大音量
    // 测试按音量改变时：只改变了系统音量和通话音量，没有改变音乐音量 create by baiaj
    public int getMaxVolume() {
        return mManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
    }

    public int getMusicVolume() {
        return mManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public int getMaxMusicVolume() {
        return mManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    // 设置音量
    public void setVolume(int index) {
        if (index == getMaxVolume()) {
            mManager.setStreamVolume(AudioManager.STREAM_MUSIC, getMaxMusicVolume(), 0);
        } else {
            mManager.setStreamVolume(AudioManager.STREAM_MUSIC, index * 2, 0);
        }
        mManager.setStreamVolume(AudioManager.STREAM_SYSTEM, index, 0);
    }

    public void upVolume() {
        mManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
    }

    public void downVolume() {
        mManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }

    /**
     * 手机是否正在播放声音
     *
     * @return true:有声音，false：没声音
     */
    public boolean isFmActive() {
        if (mManager == null) {
            Log.w(TAG, "isFmActive: couldn't get AudioManager reference");
            return false;
        }

        return mManager.isMusicActive();
    }

    // 设置移动网络数据
    public void setMobileData(boolean state) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            openMobileData2(state);
        } else {
            openMobileData1(state);
        }
    }

    /**
     * android5.0以前可调用这方法，更改网络状态
     * 权限：CHANGE_NETWORK_STATE
     *
     * @param state 状态
     */
    @SuppressWarnings("unchecked")
    private void openMobileData1(boolean state) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            Class ownerClass = mConnectivityManager.getClass();
            Class[] argsClass = new Class[1];
            argsClass[0] = boolean.class;
            Method method = ownerClass.getMethod("setMobileDataEnabled", argsClass);
            method.invoke(mConnectivityManager, state);
        } catch (Exception e) {
            LogUtils.e(TAG, "打开移动网络失败:" + e.toString());
        }
    }

    /**
     * android5.0以后
     * 系统权限：MODIFY_PHONE_STATE
     *
     * @param state 状态
     */
    private void openMobileData2(boolean state) {
        Method setMobileDataEnabledMethod;
        try {
            setMobileDataEnabledMethod = mTelephonyManager.getClass().getDeclaredMethod("setDataEnabled", boolean
                    .class);
            setMobileDataEnabledMethod.invoke(mTelephonyManager, state);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LogUtils.e(TAG, "打开移动网络失败:" + e.toString());
        }
    }

    /**
     * 是否开启数据连接
     *
     * @return 移动数据是否开启
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean isMobileDataOn() {
        Boolean isOpen = false;

        try {
            String methodName = "getMobileDataEnabled";
            Class cmClass = mConnManager.getClass();
            Class[] classes = new Class[0];
            Object[] objects = new Object[0];
            Method method = cmClass.getMethod(methodName, classes);
            isOpen = (Boolean) method.invoke(mConnManager, objects);
        } catch (Exception e) {
            LogUtils.e(TAG, "get mobileData failed:" + e.toString());
        }

        return isOpen;
    }

    /**
     * 获取手机信号强度，需添加权限 android.permission.ACCESS_COARSE_LOCATION <br> 6.0后需要手动申请该权限
     * API要求不低于17 <br>
     *
     * @return 当前手机主卡信号强度, 单位 dBm（-1是默认值，表示获取失败）
     */
    public int getMobileDbm(Context context) {
        int dbm = -1;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> cellInfoList = tm.getAllCellInfo();
        if (null != cellInfoList) {
            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo instanceof CellInfoGsm) {
                    CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                    dbm = cellSignalStrengthGsm.getDbm();
                } else if (cellInfo instanceof CellInfoCdma) {
                    CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma) cellInfo).getCellSignalStrength();
                    dbm = cellSignalStrengthCdma.getDbm();
                } else if (cellInfo instanceof CellInfoWcdma) {
                    CellSignalStrengthWcdma cellSignalStrengthWcdma = ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                    dbm = cellSignalStrengthWcdma.getDbm();
                } else if (cellInfo instanceof CellInfoLte) {
                    CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                    dbm = cellSignalStrengthLte.getDbm();
                }
            }
        }
        return dbm;
    }

    public WifiInfo getWifiInfo(Context context) {
        WifiManager wifi_service = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi_service.getConnectionInfo();
        return wifiInfo;
    }

    /**
     * 获取手机内部剩余存储空间
     *
     * @return 单位：MB
     */
    public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize / (1024 * 1024);
    }

    public boolean isWifi() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                //当前网络是连接的
                return info.getState() == NetworkInfo.State.CONNECTED && "WIFI".equals(info.getTypeName());
            }
        }
        return false;
    }

    public String getSensorListInfo(Context context) {
        // 获取传感器管理器
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // 获取全部传感器列表
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // 打印每个传感器信息
        List<Integer> sensorTypes = new ArrayList<>();
        for (Sensor item : sensors) {
            sensorTypes.add(item.getType());
        }
        StringBuilder rst = new StringBuilder();
        if (!sensorTypes.contains(Sensor.TYPE_ACCELEROMETER)) {
            rst.append("没有加速度传感器\n");
        }
        if (!sensorTypes.contains(Sensor.TYPE_MAGNETIC_FIELD)) {
            rst.append("没有地磁传感器\n");
        }
        if (!sensorTypes.contains(Sensor.TYPE_GYROSCOPE)) {
            rst.append("没有陀螺仪传感器\n");
        }
        return rst.toString().isEmpty() ? "PASS" : rst.toString();
    }

    /**
     * 获取SIM卡信息
     *
     * @param context
     * @return
     */
    public String getPhoneInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = tm.getSimState();
        String result = HAVAE_SIME_CARD;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = NO_SIME_CARD;
                break;
            default:
                break;
        }
        return result;
    }


    private static final String HEADSET_STATE_PATH = "/sys/class/switch/wfd/state";

    /**
     * 判断耳机是否接入了
     *
     * @return 耳机是否接入
     */
    public boolean isHeadsetExists() throws IOException {
        char[] buffer = new char[1024];
        int newState = 0;
        FileReader file = null;
        try {
            file = new FileReader(HEADSET_STATE_PATH);
            int len = file.read(buffer, 0, 1024);
            newState = Integer.parseInt((new String(buffer, 0, len)).trim());
        } catch (FileNotFoundException e) {
            Log.e("FMTest", "This kernel does not have wired headset support");
        } catch (Exception e) {
            Log.e("FMTest", "", e);
        } finally {
            if (file != null) {
                file.close();
            }
        }
        return newState != 0;
    }

    public static String getMobileStrengthStr(int mobileStrength) {
        switch (mobileStrength) {
            case -1:
                return "无信号";
            case 1:
                return "信号微弱";
            case 2:
                return "信号较弱";
            case 3:
                return "信号较强";
            case 4:
                return "信号最强";
            default:
                return "无信号";
        }
    }

    /**
     * 获取当前的运营商 需要 android.permission.READ_PHONE_STATE 权限，6.0后要动态申请
     *
     * @return 运营商名字
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public String getOperator() {

        String ProvidersName = "";
        String IMSI = mTelephonyManager.getSubscriberId();
        Log.i("qweqwes", "运营商代码" + IMSI);
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                ProvidersName = "中国移动";
            } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            }
            return ProvidersName;
        } else {
            return "没有获取到sim卡信息";
        }
    }

    /**
     * 安卓10及以上 无法获取IMEI
     * @return
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public String getIMEI() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return "";
        }
        //获取IMEI号
        String imei = mTelephonyManager.getDeviceId();
        //该方法需要SDK版本大于26
//        String imei = mTelephonyManager.getImei();
        //在次做个验证，也不是什么时候都能获取到的啊
        if (imei == null) {
            imei = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return imei;
    }
}
