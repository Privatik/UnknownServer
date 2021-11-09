package com.io.di

import com.io.controller.user.UserController
import com.io.controller.user.UserControllerImpl
import com.io.repository.user.UserRepository
import com.io.repository.user.UserRepositoryImpl
import com.io.util.Constants
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val controllerModule = module {
    single<UserController> {
        UserControllerImpl(get())
    }
}

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}

val dbModule = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.USER_DB)
    }
}