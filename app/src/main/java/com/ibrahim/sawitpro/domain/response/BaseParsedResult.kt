package com.ibrahim.sawitpro.domain.response

import com.ibrahim.sawitpro.data.model.response.BaseParsedResultResponse

data class BaseParsedResult(
    val isErroredOnProcessing: Boolean?,
    val oCRExitCode: Int?,
    val parsedResults: List<ParsedResult>,
    val processingTimeInMilliseconds: String,
    val searchablePDFURL: String,
)

fun BaseParsedResultResponse.toBaseParsedResult(): BaseParsedResult {
    return BaseParsedResult(
        isErroredOnProcessing = isErroredOnProcessing,
        oCRExitCode = oCRExitCode,
        parsedResults = parsedResults?.map { it.toParsedResult() } ?: listOf(),
        processingTimeInMilliseconds = processingTimeInMilliseconds.orEmpty(),
        searchablePDFURL = searchablePDFURL.orEmpty(),
    )
}