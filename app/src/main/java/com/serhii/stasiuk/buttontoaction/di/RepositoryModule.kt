package com.serhii.stasiuk.buttontoaction.di


import com.serhii.stasiuk.buttontoaction.data.repository.ServerRepositoryImpl
import com.serhii.stasiuk.buttontoaction.data.repository.SettingsRepositoryImpl
import com.serhii.stasiuk.buttontoaction.domain.repository.ServerRepository
import com.serhii.stasiuk.buttontoaction.domain.repository.SettingsRepository
import org.koin.core.module.Module
import org.koin.dsl.module


val repositoryModule: Module = module {
    single<ServerRepository> { ServerRepositoryImpl(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}