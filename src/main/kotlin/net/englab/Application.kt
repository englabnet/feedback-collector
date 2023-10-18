package net.englab

import io.ktor.server.application.*
import io.ktor.server.netty.*
import net.englab.dao.DatabaseFactory
import net.englab.plugins.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    val config = environment.config;
    DatabaseFactory.init(config)
    configureSerialization()
    configureRouting()
}
