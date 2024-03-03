package com.otus.otuskotlin.marketplace.m2l1.homework.hard.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.otus.otuskotlin.marketplace.m2l1.homework.hard.Meaning

@JsonIgnoreProperties(ignoreUnknown = true)
data class Dictionary(
    val word: String,
    val meanings: List<Meaning>
)
