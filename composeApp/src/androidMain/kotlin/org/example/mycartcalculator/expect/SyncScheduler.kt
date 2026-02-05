package org.example.mycartcalculator.expect

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import org.example.mycartcalculator.domain.workManager.CartSyncWorker
import java.util.concurrent.TimeUnit

actual class SyncScheduler(
    private val context: Context
) {
    actual fun scheduleCartSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<CartSyncWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                30,
                TimeUnit.SECONDS
            )
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "cart_sync",
                ExistingWorkPolicy.KEEP,
                request
            )
    }
}
