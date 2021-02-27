package com.jzh.pusher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.jzh.pusher.bean.PushData;
import com.jzh.pusher.utils.Logger;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 *
 * @author mayixiang
 */
public class MiMessageReceiver extends PushMessageReceiver {
    public static final String BRAND_MI = "XIAOMI";

    private String mRegId;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;


    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Logger.d("onReceivePassThroughMessage is called. " + message.toString());
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        if (!TextUtils.isEmpty(message.getContent())) {
            handPushMessage(context, message.getContent(), 1, "");
        }



    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        Logger.d("onNotificationMessageClicked is called. " + message.toString());
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }

        if (!TextUtils.isEmpty(message.getContent())) {
            handPushMessage(context, message.getContent(), 1, "");
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        Logger.d("onNotificationMessageArrived is called. " + message.toString());
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        Logger.d("onCommandResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();

        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
//                log = context.getString(R.string.register_success);
            } else {
//                log = context.getString(R.string.register_fail);
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
//                log = context.getString(R.string.set_alias_success, mAlias);
            } else {
//                log = context.getString(R.string.set_alias_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
//                log = context.getString(R.string.unset_alias_success, mAlias);
            } else {
//                log = context.getString(R.string.unset_alias_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
//                log = context.getString(R.string.set_account_success, mAccount);
            } else {
//                log = context.getString(R.string.set_account_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
//                log = context.getString(R.string.unset_account_success, mAccount);
            } else {
//                log = context.getString(R.string.unset_account_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
//                log = context.getString(R.string.subscribe_topic_success, mTopic);
            } else {
//                log = context.getString(R.string.subscribe_topic_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
//                log = context.getString(R.string.unsubscribe_topic_success, mTopic);
            } else {
//                log = context.getString(R.string.unsubscribe_topic_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
//                log = context.getString(R.string.set_accept_time_success, mStartTime, mEndTime);
            } else {
//                log = context.getString(R.string.set_accept_time_fail, message.getReason());
            }
        } else {
            log = message.getReason();
        }
        if (!TextUtils.isEmpty(mRegId)) {
            handPushMessage(context, "", 0, mRegId);
        };
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        Logger.d("onReceiveRegisterResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
//                log = context.getString(R.string.register_success);
            } else {
//                log = context.getString(R.string.register_fail);
            }
        } else {
            log = message.getReason();
        }
    }

    @Override
    public void onRequirePermissions(Context context, String[] permissions) {
        super.onRequirePermissions(context, permissions);
        Logger.d("onRequirePermissions is called. need permission" + arrayToString(permissions));
//
//        if (Build.VERSION.SDK_INT >= 23 && context.getApplicationInfo().targetSdkVersion >= 23) {
//            Intent intent = new Intent();
//            intent.putExtra("permissions", permissions);
//            intent.setComponent(new ComponentName(context.getPackageName(), PermissionActivity.class.getCanonicalName()));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//            context.startActivity(intent);
//        }
    }

    @SuppressLint("SimpleDateFormat")
    private static String getSimpleDate() {
        return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
    }

    public String arrayToString(String[] strings) {
        String result = " ";
        for (String str : strings) {
            result = result + str + " ";
        }
        return result;
    }

    /**
     *
     * @param context
     * @param receivePushData
     * @param action : 0通知推送，１表示透传
     */
    public void handPushMessage(Context context, String receivePushData, int action, String token){
        //逻辑分析：
        //1. 如果进程不存活，直接重新启动应用
        //2. 如果进程存活，分为两种情况：
        //    情况1：进程存活，但Task栈已经空了,此时需要重新启动应用,比如用户点击Back键退出应用，但进程还没有被系统回收
        //    情况2：进程存活，Task 栈不为空，也分两种情况：
        //            1：应用在前台，不做任何处理
        //            2：应用在后台，将应用切换到前台
        if (MiProcessCheck.isAppAlive(context, context.getPackageName())) {
            Logger.d( "the app process is alive,"+context.getPackageName());
            if (MiProcessCheck.isTaskEmpty(context)){
                // 任务栈为空，重新启动app
                Logger.d( "the app process is alive, task is empty");

                MiProcessCheck.reOpenApp(context, getPushData(receivePushData, action).toJson(), BRAND_MI);
            }else{
                // 任务栈不为空，将后台应用放到前台
                Logger.d( "the app process is alive, task is not empty");
                MiProcessCheck.setTopApp(context);
                passMessage(token, receivePushData, action);
            }
        } else {
            // 任务栈为空，重新启动app
            Logger.d( "the app process is dead");
            MiProcessCheck.reOpenApp(context, getPushData(receivePushData, action).toJson(), BRAND_MI);
        }
    }

    PushData getPushData(String message, int action) {
        PushData pushData = new PushData();
        pushData.setType(BRAND_MI);
        pushData.setAction(action);
        pushData.setMessage(message);
        return pushData;
    }

    private void passMessage(String token, String data, int action) {
        PushData pushData = new PushData();
        pushData.setType(BRAND_MI);
        pushData.setToken(token);
        pushData.setAction(action);
        pushData.setMessage(data);
//        HWPushActivity.handler.postDelayed(() -> HWPushActivity.handler.sendMessage(HWPushActivity.createMessage(pushData)), 1 * 1000);
    }
}
