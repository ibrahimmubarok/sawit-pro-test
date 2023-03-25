package com.ibrahim.sawitpro.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.ibrahim.sawitpro.data.remote.network.NetworkClient
import com.ibrahim.sawitpro.data.repository.IOcrRepository
import com.ibrahim.sawitpro.data.repository.OcrRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppModules {
    val remoteModule = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { NetworkClient(get()) }
    }

    val repositoryModule = module {
        single<IOcrRepository> { OcrRepositoryImpl(get()) }
    }

    val useCaseModule = module {

    }

    val viewModelModule = module {

    }

    val commonModule = module {
        single { Gson() }
    }
}