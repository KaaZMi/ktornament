package com.example.di

import com.example.repository.PlayerRepository
import com.example.service.PlayerService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val playerModule = module {
    singleOf(::PlayerService)
    singleOf(::PlayerRepository)
}

val mongoModule = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase("test")
    }
}

