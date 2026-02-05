package org.example.mycartcalculator.di

import androidx.compose.runtime.staticCompositionLocalOf
import org.example.mycartcalculator.domain.usecase.GetHistoryUseCase
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.domain.usecase.SaveCartUseCase
import org.example.mycartcalculator.domain.usecase.interfaces.IGetHistoryUseCase
import org.example.mycartcalculator.domain.usecase.interfaces.IParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.interfaces.IRecognizeTextUseCase
import org.example.mycartcalculator.domain.usecase.interfaces.ISaveCartUseCase
import org.example.mycartcalculator.viewModel.CartViewModel
import org.example.mycartcalculator.viewModel.HistoryViewModel
import org.koin.dsl.module

val commonModule = module {

    factory { CartViewModel(get(), get(), get()) }
    factory { HistoryViewModel(get()) }

    // --- UseCases ---
    factory<IParseReceiptUseCase> { ParseReceiptUseCase() }
    factory<IRecognizeTextUseCase> { RecognizeTextUseCase(get()) }
    factory<ISaveCartUseCase> { SaveCartUseCase(get(), get()) }
    factory<IGetHistoryUseCase> { GetHistoryUseCase(get()) }

}

val ProvidedCartViewModel = staticCompositionLocalOf<CartViewModel> {
    error("CartViewModel not provided")
}

val ProvidedHistoryViewModel = staticCompositionLocalOf<HistoryViewModel> {
    error("HistoryViewModel not provided")
}
