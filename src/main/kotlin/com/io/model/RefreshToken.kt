package com.io.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.sql.Timestamp

data class RefreshToken(
    @BsonId
    val id: String = ObjectId().toString(),
    val userId: String,
    val email: String,
    val refreshToken: String,
    val timestamp: Long
)
