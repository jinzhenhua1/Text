package com.jzh.basemodule.callback;


import com.jzh.basemodule.config.Eenum;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/4 16:55
 */
public interface IonBroadcastResultListenner {
    /**
     * 收到广播的回调
     * @param broadcastType 发出广播的类型，网络改变、移动热点改变、蓝牙等
     * @param state 状态值，如broadcastType 为网络改变，那么该值为1代表网络已连接，0代表为连接
     * @param type  如broadcastType 为网络改变，那么该参数用来区分具体的网络类型
     */
    void onBroadcastResult(Eenum.BroadcastType broadcastType, int state, int type);

    /**
     * 移动信号改变的监听
     * @param level 信号的级别 0-4 越大越强
     * @param strength  具体的信号值
     * @param type  移动网络类型 2:2G ,3:3G，4:4G
     */
    void onMobileChange(int level, int strength, int type);
}
