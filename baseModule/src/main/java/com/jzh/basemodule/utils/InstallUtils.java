package com.jzh.basemodule.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.jzh.basemodule.BuildConfig;

import java.io.File;

/**
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/1/27 14:20
 */
public class InstallUtils {
    private static final String TAG = InstallUtils.class.getSimpleName();
    /**
     * 安装APK文件
     */
    public static void installApk(String filePath, Context context) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
//        File apkfile = new File(apkPath, updateFileName);
        File apkfile = new File(filePath);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        //android7.0适配，7.0 开始要使用外部程序打开本程序的文件，需要通过FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.LIBRARY_PACKAGE_NAME + ".fileProvider", apkfile);
            i.setDataAndType(contentUri, "application/vnd.android.package-archive");
            // android8.0适配，8.0开始要动态申请安装的权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    Toast.makeText(context, "没有安装未知来源apk的权限,请允许权限然后重新更新", Toast.LENGTH_SHORT).show();
                    startInstallPermissionSettingActivity(context);
                    return;
                }
            }
        } else {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        context.startActivity(i);
        // 如果不加上这句的话在apk安装完成之后点击单开会崩溃
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    /**
     * 跳转到设置-允许安装未知来源-页面，大于安卓8.0才需要动态申请
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context context) {
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Log.d(TAG, "startInstallPermissionSettingActivity: ");
        //该处经验证，会立刻返回空的返回值，所以用startActivityForResult 没有意义
//        ((ControlActivity) mContext).startActivityForResult(intent, INSTALL_PERMISSION_CODE);
    }
}
