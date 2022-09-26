package com.serhii.stasiuk.buttontoaction.di

import android.app.Application
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.serhii.stasiuk.buttontoaction.BuildConfig
import com.serhii.stasiuk.buttontoaction.data.datasource.remote.ServerApi
import com.serhii.stasiuk.buttontoaction.utils.Logger
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CACHE_SIZE = 10_000_000L
private const val TIMEOUT = 10L

val networkModule: Module = module {
    factory { provideHttpCache(get()) }
    factory { provideOkhttpClientBuilder(get()) }
    factory { provideRetrofitBuilder(get()) }

    factory { provideOkhttpClient(get()) }
    single { provideServerRetrofit(get(), get()) }
    single { provideServerApi(get()) }
}


fun provideHttpCache(application: Application): Cache {
    val cacheSize = CACHE_SIZE
    return Cache(application.cacheDir, cacheSize)
}

private fun provideOkhttpClientBuilder(cache: Cache): OkHttpClient.Builder {
    return OkHttpClient().newBuilder().apply {
        cache(cache)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
                Logger.d("OkHttp", message)
            }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(httpLoggingInterceptor)
        }
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    }
}

private fun provideOkhttpClient(builder: OkHttpClient.Builder): OkHttpClient = builder.build()


private fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
}

private fun provideServerRetrofit(builder: Retrofit.Builder, okHttpClient: OkHttpClient): Retrofit {
    return builder.client(okHttpClient)
        .baseUrl(BuildConfig.SERVER_URL)
        .build()
}

private fun provideServerApi(retrofit: Retrofit): ServerApi {
    return retrofit.create(ServerApi::class.java)
}