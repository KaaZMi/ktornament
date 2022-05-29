package com.example

import com.example.di.mongoModule
import com.example.di.playerModule
import com.example.plugins.configureHTTP
import com.example.plugins.configureSerialization
import com.example.routes.playerRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    // Start Ktor
    embeddedServer(Netty, environment = commandLineEnvironment(args)).start(wait = true)
}

fun Application.main() {
    // Install Koin features
    install(Koin) {
        slf4jLogger()
        modules(playerModule, mongoModule)
    }

    configureSerialization()
    configureHTTP()

    routing {
        playerRouting()
    }
}
