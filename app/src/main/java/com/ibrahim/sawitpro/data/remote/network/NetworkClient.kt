package com.ibrahim.sawitpro.data.remote.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient(
    val chuckerInterceptor: ChuckerInterceptor,
) {

    inline fun <reified I> create(): I {

        //okhttp
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(Interceptor {chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("apikey", "K81072151088957")
                    .build()

                return@Interceptor chain.proceed(newRequest)
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        //retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.ocr.space/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(I::class.java)
    }
}