package com.io.secutiry.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)