package net.englab.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import net.englab.dao.dao
import net.englab.models.Message
import net.englab.models.dto.MessageDto

fun Route.feedbackRouting() {
    route("/feedback") {
        get {
            val page = call.parameters["page"]?.toIntOrNull() ?: 0
            val size = call.parameters["size"]?.toIntOrNull() ?: 10
            call.respond(dao.allMessages(page, size))
        }
        post<MessageDto> {
            dao.addMessage(Message(
                name = it.name,
                email = it.email,
                type = it.type,
                message = it.message,
                timestamp = Clock.System.now()
            ))
            call.respond(HttpStatusCode.OK, "The message has been added.")
        }
        delete("/{id}") {
            call.parameters["id"]?.toIntOrNull()?.let { id ->
                if (dao.deleteMessage(id)) {
                    call.respond(HttpStatusCode.OK, "The message has been deleted.")
                } else {
                    call.respond(HttpStatusCode.NotFound, "The message has not been found.")
                }
            } ?: call.respond(HttpStatusCode.BadRequest, "The request is incorrect.")
        }
        delete {
            if (dao.deleteAllMessages()) {
                call.respond(HttpStatusCode.OK, "All messages have been deleted.")
            } else {
                call.respond(HttpStatusCode.NotFound, "There are no messages.")
            }
        }
    }
}
