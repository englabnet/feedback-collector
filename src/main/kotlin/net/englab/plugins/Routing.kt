package net.englab.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import net.englab.routes.feedbackRouting

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            feedbackRouting()
        }
    }
}
