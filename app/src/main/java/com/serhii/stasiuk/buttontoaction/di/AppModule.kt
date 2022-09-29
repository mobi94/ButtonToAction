package com.serhii.stasiuk.buttontoaction.di

import androidx.preference.PreferenceManager
import com.serhii.stasiuk.buttontoaction.data.datasource.local.Prefs
import com.serhii.stasiuk.buttontoaction.data.datasource.local.PrefsImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single<Prefs> { PrefsImpl(get(), get()) }
}