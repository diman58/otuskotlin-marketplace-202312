package com.otus.otuskotlin.marketplace.m2l1.homework.easy

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class HWEasy {

    @Test
    fun easyHw() = runBlocking {
        val numbers = generateNumbers()
        val toFind = 10
        val toFindOther = 1000

        val first = async { findNumberInList(toFind, numbers) }
        val second = async { findNumberInList(toFindOther, numbers) }

        val foundNumbers = listOf(first.await(), second.await())

        foundNumbers.forEach {
            if (it != -1) {
                println("Your number $it found!")
            } else {
                println("Not found number $toFind || $toFindOther")
            }
        }
    }
}
