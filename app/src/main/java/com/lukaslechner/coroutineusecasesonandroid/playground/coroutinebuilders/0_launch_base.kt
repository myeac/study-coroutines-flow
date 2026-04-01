package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main()= runBlocking {
    val job = launch {
        networkRequest()
        println("resultado recibido")
    }
    delay(200)
    job.start()
    println("final de runblocking")
}

suspend fun networkRequest():String {
    delay(500)
    return "result"
}