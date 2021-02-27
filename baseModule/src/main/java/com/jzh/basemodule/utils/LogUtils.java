package com.jzh.basemodule.utils;

import android.util.Log;


/**
 * @author jinzhenhua
 * @date 2019/4/14
 */
public class LogUtils {

    private LogUtils() {
    }

    private static final Boolean DEBUG = true;

    public static void i(String tag, String content) {
        if (DEBUG) {
            Log.i(tag, buildContent(content));
        }
    }

    public static void v(String tag, String content) {
        if (DEBUG) {
            Log.v(tag, buildContent(content));
        }
    }

    public static void d(String tag, String content) {
        if (DEBUG) {
            Log.d(tag, buildContent(content));
        }
    }

    public static void w(String tag, String content) {
        if (DEBUG) {
            Log.w(tag, buildContent(content));
        }
    }

    public static void e(String tag, String content) {
        if (DEBUG) {
            Log.e(tag, buildContent(content));
        }
    }

    public static void e(String tag, Throwable e) {
        if (DEBUG) {
            Log.e(tag, "e: ", e);
        }
    }

//    private static String buildContent(String content) {
//        return content + ",当前线程:" + Thread.currentThread().getName();
//    }

    private static String buildContent(String content) {
        StackTraceElement targetStackTraceElement = getStackTraceElement();
        StringBuilder stringBuilder = new StringBuilder();
        if (null != targetStackTraceElement) {
            stringBuilder.append("位置-> (" + targetStackTraceElement.getFileName() + ":" + targetStackTraceElement.getLineNumber() + ")" + " ->" + targetStackTraceElement.getMethodName() + "\n");
        }
        stringBuilder.append(content);
        return stringBuilder.toString();
    }

    private static StackTraceElement getStackTraceElement() {
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(LogUtils.class.getName());
            if (shouldTrace && !isLogMethod) {
                return stackTraceElement;
            }
            shouldTrace = isLogMethod;
        }
        return null;
    }

}
