package com.ibrahim.sawitpro.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.ibrahim.sawitpro.data.remote.network.NetworkClient
import com.ibrahim.sawitpro.data.remote.service.ApiService
import com.ibrahim.sawitpro.data.repository.IOcrRepository
import com.ibrahim.sawitpro.data.repository.OcrRepositoryImpl
import com.ibrahim.sawitpro.domain.PostImageToTextUseCase
import com.ibrahim.sawitpro.presentation.home.OcrViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules {
    val remoteModule = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { NetworkClient(get()) }
        single<ApiService> { get<NetworkClient>().create() }
    }

    val repositoryModule = module {
        single<IOcrRepository> { OcrRepositoryImpl(get()) }
    }

    val useCaseModule = module {
        single { PostImageToTextUseCase(get(), Dispatchers.IO) }
    }

    val viewModelModule = module {
        viewModel { OcrViewModel(get()) }
    }

    val commonModule = module {
        single { Gson() }
    }
}