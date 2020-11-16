package com.jzh.basemodule.config;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/4 11:50
 */
public class Eenum {
    /**
     * 广播类型
     */
    public enum BroadcastType {
        // 移动网络
        NETWORK_STATE,
        // wifi热点
        WIFI_AP_STATE,
        //蓝牙
        BLUETOOTH_STATE,
        //启动
        BOOT,
        //广播卸载
        SHUTDOWN
    }
}
