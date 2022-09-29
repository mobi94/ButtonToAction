package com.serhii.stasiuk.buttontoaction.di

import com.serhii.stasiuk.buttontoaction.domain.usecase.FetchUserContactsUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.CheckButtonCoolDownUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.FetchButtonPropertiesUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.FindButtonActionUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.cool_down.GetButtonCoolDownUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.cool_down.SaveButtonCoolDownUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModule: Module = module {
    single { FetchButtonPropertiesUseCase(get()) }
    single { FetchUserContactsUseCase(androidContext()) }
    single { FindButtonActionUseCase(get()) }
    single { SaveButtonCoolDownUseCase(get()) }
    single { GetButtonCoolDownUseCase(get()) }
    single { CheckButtonCoolDownUseCase(get()) }
}
