package com.ibrahim.sawitpro.data.remote.network.exception

class ApiErrorException(
    override val message: String? = null,
    val httpCode: Int? = null
) : Exception()