package com.jeanmeza.dispensapp.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.jeanmeza.dispensapp.MainActivity

class ExpiryNotificationManager(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "EXPIRY_NOTIFICATIONS"
        const val CHANNEL_NAME = "Item Expiry Notifications"
        const val NOTIFICATION_ID_EXPIRING_SOON = 1001
        const val NOTIFICATION_ID_EXPIRED = 1002
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for items that are expiring or have expired"
            enableVibration(true)
            enableLights(true)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showExpiringSoonNotification(itemName: String, daysUntilExpiry: Int) {
        if (!hasNotificationPermission()) {
            Log.w(
                "ExpiryNotificationManager",
                "No notification permission for expiring soon notification"
            )
            return
        }

        try {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert) // Using system icon as fallback
                .setContentTitle("Item Expiring Soon")
                .setContentText("$itemName expires in $daysUntilExpiry day${if (daysUntilExpiry != 1) "s" else ""}")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            // Use unique notification ID for each item to avoid overwriting
            val notificationId = NOTIFICATION_ID_EXPIRING_SOON + itemName.hashCode()
            NotificationManagerCompat.from(context).notify(notificationId, notification)
            Log.d("ExpiryNotificationManager", "Shown expiring soon notification for $itemName")
        } catch (e: Exception) {
            Log.e("ExpiryNotificationManager", "Error showing expiring soon notification", e)
        }
    }

    fun showExpiredNotification(itemName: String) {
        if (!hasNotificationPermission()) {
            Log.w(
                "ExpiryNotificationManager",
                "No notification permission for expired notification"
            )
            return
        }

        try {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert) // Using system icon as fallback
                .setContentTitle("Item Has Expired")
                .setContentText("$itemName has expired today")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            // Use unique notification ID for each item to avoid overwriting
            val notificationId = NOTIFICATION_ID_EXPIRED + itemName.hashCode()
            NotificationManagerCompat.from(context).notify(notificationId, notification)
            Log.d("ExpiryNotificationManager", "Shown expired notification for $itemName")
        } catch (e: Exception) {
            Log.e("ExpiryNotificationManager", "Error showing expired notification", e)
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}