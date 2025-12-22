package org.example.mycartcalculator.di

import androidx.compose.runtime.staticCompositionLocalOf
import org.example.mycartcalculator.database.CartDatabase
import org.example.mycartcalculator.domain.repository.CartRepository
import org.example.mycartcalculator.domain.usecase.GetHistoryUseCase
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.domain.usecase.SaveCartUseCase
import org.example.mycartcalculator.viewModel.CartViewModel
import org.example.mycartcalculator.viewModel.HistoryViewModel
import org.koin.dsl.module

val commonModule = module {

    factory { ParseReceiptUseCase() }

    factory { RecognizeTextUseCase(get()) }

    single { CartViewModel(get(), get(), get()) }
    single { HistoryViewModel(get()) }

    // Save Cart Sql Delight
    single { CartDatabase(get()) }
    single { CartRepository(get()) }
    factory { SaveCartUseCase(get()) }

    factory { GetHistoryUseCase(get()) }

}

val ProvidedCartViewModel = staticCompositionLocalOf<CartViewModel> {
    error("CartViewModel not provided")
}

val ProvidedHistoryViewModel = staticCompositionLocalOf<HistoryViewModel> {
    error("HistoryViewModel not provided")
}
