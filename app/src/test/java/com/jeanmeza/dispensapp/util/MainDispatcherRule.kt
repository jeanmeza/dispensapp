package com.jeanmeza.dispensapp.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * This rule helps manage the Main dispatcher for tests, replacing it with a test dispatcher.
 * @param testDispatcher The TestDispatcher to use. Defaults to UnconfinedTestDispatcher.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() { // TestWatcher is a JUnit Rule

    // This method is called before a test method is executed
    override fun starting(description: Description) {
        // Set the Main dispatcher to our test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    // This method is called after a test method has been executed
    override fun finished(description: Description) {
        // Reset the Main dispatcher to its original state
        Dispatchers.resetMain()
    }
}