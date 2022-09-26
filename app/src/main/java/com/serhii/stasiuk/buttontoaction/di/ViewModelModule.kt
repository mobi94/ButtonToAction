package com.serhii.stasiuk.buttontoaction.di

import com.serhii.stasiuk.buttontoaction.presentation.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { MainActivityViewModel(get(), get()) }
}