package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() =    runBlocking<Unit> {
        launch {
            delay(500)
            println("impreso con runblocking y launch")
        }
}

/* output
impreso con GlobalScope.launch

Process finished with exit code 0
 */