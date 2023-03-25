package com.ibrahim.sawitpro.data.model.response


import com.google.gson.annotations.SerializedName

data class BaseParsedResultResponse(
    @SerializedName("IsErroredOnProcessing")
    val isErroredOnProcessing: Boolean?,
    @SerializedName("OCRExitCode")
    val oCRExitCode: Int?,
    @SerializedName("ParsedResults")
    val parsedResults: List<ParsedResultResponse>?,
    @SerializedName("ProcessingTimeInMilliseconds")
    val processingTimeInMilliseconds: String?,
    @SerializedName("SearchablePDFURL")
    val searchablePDFURL: String?,
    @SerializedName("ErrorMessage")
    val errorMessage: List<String>?
)