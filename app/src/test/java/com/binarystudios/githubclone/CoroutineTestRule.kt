package com.binarystudios.githubclone

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    val testScope: TestCoroutineScope = TestCoroutineScope(dispatcher)
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineTestRule.runTest(block: suspend TestCoroutineScope.() -> Unit) =
    this.testScope.runTest {
        block()
    }