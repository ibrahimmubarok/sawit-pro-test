package com.ibrahim.sawitpro.data.model.response


import com.google.gson.annotations.SerializedName

data class ParsedResultResponse(
    @SerializedName("ErrorDetails")
    val errorDetails: String,
    @SerializedName("ErrorMessage")
    val errorMessage: String,
    @SerializedName("FileParseExitCode")
    val fileParseExitCode: Int,
    @SerializedName("ParsedText")
    val parsedText: String,
    @SerializedName("TextOrientation")
    val textOrientation: String,
)