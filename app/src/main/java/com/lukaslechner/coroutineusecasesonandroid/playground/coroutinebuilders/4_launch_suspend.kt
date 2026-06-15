package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    launch {
        networkRequestTest()
    }
}

suspend fun networkRequestTest(): String{
    delay(500)
    return "result"
}
