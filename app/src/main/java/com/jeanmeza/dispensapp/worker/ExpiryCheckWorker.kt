package com.jeanmeza.dispensapp.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.jeanmeza.dispensapp.data.local.entities.asModel
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import com.jeanmeza.dispensapp.initialiser.ExpiryCheckConstraints
import com.jeanmeza.dispensapp.notifications.ExpiryNotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

private const val TAG = "ExpiryCheckWorker"

@HiltWorker
class ExpiryCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val itemRepository: ItemRepository,
) : CoroutineWorker(context, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting expiry check")
            if (!hasNotificationPermission()) {
                Log.w(TAG, "No notification permission granted")
                Result.success()
            }
            checkAndNotifyExpiringItems()
            Log.d(TAG, "Expiry check completed")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error checking expiry", e)
            Result.failure()
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Permission not required for older versions
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private suspend fun checkAndNotifyExpiringItems() {
        val items = itemRepository.getAllItems().asModel()
        val today = LocalDate.now()
        val notificationManager = ExpiryNotificationManager(applicationContext)

        Log.d("ExpiryCheckWorker", "Checking ${items.size} items for expiry")

        items.forEach { item ->
            item.expiryDate?.let { expiryInstant ->
                val expiryDate = Instant
                    .ofEpochMilli(expiryInstant.toEpochMilliseconds())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

                val daysUntilExpiry = ChronoUnit.DAYS.between(today, expiryDate).toInt()

                Log.d(
                    "ExpiryCheckWorker",
                    "Item: ${item.name}, Days until expiry: $daysUntilExpiry"
                )

                when (daysUntilExpiry) {
                    7 -> {
                        Log.d(
                            "ExpiryCheckWorker",
                            "Showing expiring soon notification for ${item.name}"
                        )
                        notificationManager.showExpiringSoonNotification(item.name, daysUntilExpiry)
                    }

                    0 -> {
                        Log.d("ExpiryCheckWorker", "Showing expired notification for ${item.name}")
                        notificationManager.showExpiredNotification(item.name)
                    }

                    in 1..6 -> {
                        // Optional: Show notifications for items expiring in 1-6 days
                        Log.d(
                            "ExpiryCheckWorker",
                            "Item ${item.name} expires in $daysUntilExpiry days"
                        )
                        notificationManager.showExpiringSoonNotification(item.name, daysUntilExpiry)
                    }
                }
            }
        }
    }

    companion object {
        fun startUpSyncWork() =
            PeriodicWorkRequestBuilder<DelegatingWorker>(
                repeatInterval = 5L,
                repeatIntervalTimeUnit = TimeUnit.MINUTES
            )
                .setConstraints(ExpiryCheckConstraints)
                .setInputData(ExpiryCheckWorker::class.delegatedData())
                .build()
    }
}
