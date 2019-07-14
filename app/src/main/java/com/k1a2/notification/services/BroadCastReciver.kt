package com.k1a2.notification.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadCastReciver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "ㅗ", Toast.LENGTH_SHORT).show()
    }
}