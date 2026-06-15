package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val deferred1 = async {
        val result1 = networkCallDeferred(1)
        println("resultado recibido: $result1 despues de ${elapsedMillisMutable(startTime)}ms")
        result1
    }
    val deferred2 = async {
        val result2 = networkCallDeferred(2)
        println("resultado recibido: $result2 despues de ${elapsedMillisMutable(startTime)}ms")
        result2
    }
    val resultList = listOf(deferred1.await(), deferred2.await())
    println("Resultado de la lista:$resultList despues de ${elapsedMillisMutable(startTime)}ms")
}

suspend fun networkCallDeferred(number: Int): String {
    delay(500)
    return "result $number"
}

fun elapsedMillisDeferred(startTime: Long) = System.currentTimeMillis() - startTime