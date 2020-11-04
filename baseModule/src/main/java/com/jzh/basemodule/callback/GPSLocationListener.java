package com.jzh.basemodule.callback;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/4 16:55
 */
public interface GPSLocationListener {
    /**
     * GPS搜星结果
     * @param rst 搜星结果
     */
    void gpsSearchRst(String rst);
}
