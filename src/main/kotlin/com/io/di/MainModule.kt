package com.io.di

import com.io.controller.chat.ChatController
import com.io.controller.chat.ChatControllerImpl
import com.io.controller.login.LoginController
import com.io.controller.login.LoginControllerImpl
import com.io.controller.message.MessageController
import com.io.controller.message.MessageControllerImpl
import com.io.controller.refresh_token.RefreshTokenController
import com.io.controller.refresh_token.RefreshTokenControllerImpl
import com.io.controller.user.UserController
import com.io.controller.user.UserControllerImpl
import com.io.repository.chat.ChatRepository
import com.io.repository.chat.ChatRepositoryImpl
import com.io.repository.login.LoginRepository
import com.io.repository.login.LoginRepositoryImpl
import com.io.repository.message.MessageRepository
import com.io.repository.message.MessageRepositoryImpl
import com.io.repository.refresh_token.RefreshTokenRepository
import com.io.repository.refresh_token.RefreshTokenRepositoryImpl
import com.io.repository.user.UserRepository
import com.io.repository.user.UserRepositoryImpl
import com.io.secutiry.hashing.HashingService
import com.io.secutiry.hashing.SHA256HashingService
import com.io.secutiry.token.JwtTokenService
import com.io.secutiry.token.TokenService
import com.io.util.Constants
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val controllerModule = module {
    single<UserController> {
        UserControllerImpl(get(), get())
    }

    single<LoginController> {
        LoginControllerImpl(get(), get(), get())
    }

    single<ChatController> {
        ChatControllerImpl(get(), get())
    }

    single<MessageController> {
        MessageControllerImpl(get(), get(), get())
    }

    single<RefreshTokenController> {
        RefreshTokenControllerImpl(get(), get())
    }
}

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single<LoginRepository> {
        LoginRepositoryImpl(get())
    }

    single<ChatRepository> {
        ChatRepositoryImpl(get())
    }

    single<MessageRepository> {
        MessageRepositoryImpl(get())
    }

    single<RefreshTokenRepository> {
        RefreshTokenRepositoryImpl(get(), get())
    }
}

val serviceModule = module {
    single<HashingService> {
        SHA256HashingService()
    }

    single<TokenService> {
        JwtTokenService()
    }
}

val dbModule = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.USER_DB)
    }
}