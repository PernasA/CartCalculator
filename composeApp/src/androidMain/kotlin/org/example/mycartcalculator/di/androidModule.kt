package org.example.mycartcalculator.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.example.mycartcalculator.data.MlKitTextRecognitionRepository
import org.example.mycartcalculator.database.CartDatabase
import org.example.mycartcalculator.domain.dataSource.CartLocalDataSource
import org.example.mycartcalculator.domain.dataSource.CartRemoteDataSource
import org.example.mycartcalculator.domain.repository.CartRepository
import org.example.mycartcalculator.domain.repository.ICartRepository
import org.example.mycartcalculator.domain.repository.TextRecognitionRepository
import org.example.mycartcalculator.expect.DatabaseDriverFactory
import org.example.mycartcalculator.expect.SyncScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {

    // --- ML Kit ---
    single<TextRecognitionRepository> {
        MlKitTextRecognitionRepository()
    }

    // --- DB ---
    single {
        DatabaseDriverFactory(androidContext()).createDriver()
    }

    single {
        CartDatabase(get())
    }

    single {
        CartLocalDataSource(
            queries = get<CartDatabase>().cartQueries
        )
    }

    // --- Network ---
    single {
        HttpClient {
            // engine Android
            install(ContentNegotiation) {
                json()
            }
        }
    }

    single {
        CartRemoteDataSource(
            client = get()
        )
    }

    // --- Repository ---
    single<ICartRepository> {
        CartRepository(
            local = get(),
            remote = get()
        )
    }

    // --- Sync ---
    single {
        SyncScheduler(androidContext())
    }
}
