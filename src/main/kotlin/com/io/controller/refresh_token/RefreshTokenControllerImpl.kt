package com.io.controller.refresh_token

import com.io.data.model.refresh_token.RefreshTokenRequest
import com.io.model.RefreshToken
import com.io.repository.refresh_token.RefreshTokenRepository
import com.io.repository.user.UserRepository
import com.io.util.ExceptionMessage
import com.io.util.Response

class RefreshTokenControllerImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository
): RefreshTokenController {

    override suspend fun checkRefreshToken(request: RefreshTokenRequest): Response<Boolean> {
        return if (request.isBlank()){
            Response(false, ExceptionMessage.EXCEPTION_REFRESH_TOKEN_FIELD_EMPTY)
        } else {
            val response = refreshTokenRepository.checkValidRefreshToken(request.userId, "")

            Response(response, if (!response) ExceptionMessage.EXCEPTION_REFRESH_TOKEN_DEADLINE_HAS_EXPIRED else null)
        }
    }

    override suspend fun createNewRefreshToken(userId: String): Response<RefreshToken> {
        return if (userId.isBlank() || userRepository.getUserById(userId) == null){
            Response(null, ExceptionMessage.EXCEPTION_USER_DONT_EXIST)
        } else {
            val response = refreshTokenRepository.getRefreshToken(userId)

            Response(response, null)
        }
    }
}