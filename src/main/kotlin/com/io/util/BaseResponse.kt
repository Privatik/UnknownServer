package com.io.util

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val isSuccessful: Boolean,
    val message: T? = null
)

