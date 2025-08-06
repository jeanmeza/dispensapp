package com.jeanmeza.dispensapp.initialiser

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.jeanmeza.dispensapp.worker.ExpiryCheckWorker

object ExpiryCheck {
    fun initialise(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(
                uniqueWorkName = EXPIRY_CHECK_WORK_NAME,
                existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE,
                request = ExpiryCheckWorker.startUpSyncWork()
            )
        }
    }
}

private const val EXPIRY_CHECK_WORK_NAME = "EXPIRY_CHECK_WORK_NAME"