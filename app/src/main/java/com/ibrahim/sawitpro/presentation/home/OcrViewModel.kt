package com.ibrahim.sawitpro.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahim.sawitpro.data.wrapper.ViewResource
import com.ibrahim.sawitpro.domain.PostImageToTextUseCase
import com.ibrahim.sawitpro.domain.response.ParsedResult
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class OcrViewModel(
    private val postImageToTextUseCase: PostImageToTextUseCase,
) : ViewModel() {

    private val _imageResult = MutableLiveData<ViewResource<List<ParsedResult>>>()
    val imageResult: LiveData<ViewResource<List<ParsedResult>>> = _imageResult

    fun postImageToText(fileImage: MultipartBody.Part) {
        viewModelScope.launch {
            postImageToTextUseCase(fileImage).collect { result ->
                _imageResult.postValue(result)
            }
        }
    }
}