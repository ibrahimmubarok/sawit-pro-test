package com.ibrahim.sawitpro

import android.app.Application
import com.ibrahim.sawitpro.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                AppModules.remoteModule,
                AppModules.repositoryModule,
                AppModules.useCaseModule,
                AppModules.viewModelModule,
                AppModules.commonModule
            )
        }
    }
}