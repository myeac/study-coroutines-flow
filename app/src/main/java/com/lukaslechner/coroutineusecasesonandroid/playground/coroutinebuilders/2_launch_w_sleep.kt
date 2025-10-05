package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    GlobalScope.launch {
        delay(500)
        println("impreso con GlobalScope.launch")
    }
    Thread.sleep(1000)
    println("main ends")
}

/* output
impreso con GlobalScope.launch
main ends

Process finished with exit code 0
 */