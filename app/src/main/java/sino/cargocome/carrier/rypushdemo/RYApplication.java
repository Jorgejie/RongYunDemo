package sino.cargocome.carrier.rypushdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;

/**
 * Created by Jorgejie on 2017/10/31.
 */

public class RYApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            //米push
            try {
                //华为推送配置
                RongPushClient.registerHWPush(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RongPushClient.registerMiPush(this, "2882303761517633128", "5671763391128");

            RongIM.init(this);


            /**
             * 小米推送所需
             * 发送通知。如果使用 IMLib 开发，当应用在后台需要弹后台通知时，可以直接调用此函数弹出通知。
             * 未知何时应用
             * @param context             上下文
             * @param notificationMessage 融云对外公开的通知消息。
             */
//        public static void sendNotification(Context context, PushNotificationMessage notificationMessage) {}
        }

    }
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
