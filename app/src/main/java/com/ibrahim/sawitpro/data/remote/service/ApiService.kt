package com.ibrahim.sawitpro.data.remote.service

import com.ibrahim.sawitpro.data.model.response.BaseParsedResultResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("parse/image")
    suspend fun postImageToText(
        @Part fileImage: MultipartBody.Part
    ): BaseParsedResultResponse
}