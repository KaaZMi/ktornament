package com.example.routes

import com.example.model.PlayerCreationRequest
import com.example.model.PlayerUpdatingRequest
import com.example.service.PlayerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.playerRouting() {
    // Lazy inject PlayerService
    val playerService by inject<PlayerService>()

    route("/players") {
        get {
            call.respond(playerService.getPlayers())
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing player id",
                status = HttpStatusCode.BadRequest
            )
            val player = playerService.getPlayer(id) ?: return@get call.respondText(
                "No player with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(player)
        }
        post {
            val playerCreationRequest = call.receive<PlayerCreationRequest>()
            val added = playerService.addPlayer(playerCreationRequest)
            if (added) {
                call.respondText("Player added correctly", status = HttpStatusCode.Created)
            } else {
                call.respondText("Player not added", status = HttpStatusCode.InternalServerError)
            }
        }
        put("{id?}") {
            val playerUpdatingRequest = call.receive<PlayerUpdatingRequest>()
            val id = call.parameters["id"] ?: return@put call.respondText(
                "Missing player id",
                status = HttpStatusCode.BadRequest
            )
            val updated = playerService.updatePlayer(id, playerUpdatingRequest.points)
            if (updated) {
                call.respondText("Player updated", status = HttpStatusCode.OK)
            } else {
                call.respondText("Player not updated", status = HttpStatusCode.InternalServerError)
            }
        }
        delete {
            val deletedCount: Long = playerService.deletePlayers()
            call.respondText("$deletedCount players deleted", status = HttpStatusCode.OK)
        }
    }
}
