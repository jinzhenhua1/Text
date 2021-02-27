package com.jzh.basemodule.utils;

import android.Manifest;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>运行时权限工具类<p>
 *
 * @author jinzhenhua
 * @version 1.0 , create at 2020/4/2 11:16
 */
public class PermissionUtil {

    public static final int REQUEST_CODE_PERMISSION = 10001;

    private PermissionUtil() {
    }


    public interface PermissionCallBack {
        void onSuccess();

        void onShouldShow();

        void onFailed();
    }

    /**
     * 本项目需要的权限
     *
     * @return 权限数组
     */
    public static String[] getNeedPermissions() {
        return new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    /**
     * 检查权限，如果发现还有权限没有申请，则继续申请，否则检查通过
     *
     * @param cxt              上下文
     * @param checkPermissions 需要检查的权限数组
     * @return true：所有权限已通过
     */
    public static boolean checkAndRequestPermissionsInActivity(AppCompatActivity cxt, String... checkPermissions) {
        //SDK 23以上才需要动态申请权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        boolean isHas = true;
        List<String> permissions = new ArrayList<>();
        for (String checkPermission : checkPermissions) {
            if (PermissionChecker.checkSelfPermission(cxt, checkPermission) != PermissionChecker.PERMISSION_GRANTED) {
                isHas = false;
                permissions.add(checkPermission);
            }
        }
        //如果还有权限没有通过，则继续申请
        if (!isHas) {
            String[] p = permissions.toArray(new String[permissions.size()]);
            requestPermissionsInActivity(cxt, REQUEST_CODE_PERMISSION, p);
        }
        return isHas;
    }

    /**
     * 申请权限
     *
     * @param cxt         上下文
     * @param requestCode 请求码
     * @param permissions 权限数组
     */
    private static void requestPermissionsInActivity(AppCompatActivity cxt, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(cxt, permissions, requestCode);
    }

    /**
     * 申请权限回调，在activity的onRequestPermissionsResult方法，调用此方法
     *
     * @param cxt          上下文
     * @param permissions  权限数组
     * @param grantResults 申请结果数组@link{ PermissionChecker.PERMISSION_GRANTED}
     * @param listener     申请结果回调
     */
    public static void onPermissionResult(AppCompatActivity cxt, @NonNull String[] permissions, @NonNull int[] grantResults, PermissionCallBack listener) {
        int length = grantResults.length;
        List<Integer> positions = new ArrayList<>();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (grantResults[i] != PermissionChecker.PERMISSION_GRANTED) {
                    positions.add(i);
                }
            }
        }
        if (positions.isEmpty()) {
            listener.onSuccess();
            return;
        }
        progressNoPermission(cxt, listener, permissions, positions, 0);

    }

    /**
     * 判断哪些权限还未通过
     *
     * @param cxt         上下文
     * @param listener    申请结果回调
     * @param permissions 申请权限数组
     * @param positions   申请结果
     */
    private static void progressNoPermission(AppCompatActivity cxt, PermissionCallBack listener, String[] permissions, List<Integer> positions, int i) {
        int index = positions.get(i);
        if (ActivityCompat.shouldShowRequestPermissionRationale(cxt, permissions[index])) {
            listener.onShouldShow();
            return;
        }
        if (i < positions.size() - 1) {
            i++;
            progressNoPermission(cxt, listener, permissions, positions, i);
            return;
        }
        listener.onFailed();
    }
}
