package com.io.data.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse(
    val isSuccessful: Boolean,
    val message: String? = null
)
