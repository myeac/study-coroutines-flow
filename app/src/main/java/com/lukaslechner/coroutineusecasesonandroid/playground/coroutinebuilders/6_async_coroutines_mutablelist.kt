package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val resultList = mutableListOf<String>()
    val job1 = launch {
        val result1 = networkCallMutable(1)
        resultList.add(result1)
        println("resultado recibido: $result1 despues de ${elapsedMillisMutable(startTime)}ms")
    }

    val job2 = launch {
        val result2 = networkCallMutable(2)
        resultList.add(result2)
        println("resultado recibido: $result2 despues de ${elapsedMillisMutable(startTime)}ms")
    }
    job1.join()
    job2.join()
    println("Resultado de la lista:$resultList despues de ${elapsedMillisMutable(startTime)}ms")
}

suspend fun networkCallMutable(number: Int): String {
    delay(500)
    return "result $number"
}

fun elapsedMillisMutable(startTime: Long) = System.currentTimeMillis() - startTime