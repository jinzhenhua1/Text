package com.jzh.basemodule.utils;

import android.content.Context;
import android.os.BatteryManager;

import static android.content.Context.BATTERY_SERVICE;


/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/4 16:55
 */
public class BatteryUtil {

    private BatteryUtil(){}

    /**
     * 获取当前电量
     * @param context 上下文
     * @return 电量等级
     */
    public static int getBatteryLevel(Context context) {
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

}
