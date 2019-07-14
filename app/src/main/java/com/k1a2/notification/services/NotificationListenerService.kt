package com.k1a2.notification.services

import android.content.Intent
import android.content.IntentFilter
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.TextUtils
import android.util.Log


class NotificationListenerService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName.toString()
        val intent:Intent = Intent("android.k1a2.action.broadcastrecive")
        val filter = IntentFilter("android.k1a2.action.broadcastrecive")
        this.registerReceiver(BroadCastReciver(), filter)
        sendBroadcast(intent)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.e("NotificationListener", "[snowdeer] onNotificationRemoved() - $sbn")
    }

}