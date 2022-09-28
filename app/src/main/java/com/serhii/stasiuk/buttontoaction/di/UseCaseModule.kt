package com.serhii.stasiuk.buttontoaction.di

import com.serhii.stasiuk.buttontoaction.domain.usecase.FetchButtonPropertiesUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.FetchUserContactsUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.GetButtonActionUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModule: Module = module {
    single { FetchButtonPropertiesUseCase(get()) }
    single { GetButtonActionUseCase() }
    single { FetchUserContactsUseCase(androidContext()) }
}
