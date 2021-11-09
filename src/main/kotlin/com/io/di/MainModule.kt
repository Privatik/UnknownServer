package com.io.di

import com.io.controller.chat.ChatController
import com.io.controller.chat.ChatControllerImpl
import com.io.controller.login.LoginController
import com.io.controller.login.LoginControllerImpl
import com.io.controller.user.UserController
import com.io.controller.user.UserControllerImpl
import com.io.repository.chat.ChatRepository
import com.io.repository.chat.ChatRepositoryImpl
import com.io.repository.login.LoginRepository
import com.io.repository.login.LoginRepositoryImpl
import com.io.repository.user.UserRepository
import com.io.repository.user.UserRepositoryImpl
import com.io.util.Constants
import org.koin.core.scope.get
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val controllerModule = module {
    single<UserController> {
        UserControllerImpl(get())
    }

    single<LoginController> {
        LoginControllerImpl(get(), get())
    }

    single<ChatController> {
        ChatControllerImpl(get())
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
}

val dbModule = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.USER_DB)
    }
}