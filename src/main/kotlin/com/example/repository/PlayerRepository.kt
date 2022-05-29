package com.example.repository

import com.example.model.Player
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class PlayerRepository(database: CoroutineDatabase) {
    private val playersCollection = database.getCollection<Player>("players")

    suspend fun getPlayers(): List<Player> = playersCollection.find().toList()

    suspend fun addPlayer(player: Player): Boolean = playersCollection.insertOne(player).wasAcknowledged()

    suspend fun deletePlayers(): Long = playersCollection.deleteMany().deletedCount

    suspend fun updatePlayer(id: String, points: Int): Boolean =
        playersCollection.updateOne(Player::_id eq id, setValue(Player::points, points)).wasAcknowledged()
}
