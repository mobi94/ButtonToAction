package com.serhii.stasiuk.buttontoaction.di

import com.serhii.stasiuk.buttontoaction.presentation.fragment.contacts.ContactsFragmentViewModel
import com.serhii.stasiuk.buttontoaction.presentation.fragment.main.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { MainFragmentViewModel(get(), get()) }
    viewModel { ContactsFragmentViewModel(get()) }
}