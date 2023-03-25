package com.ibrahim.sawitpro.domain.response

import android.os.Parcelable
import com.ibrahim.sawitpro.data.model.response.ParsedResultResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParsedResult(
    val errorDetails: String,
    val fileParseExitCode: Int?,
    val parsedText: String,
    val textOrientation: String,
) : Parcelable

fun ParsedResultResponse.toParsedResult(): ParsedResult {
    return ParsedResult(
        errorDetails = errorDetails.orEmpty(),
        fileParseExitCode = fileParseExitCode,
        parsedText = parsedText.orEmpty(),
        textOrientation = textOrientation.orEmpty(),
    )
}