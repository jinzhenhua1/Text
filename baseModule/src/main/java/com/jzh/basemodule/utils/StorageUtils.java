package com.jzh.basemodule.utils;

import android.annotation.SuppressLint;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.io.File;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/11/3 16:55
 */
public class StorageUtils {

    /**
     * 获取手机内部总的存储空间
     *
     * @return 如果手机内外存储都再一个物理存储上，那么 getDataDirectory 与 getExternalStorageDirectory
     *      获取到的空间大小一致，都为：机身存储 - 系统固件大小；
     *      如：64G内存的机器，系统占用了7.71G ，那么返回的大小为56.288G(当然 这里的G 是按 1000 来除的，而不是1024)
     */
    public static double getExternalSize() {
        File path = Environment.getDataDirectory();
//        return path.getTotalSpace();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize / 1000.0 / 1000.0 / 1000.0;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return 这里获取到的是真正的机身存储，如果是64G 的机器，那么就是返回64G
     */
    @SuppressLint("NewApi")
    public static double getSystemAvailableStorage(Context context) {
        try {
            StorageStatsManager stats = context.getSystemService(StorageStatsManager.class);
//            return stats.getFreeBytes(StorageManager.UUID_DEFAULT)/1000/1000/1000;
            double size = stats.getTotalBytes(StorageManager.UUID_DEFAULT)/1000.0/1000.0/1000.0;
            return size;
        } catch (NoSuchFieldError | NoClassDefFoundError | NullPointerException | IOException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 获取外部存储根目录
     * @return 如：/storage/emulated/0
     */
    public static String getExternalStoragePath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取应用在外部存储中的files路径
     * @param context
     * @return 如：/storage/emulated/0/Android/data/packname/files
     * 经过验证，用as自带的device file explorer查看目录， 前面的 /storage/emulated/0 再红米6手机中真实路径 /storage/sdcard0。
     * 再红米k20 pro手机中，真实路径为/storage/self/primary
     */
    public static String getExternalFilesPath(Context context){
        //参数为该文件夹下指定的文件夹
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 获取应用在外部存储中的cache路径
     * @param context
     * @return 如：/storage/emulated/0/Android/data/packname/cache
     * 经过验证，用as自带的device file explorer查看目录， 前面的 /storage/emulated/0 再红米6手机中真实路径 /storage/sdcard0
     */
    public static String getExternalCachePath(Context context){
        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取内部存储根目录
     * @return  如：/data
     */
    public static String getDataRootPath(){
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * 获取应用在内部存储中的files路径
     * @param context
     * @return 如：/data/user/0/packname/files
     * 经过验证，用as自带的device file explorer查看目录， 前面的 /data/user/0 再红米6手机中真实路径 /data/data
     */
    public static String getFileDirPath(Context context){
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取应用在内部存储中的cache路径
     * @param context
     * @return 如：/data/user/0/packname/cache
     * 经过验证，用as自带的device file explorer查看目录， 前面的 /data/user/0 再红米6手机中真实路径 /data/data
     */
    public static String getCacheDirPath(Context context){
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获取应用在内部存储中的 自定义 路径
     * @param context
     * @param name
     * @return 如：/data/user/0/packname/{@link #name}
     * 经过验证，用as自带的device file explorer查看目录， 前面的 /data/user/0 再红米6手机中真实路径 /data/data
     */
    public static String getDirPath(Context context,String name){
        return context.getDir(name, MODE_PRIVATE).getAbsolutePath();
    }
}
