package com.jeanmeza.dispensapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jeanmeza.dispensapp.notifications.ExpiryNotificationService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ExpiryCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val expiryNotificationService: ExpiryNotificationService
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            expiryNotificationService.scheduleExpiryChecks()
            Result.success()
        } catch (e: Exception) {
            Log.e("ExpiryCheckWorker", "Failed to schedule expiry checks", e)
            Result.retry()
        }
    }

}
