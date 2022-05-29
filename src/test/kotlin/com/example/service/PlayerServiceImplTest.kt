package com.example.service

import com.example.di.playerModule
import com.example.model.Player
import com.example.model.PlayerCreationRequest
import com.example.model.RankedPlayer
import com.example.repository.PlayerRepository
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class PlayerServiceTest : KoinTest {
    private val playerService: PlayerService by inject()

    private val players = listOf(
        Player(_id = "1", pseudo = "toto", points = 12),
        Player(_id = "2", pseudo = "titi", points = 23),
        Player(_id = "3", pseudo = "tata", points = 12),
        Player(_id = "4", pseudo = "tutu", points = 4),
    )

    @Before
    fun setup() {
        startKoin {
            modules(playerModule)
        }

        MockProvider.register { mockkClass(it) }
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun `get players should return players ranked by points`() {
        declareMock<PlayerRepository> {
            coEvery {
                getPlayers()
            } returns players
        }

        val result = runBlocking { playerService.getPlayers() }

        assertEquals(4, result.size)
        assertEquals(RankedPlayer(id = "2", pseudo = "titi", points = 23, ranking = 1), result[0])
        assertEquals(RankedPlayer(id = "1", pseudo = "toto", points = 12, ranking = 2), result[1])
        assertEquals(RankedPlayer(id = "3", pseudo = "tata", points = 12, ranking = 2), result[2])
        assertEquals(RankedPlayer(id = "4", pseudo = "tutu", points = 4, ranking = 3), result[3])
    }

    @Test
    fun `get players should return empty list if exception is thrown by repo`() {
        declareMock<PlayerRepository> {
            coEvery {
                getPlayers()
            } throws Exception("error")
        }

        val result = runBlocking { playerService.getPlayers() }

        assertEquals(0, result.size) }

    @Test
    fun `get player should return player with points and rank`() {
        declareMock<PlayerRepository> {
            coEvery {
                getPlayers()
            } returns players
        }

        val result = runBlocking { playerService.getPlayer("2") }
        assertEquals(RankedPlayer(id = "2", pseudo = "titi", points = 23, ranking = 1), result)
    }

    @Test
    fun `get player should return null if exception is thrown by repo`() {
        declareMock<PlayerRepository> {
            coEvery {
                getPlayers()
            } throws Exception("error")
        }

        val result = runBlocking { playerService.getPlayer("2") }
        assertNull(result)
    }

    @Test
    fun `add player should return true`() {
        declareMock<PlayerRepository> {
            coEvery {
                addPlayer(any())
            } returns true
        }

        val result = runBlocking { playerService.addPlayer(PlayerCreationRequest(pseudo = "titi")) }
        assertEquals(true, result)
    }

    @Test
    fun `add player should return false if exception is thrown by repo`() {
        declareMock<PlayerRepository> {
            coEvery {
                addPlayer(any())
            } throws Exception("error")
        }

        val result = runBlocking { playerService.addPlayer(PlayerCreationRequest(pseudo = "titi")) }
        assertEquals(false, result)
    }

    @Test
    fun `delete players should return deleted players counter`() {
        declareMock<PlayerRepository> {
            coEvery {
                deletePlayers()
            } returns 1
        }

        val result = runBlocking { playerService.deletePlayers() }
        assertEquals(1, result)
    }

    @Test
    fun `delete players should return deleted players counter at zero if exception is thrown`() {
        declareMock<PlayerRepository> {
            coEvery {
                deletePlayers()
            } throws Exception("error")
        }

        val result = runBlocking { playerService.deletePlayers() }
        assertEquals(0, result)
    }

    @Test
    fun `update player should return true`() {
        declareMock<PlayerRepository> {
            coEvery {
                updatePlayer(any(), any())
            } returns true
        }

        val result = runBlocking { playerService.updatePlayer("toto", 12) }
        assertEquals(true, result)
    }

    @Test
    fun `update player should return false if exception is thrown by repo`() {
        declareMock<PlayerRepository> {
            coEvery {
                updatePlayer(any(), any())
            } throws Exception("error")
        }

        val result = runBlocking { playerService.updatePlayer("toto", 12) }
        assertEquals(false, result)
    }
}
