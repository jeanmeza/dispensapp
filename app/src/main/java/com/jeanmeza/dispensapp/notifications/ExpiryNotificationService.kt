package com.jeanmeza.dispensapp.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jeanmeza.dispensapp.data.local.entities.asModel
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpiryNotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val itemRepository: ItemRepository
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    suspend fun scheduleExpiryChecks() {
        Log.d("ExpiryNotificationService", "Scheduling expiry checks")

        try {
            val items = itemRepository.getAllItems().asModel()
            val today = LocalDate.now()

            items.forEach { item ->
                item.expiryDate?.let { expiryInstant ->
                    val expiryDate = Instant.ofEpochMilli(expiryInstant.toEpochMilliseconds())
                        .atZone(ZoneId.systemDefault()).toLocalDate()

                    val daysUntilExpiry = ChronoUnit.DAYS.between(today, expiryDate).toInt()

                    when (daysUntilExpiry) {
                        7 -> {
                            // Schedule notification for 7 days before expiry
                            scheduleNotification(
                                item.name,
                                "expiring_soon",
                                daysUntilExpiry,
                                System.currentTimeMillis() + 1000 // Schedule immediately for testing, or calculate proper time
                            )
                        }

                        0 -> {
                            // Schedule notification for expiry day
                            scheduleNotification(
                                item.name,
                                "expired",
                                0,
                                System.currentTimeMillis() + 1000
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ExpiryNotificationService", "Error scheduling expiry checks", e)
        }
    }

    private fun scheduleNotification(
        itemName: String,
        notificationType: String,
        daysUntilExpiry: Int,
        triggerTime: Long
    ) {
        val intent = Intent(context, ExpiryNotificationReceiver::class.java).apply {
            putExtra("item_name", itemName)
            putExtra("notification_type", notificationType)
            putExtra("days_until_expiry", daysUntilExpiry)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            itemName.hashCode(), // Use item name hash as unique ID
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
            Log.d(
                "ExpiryNotificationService",
                "Scheduled $notificationType notification for $itemName"
            )
        } catch (e: SecurityException) {
            Log.e("ExpiryNotificationService", "Permission denied for scheduling exact alarm", e)
        }
    }
}