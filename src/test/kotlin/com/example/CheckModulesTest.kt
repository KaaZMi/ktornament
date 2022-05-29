package com.example

import com.example.di.mongoModule
import com.example.di.playerModule
import io.mockk.mockkClass
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkKoinModules
import org.koin.test.junit5.AutoCloseKoinTest
import org.koin.test.mock.MockProvider
import kotlin.test.Test

class CheckModulesTest: KoinTest {
    @Test
    fun verifyKoinModules() = checkKoinModules(listOf(playerModule, mongoModule))
}
