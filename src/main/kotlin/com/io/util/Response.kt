package com.io.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

data class Response<T>(
    val data: T?,
    val exceptionMessage: ExceptionMessage? = null
) {
    fun isSuccess(): Boolean{
        return data != null
    }
}
