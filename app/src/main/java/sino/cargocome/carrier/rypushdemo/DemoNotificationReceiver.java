package sino.cargocome.carrier.rypushdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by Jorgejie on 2017/10/31.
 */

public class DemoNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
//        if (!TextUtils.isEmpty(pushNotificationMessage.getPushId())) {
//            RongPushClient.recordNotificationEvent(pushNotificationMessage.getPushId());
//        }
//        show(context, pushNotificationMessage);
        return false;
    }


    public void show(Context context, PushNotificationMessage pushNotificationMessage) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.message);
        rv.setTextViewText(R.id.tv, pushNotificationMessage.getPushContent());
        rv.setTextViewText(R.id.tv_pic, "智运通");


        rv.setTextColor(R.id.tv, context.getResources().getColor(R.color.colorPrimary));
        rv.setTextColor(R.id.tv_pic, context.getResources().getColor(R.color.colorPrimary));
        //修改自定义布局中的图片(两种方法)
//        rv.setImageViewResource(R.id.iv, R.mipmap.ic_launcher);
        builder.setContent(rv);
        //设置点击通知跳转页面后，通知消失
        builder.setAutoCancel(true);
        // 需要VIBRATE权限
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setPriority(Notification.PRIORITY_DEFAULT);

        Intent intent = new Intent(context, JumpActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            builder.setCustomBigContentView(rv);
        }
        builder.setContentIntent(pi);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, notification);
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        if (!TextUtils.isEmpty(pushNotificationMessage.getPushId())) {
            RongPushClient.recordNotificationEvent(pushNotificationMessage.getPushId());
        }
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri.Builder builder = Uri.parse("rong://" + context.getPackageName()).buildUpon();

        RongPushClient.ConversationType type = pushNotificationMessage.getConversationType();

        builder.appendPath("conversation").appendPath(type.getName())
                .appendQueryParameter("content", pushNotificationMessage.getPushContent());
        Uri uri = builder.build();
        intent.setData(uri);
        RongPushClient.clearAllNotifications(context);
        context.startActivity(intent);
        return true;
    }
}
