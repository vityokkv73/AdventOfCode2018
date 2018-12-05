package com.viktor.adventofcode2019.day1

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class Resolver1 {
    suspend fun resolve(input: String) = coroutineScope {
        async {
            input.lines().filter { it.isNotEmpty() }.map { Integer.parseInt(it) }.sum()
        }
    }
}