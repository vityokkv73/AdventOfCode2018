package com.viktor.adventofcode2019.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun readFromFile(context: Context, fileName: String): String {
    var input = ""
    var line: String?
    val bufferedReader = BufferedReader(InputStreamReader(context.assets.open(fileName)))
    while (true) {
        line = bufferedReader.readLine()
        if (line != null)
            input += line + "\n"
        else
            break
    }
    return input
}