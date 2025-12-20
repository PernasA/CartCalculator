package org.example.mycartcalculator.di

import org.example.mycartcalculator.data.MlKitTextRecognitionRepository
import org.example.mycartcalculator.domain.repository.TextRecognitionRepository
import org.example.mycartcalculator.expect.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {

    single<TextRecognitionRepository> {
        MlKitTextRecognitionRepository(androidContext())
    }
    single {
        DatabaseDriverFactory(androidContext()).createDriver()
    }

}
