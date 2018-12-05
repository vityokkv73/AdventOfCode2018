package com.viktor.adventofcode2019

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viktor.adventofcode2019.day2.Resolver3
import com.viktor.adventofcode2019.day3.getTask3Input
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch {
            val input = getTask3Input(this@MainActivity)
            result.text = async { Resolver3().resolve(input) }.await().toString()
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}
