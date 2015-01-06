package com.mayi.julyup.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mayi.julyup.R;

/**
 * @author mayi 2014-7-11 上午10:25:33 document:
 */

public final class NotificationHelper {

    public static final int ID_PROGRESS = 3;

    public static final int ID_DOWNLOAD_INFO = 4;

    static NotificationManager mNotificationMgr;

    private NotificationHelper() {
    }

    /**
     * <清除通知>
     *
     * @param context
     * @param id
     * @return void
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static synchronized void clearNotification(Context context, int id) {
        if (mNotificationMgr == null) {
            mNotificationMgr = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }
        mNotificationMgr.cancel(ID_DOWNLOAD_INFO);
    }

    private static synchronized void clearAll() {
        mNotificationMgr.cancel(ID_PROGRESS);
        mNotificationMgr.cancel(ID_DOWNLOAD_INFO);
    }

    /**
     * <下载进度>
     *
     * @param context
     * @param sum     可以设置为-1，表示不显示具体数目
     * @return void
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static synchronized void notifyProgress(Context context,
                                                   String message, int progress) {

        if (mNotificationMgr == null) {
            mNotificationMgr = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }
        // 先取消原来的通知
        // clearAll();

        Notification notification = new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.contentView = new RemoteViews(context.getPackageName(),
                R.layout.my_download_progress);
        ;
        notification.contentView.setTextViewText(R.id.tv_title, message);
        notification.contentView.setTextViewText(R.id.tv_progress,
                context.getString(R.string.download_progress, progress));
        notification.contentView.setProgressBar(R.id.progress_bar, 100,
                progress, false);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, new Intent(context, context
                        .getApplicationContext().getClass()), 0);
        notification.contentIntent = pendingIntent;

        mNotificationMgr.notify(ID_PROGRESS, notification);
    }

}
