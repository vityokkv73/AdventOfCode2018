package com.viktor.adventofcode2019.day1

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class Resolver2 {
    suspend fun resolve(input: String) = coroutineScope {
        async {
            val frequencies = input.lines().filter { it.isNotEmpty() }.map { Integer.parseInt(it) }
            val setOfFrequencies = HashSet<Int>()
            var currentFrequency = 0
            var index = 0
            val sizeOfFrequencies = frequencies.size
            while(true) {
                if (!setOfFrequencies.add(currentFrequency)) {
                    return@async currentFrequency
                }
                currentFrequency += frequencies[index]
                index = (index + 1) % sizeOfFrequencies
            }
        }
    }
}
