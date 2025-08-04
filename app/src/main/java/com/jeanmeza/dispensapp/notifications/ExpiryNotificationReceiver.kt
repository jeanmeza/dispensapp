package com.jeanmeza.dispensapp.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExpiryNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var expiryNotificationService: ExpiryNotificationService

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val itemName = intent.getStringExtra("item_name") ?: return
        val notificationType = intent.getStringExtra("notification_type") ?: return
        val daysUntilExpiry = intent.getIntExtra("days_until_expiry", 0)

        Log.d(
            "ExpiryNotificationReceiver",
            "Received notification for $itemName, type: $notificationType"
        )

        val notificationManager = ExpiryNotificationManager(context)

        when (notificationType) {
            "expiring_soon" -> {
                notificationManager.showExpiringSoonNotification(itemName, daysUntilExpiry)
            }

            "expired" -> {
                notificationManager.showExpiredNotification(itemName)
            }
        }

        // Schedule the next check
        CoroutineScope(Dispatchers.IO).launch {
            expiryNotificationService.scheduleExpiryChecks()
        }
    }
}