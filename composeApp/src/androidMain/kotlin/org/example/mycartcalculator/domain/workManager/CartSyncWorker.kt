package org.example.mycartcalculator.domain.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.example.mycartcalculator.domain.repository.ICartRepository
import org.koin.mp.KoinPlatform.getKoin

class CartSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    // IMPORTANTE: resolver repo desde DI
    private val repository: ICartRepository by lazy {
        getKoin().get<ICartRepository>()
    }

    override suspend fun doWork(): Result =
        runCatching {
            repository.syncPending()
        }.fold(
            onSuccess = { Result.success() },
            onFailure = { Result.retry() }
        )
}