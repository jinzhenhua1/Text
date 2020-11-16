package com.jzh.basemodule.utils.compression;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>网络相关的工具类<p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/9/8 9:23
 */
public class NetWorkUtil {

    private NetWorkUtil() {
    }

    /**
     * 验证IP是否正确
     *
     * @param ip ip 地址，如：192.168.1.1
     * @return true-IP正确
     */
    public static boolean verifyIp(String ip) {
        if (TextUtils.isEmpty(ip)) {
            return false;
        }

        //正则表达式,限定输入格式
        String ipMatcher = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|[1-9])\\."
                + "(25[0-5]|2[0-4]\\d|1\\d{1,2}|\\d{2}|\\d)\\."
                + "(25[0-5]|2[0-4]\\d|1\\d{1,2}|\\d{2}|\\d)\\."
                + "(25[0-5]|2[0-4]\\d|1\\d{1,2}|\\d{2}|\\d)";

        Pattern p = Pattern.compile(ipMatcher);
        Matcher m = p.matcher(ip);
        return m.matches();
    }
}
