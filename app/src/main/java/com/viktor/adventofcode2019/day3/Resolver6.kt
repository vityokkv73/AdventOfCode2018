package com.viktor.adventofcode2019.day3

import kotlinx.coroutines.coroutineScope
import java.util.regex.Pattern

class Resolver6 {
    suspend fun resolve(input: String) = coroutineScope {
        val claims = input.lines().filter { it.isNotEmpty() }.map { parseClaim(it) }
        val (rowsCount, columnsCount) = findRowsAndColumnsCounts(claims)
        val flatMatrix = MutableList(rowsCount * columnsCount) { 0 }
        val pureClaims = HashSet<Int>(claims.size)
        fillMatrix(flatMatrix, claims, columnsCount, pureClaims)
        pureClaims
    }

    private fun fillMatrix(flatMatrix: MutableList<Int>, claims: List<Claim>, columnsCount: Int, pureClaims: MutableSet<Int>) {
        claims.forEach { claim -> fillMatrixWithClaim(flatMatrix, claim, columnsCount, pureClaims) }
    }

    private fun fillMatrixWithClaim(flatMatrix: MutableList<Int>, claim: Claim, columnsCount: Int, pureClaims: MutableSet<Int>) {
        pureClaims.add(claim.id)
        for (i in (claim.top until claim.top + claim.height)) {
            for (j in (claim.left until claim.left + claim.width)) {
                val cellValue = flatMatrix[i * columnsCount + j]
                if (cellValue != 0) {
                    pureClaims.remove(claim.id)
                    pureClaims.remove(cellValue)
                } else {
                    flatMatrix[i * columnsCount + j] = claim.id
                }
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
