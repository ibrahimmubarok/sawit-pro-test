package com.ibrahim.sawitpro.data.repository

import com.ibrahim.sawitpro.data.model.response.BaseParsedResultResponse
import com.ibrahim.sawitpro.data.remote.network.handler.ApiHandler
import com.ibrahim.sawitpro.data.remote.service.ApiService
import com.ibrahim.sawitpro.data.wrapper.DataResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

interface IOcrRepository {
    suspend fun postImageToText(fileImage: MultipartBody.Part): Flow<DataResource<BaseParsedResultResponse>>
}

class OcrRepositoryImpl(private val apiService: ApiService) : IOcrRepository {
    override suspend fun postImageToText(fileImage: MultipartBody.Part): Flow<DataResource<BaseParsedResultResponse>> {
        return flow {
            emit(ApiHandler.safeNetworkCall {
                apiService.postImageToText(fileImage = fileImage)
            })
        }
    }
}