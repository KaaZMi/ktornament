package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val _id: String,
    val pseudo: String,
    val points: Int
)

@Serializable
data class RankedPlayer(val id: String, val pseudo: String, val points: Int, val ranking: Int)

@Serializable
data class PlayerCreationRequest(val pseudo: String)

@Serializable
data class PlayerUpdatingRequest(val points: Int)
