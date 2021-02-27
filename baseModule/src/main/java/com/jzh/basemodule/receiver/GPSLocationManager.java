package com.jzh.basemodule.receiver;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;


import com.jzh.basemodule.callback.GPSLocationListener;
import com.jzh.basemodule.config.Eenum;
import com.jzh.basemodule.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.Iterator;

/**
 * <p>GPS 信号搜索</p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/4 16:55
 */
public class GPSLocationManager implements LocationListener {
    /**
     * 最少的pass数量
     */
    public static final int MIN_PASS_COUNT = 12;

    private WeakReference<Context> mContext;
    private LocationManager locationManager;
    private GPSLocationListener gpsLocationListener;
    private static final int MAX_GPS_COUNT = 15;
    private int gpsCount = 0;

    public static GPSLocationManager getInstance() {
        return InnerClass.gpsLocationManager;
    }

    /**
     * 是否搜索成功,搜错成功就可以不返回数据了
     */
    private boolean searchSuccess = false;

    public boolean isSearchSuccess() {
        return searchSuccess;
    }

    public void initData(Context context) {
        this.mContext = new WeakReference<>(context);
        if (mContext.get() != null) {
            locationManager = (LocationManager) (mContext.get().getSystemService(Context.LOCATION_SERVICE));
        }
        startGps();
    }

    public void setGpsLocationListener(GPSLocationListener gpsLocationListener) {
        this.gpsLocationListener = gpsLocationListener;
    }

    /**
     * 方法描述：开启定位
     */
    public void startGps() {
        locationManager = (LocationManager) mContext.get().getSystemService(Context.LOCATION_SERVICE);
        //判断GPS是否正常启动
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return;
        }
        //添加卫星状态改变监听
        if (ActivityCompat.checkSelfPermission(mContext.get(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.addGpsStatusListener(gpsStatusListener);
        //1000位最小的时间间隔，1为最小位移变化；也就是说每隔1000ms会回调一次位置信息
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
    }

    private GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            switch (event) {
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    //获取当前状态
                    if (ActivityCompat.checkSelfPermission(mContext.get(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                    //获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    //获取所有的卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    //卫星颗数统计
                    int count = 0;
                    while (iters.hasNext() && count <= MAX_GPS_COUNT) {
                        GpsSatellite s = iters.next();
//                        if (s.usedInFix()) {
//                            count++;
//                        }

                        //卫星的信噪比
                        float snr = s.getSnr();
//                        LogUtils.loge("卫星的信噪比为:" + snr);
                        if (snr > 0) {
                            count++;
                            if (!searchSuccess) {
                                if (gpsLocationListener != null) {
                                    gpsLocationListener.gpsSearchRst("PASS");
                                }
                                searchSuccess = true;
                                break;
                            }
                        }
                    }

                    gpsCount = count;
                    int strength = getGPSLevel();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 方法描述：终止GPS定位,该方法最好在onPause()中调用
     */
    public void stop() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private static class InnerClass {
        static GPSLocationManager gpsLocationManager = new GPSLocationManager();
    }

    /**
     * 获取gps的信号强度
     * 12-15颗 是5格信号
     */
    private int getGPSLevel() {
        int level = -1;
        int average = MAX_GPS_COUNT / 5;
        if (gpsCount != 0) {
            if (gpsCount > MAX_GPS_COUNT) {
                level = 5;
            } else {
                level = (gpsCount - 1) / average + 1;
            }
        }
        return level;
    }

    public static String getGPSStrengthString(int level){
        switch (level){
            case -1:
                return "无信号";
            case 1:
                return "信号微弱";
            case 2:
                return "信号较弱";
            case 3:
                return "信号中等";
            case 4:
                return "信号较强";
            case 5:
                return "信号最强";
            default:
                return "无信号";
        }
    }

    /**
     * 获取卫星数量
     */
    public int getGpsCount() {
        return gpsCount;
    }
}
