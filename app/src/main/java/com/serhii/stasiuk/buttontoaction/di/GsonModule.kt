package com.serhii.stasiuk.buttontoaction.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val gsonModule = module {
    single { provideGsonBuilder() }
    single { provideGson(get()) }
}

private fun provideGsonBuilder(): GsonBuilder {
    return GsonBuilder()
        .setLenient()
        .enableComplexMapKeySerialization()
}

private fun provideGson(builder: GsonBuilder): Gson {
    return builder.create()
}
