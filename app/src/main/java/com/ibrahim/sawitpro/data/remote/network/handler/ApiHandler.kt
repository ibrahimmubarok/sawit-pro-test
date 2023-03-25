package com.ibrahim.sawitpro.data.remote.network.handler

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.ibrahim.sawitpro.data.model.response.BaseParsedResultResponse
import com.ibrahim.sawitpro.data.remote.network.exception.ApiErrorException
import com.ibrahim.sawitpro.data.remote.network.exception.NoInternetConnectionException
import com.ibrahim.sawitpro.data.remote.network.exception.UnexpectedErrorException
import com.ibrahim.sawitpro.data.wrapper.DataResource
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException
import java.io.IOException

object ApiHandler {

    private val gson: Gson by inject(Gson::class.java)

    private fun <T> getErrorMessageFromApi(response : T) : String {
        val responseBody = response as ResponseBody
        return try {
            val body = gson.fromJson(responseBody.string(), BaseParsedResultResponse::class.java)
            body.errorMessage?.first() ?: "Error Api!"
        } catch (e: JsonParseException) {
            "Error Api!"
        }
    }

    suspend fun <T> safeNetworkCall(apiCall: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> DataResource.Error(NoInternetConnectionException())
                is HttpException -> {
                    DataResource.Error(ApiErrorException(getErrorMessageFromApi(throwable.response()?.errorBody()), throwable.code()))
                }
                else -> {
                    DataResource.Error(UnexpectedErrorException())
                }
            }
        }
    }
}