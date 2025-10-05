package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    GlobalScope.launch {
        delay(500)
        println("impreso con GlobalScope.launch")
    }
    println("main ends")
}

/* output
main ends

Process finished with exit code 0
 */