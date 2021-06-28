package com.jzh.basemodule.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import com.securepreferences.SecurePreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * <p>SecurePreferences帮助类，应该以单例的方式访问.</p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/4/2 17:11
 */
public class SecurePreferencesUtil {

    private SecurePreferences mPreferences;
    private static SecurePreferencesUtil securePreferencesUtil;

    public static synchronized SecurePreferencesUtil getInstance(Context context) {
        if (securePreferencesUtil == null) {
            securePreferencesUtil = new SecurePreferencesUtil(context.getApplicationContext());
        }
        return securePreferencesUtil;
    }

    /**
     * Default constructor
     *
     * @param context Application Context
     */
    private SecurePreferencesUtil(Context context) {
        mPreferences = new SecurePreferences(context, "cdtye@W3369", "cdtye_prefs");
    }

    public void setInteger(String key, int value) {
        SecurePreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInteger(String key) {
        return mPreferences.getInt(key, 0);
    }

    public void setString(String key, String value) {
        SecurePreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    @SuppressWarnings("unused")
    public void setBoolean(String key, boolean value) {
        SecurePreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @SuppressWarnings("unused")
    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    @SuppressWarnings("unused")
    public void setFloat(String key, float value) {
        SecurePreferences.Editor editor = mPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    @SuppressWarnings("unused")
    public float getFloat(String key) {
        return mPreferences.getFloat(key, 0.0f);
    }

    @SuppressWarnings("unused")
    public void setLong(String key, long value) {
        SecurePreferences.Editor editor = mPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    @SuppressWarnings("unused")
    public long getLong(String key) {
        return mPreferences.getLong(key, 0L);
    }

    /**
     * 保存对象
     *
     * @param key     键
     * @param obj     要保存的对象（Serializable的子类）
     * @param <T>     泛型定义
     */
    public <T extends Serializable> void saveObject(String key, T obj) {
        try {
            put(key, obj);
        } catch (Exception e) {
            Log.e("SharedPrefUtil", "saveObject error");
        }
    }

    /**
     * 获取对象
     *
     * @param key     键
     * @param <T>     指定泛型
     * @return 泛型对象
     */
    public <T extends Serializable> T getObject(String key) {
        try {
            return (T) get(key);
        } catch (Exception e) {
            Log.e("SharedPrefUtil", "getObject error");
        }
        return null;
    }

    /**
     * 存储对象
     */
    private void put(String key, Object obj)
            throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        setString(key, objectStr);
    }

    /**
     * 获取对象
     */
    private Object get(String key)
            throws IOException, ClassNotFoundException {
        String wordBase64 = getString(key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }
}
