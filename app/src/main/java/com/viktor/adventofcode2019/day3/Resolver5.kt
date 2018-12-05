package com.viktor.adventofcode2019.day3

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.regex.Pattern

class Resolver5 {
    suspend fun resolve(input: String) = coroutineScope {
        async {
            val claims = input.lines().filter { it.isNotEmpty() }.map { parseClaim(it) }
            val (rowsCount, columnsCount) = findRowsAndColumnsCounts(claims)
            val flatMatrix = MutableList(rowsCount * columnsCount) { 0 }
            fillMatrix(flatMatrix, claims, columnsCount)
            countOverlappedInches(flatMatrix)
        }
    }

    private fun countOverlappedInches(flatMatrix: MutableList<Int>): Int {
        return flatMatrix.count { it >= 2 }
    }

    private fun fillMatrix(flatMatrix: MutableList<Int>, claims: List<Claim>, columnsCount: Int) {
        claims.forEach { claim -> fillMatrixWithClaim(flatMatrix, claim, columnsCount) }
    }

    private fun fillMatrixWithClaim(flatMatrix: MutableList<Int>, claim: Claim, columnsCount: Int) {
        for (i in (claim.top until claim.top + claim.height)) {
            for (j in (claim.left until claim.left + claim.width)) {
                flatMatrix[i * columnsCount + j] = flatMatrix[i * columnsCount + j] + 1
            }
        }
    }

    private fun findRowsAndColumnsCounts(claims: List<Claim>): Pair<Int, Int> {
        var rows = 0
        var columns = 0
        claims.forEach { claim ->
            val claimColumnCount = claim.left + claim.width
            val claimRowsCount = claim.top + claim.height
            if (rows < claimRowsCount) {
                rows = claimRowsCount
            }
            if (columns < claimColumnCount) {
                columns = claimColumnCount
            }
        }
        return rows to columns
    }

    private fun parseClaim(claim: String): Claim {
        val matcher = Pattern.compile("""^#(\d+)\s@\s(\d+),(\d+):\s(\d+)x(\d+)$""").matcher(claim)
        matcher.find()
        val matchResult = matcher.toMatchResult()
        return Claim(
            Integer.parseInt(matchResult.group(1)),
            Integer.parseInt(matchResult.group(2)),
            Integer.parseInt(matchResult.group(3)),
            Integer.parseInt(matchResult.group(4)),
            Integer.parseInt(matchResult.group(5))
        )
    }
}