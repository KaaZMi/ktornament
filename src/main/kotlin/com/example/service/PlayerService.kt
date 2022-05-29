package com.example.service

import com.example.model.Player
import com.example.model.PlayerCreationRequest
import com.example.model.RankedPlayer
import com.example.repository.PlayerRepository
import org.slf4j.LoggerFactory
import java.util.UUID

class PlayerService(private val playerRepository: PlayerRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun getPlayers(): List<RankedPlayer> {
        return try {
            playerRepository.getPlayers().ranked()
        } catch (e: Exception) {
            logger.error("Error while retrieving the players", e)
            emptyList()
        }
    }

    suspend fun getPlayer(id: String): RankedPlayer? {
        return try {
            val players = playerRepository.getPlayers().ranked()
            players.find { it.id == id }
        } catch (e: Exception) {
            logger.error("Error while retrieving the player", e)
            null
        }
    }

    suspend fun addPlayer(playerCreationRequest: PlayerCreationRequest): Boolean {
        return try {
            playerRepository.addPlayer(
                Player(
                    _id = UUID.randomUUID().toString(),
                    pseudo = playerCreationRequest.pseudo,
                    points = 0,
                )
            )
        } catch (e: Exception) {
            logger.error("Error while adding the player", e)
            false
        }
    }

    suspend fun deletePlayers(): Long {
        return try {
            playerRepository.deletePlayers()
        } catch (e: Exception) {
            logger.error("Error while deleting the players", e)
            0L
        }
    }

    suspend fun updatePlayer(id: String, points: Int): Boolean {
        return try {
            playerRepository.updatePlayer(id, points)
        } catch (e: Exception) {
            logger.error("Error while updating the player", e)
            false
        }
    }

    /**
     * Ranking with ties
     */
    private fun List<Player>.ranked(): List<RankedPlayer> {
        val sortedPlayers = sortedByDescending { it.points }

        var ranking = 1
        return sortedPlayers.mapIndexed { i, player ->
            if (i == 0) {
                player.toRankedPlayer(ranking)
            } else {
                if (player.points == sortedPlayers[i - 1].points) {
                    player.toRankedPlayer(ranking)
                } else {
                    ranking++
                    player.toRankedPlayer(ranking)
                }
            }
        }
    }

    private fun Player.toRankedPlayer(ranking: Int): RankedPlayer =
        RankedPlayer(
            id = _id,
            pseudo = pseudo,
            points = points,
            ranking = ranking
        )
}
