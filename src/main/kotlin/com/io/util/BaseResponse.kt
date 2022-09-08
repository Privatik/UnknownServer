package com.io.util

import kotlinx.serialization.Serializer


@kotlinx.serialization.Serializable
data class BaseResponse<T>(
    val isSuccessful: Boolean,
    val message: T? = null
)

