package com.ibrahim.sawitpro.domain

import com.ibrahim.sawitpro.data.repository.IOcrRepository
import com.ibrahim.sawitpro.data.wrapper.ViewResource
import com.ibrahim.sawitpro.domain.base.BaseUseCase
import com.ibrahim.sawitpro.domain.response.ParsedResult
import com.ibrahim.sawitpro.domain.response.toParsedResult
import com.ibrahim.sawitpro.utils.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class PostImageToTextUseCase(
    private val repository: IOcrRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<MultipartBody.Part, List<ParsedResult>>(dispatcher) {

    override suspend fun execute(param: MultipartBody.Part?): Flow<ViewResource<List<ParsedResult>>> {
        return flow {
            emit(ViewResource.Loading())
            param?.let { fileImage ->
                repository.postImageToText(fileImage).collect { result ->
                    result.suspendSubscribe(
                        doOnSuccess = { data ->
                            if (data.payload?.parsedResults?.isEmpty() == true) {
                                emit(ViewResource.Empty())
                            } else {
                                emit(
                                    ViewResource.Success(
                                        data.payload?.parsedResults?.map { it.toParsedResult() }
                                            .orEmpty()
                                    )
                                )
                            }
                        },
                        doOnError = { error ->
                            emit(ViewResource.Error(error.exception))
                        }
                    )
                }
            } ?: throw java.lang.IllegalStateException("Param Required!")
        }
    }
}