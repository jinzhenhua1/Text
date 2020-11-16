package com.jzh.pusher.utils;

/**
 * https://www.jianshu.com/p/ca869aa2fd72
 */
public class DeviceUtils {

    /**
     * 获取厂商名
     * **/
    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取产品名
     * **/
    public static String getDeviceProduct() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 获取手机品牌
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机主板名
     */
    public static String getDeviceBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * 设备名
     * **/
    public static String getDeviceDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     *
     *
     * fingerprit 信息
     * **/
    public static String getDeviceFubgerprint() {
        return android.os.Build.FINGERPRINT;
    }

    public static void getDeviceInfo() {
        Logger.d("厂商名:"+getDeviceManufacturer()+"\n"+
                "获取产品名:"+getDeviceProduct()+"\n"+
                "获取手机品牌:"+getDeviceBrand()+"\n"+
                "获取手机型号:"+getDeviceModel()+"\n");
    }
}
