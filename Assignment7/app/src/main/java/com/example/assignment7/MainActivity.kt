package com.example.assignment7

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var incrementNumber = 0
    lateinit var tvIncrement: TextView

    companion object {
        private const val KEY_INCREMENT = "key_increment"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvIncrement = findViewById(R.id.tv_increment)

        tvIncrement.setOnClickListener {
            ++incrementNumber
            tvIncrement.text =incrementNumber.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val userNumber = incrementNumber
        outState.putInt("savedInt", incrementNumber)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val userInt = savedInstanceState.getInt("savedInt", 0)
        incrementNumber = userInt
        tvIncrement.text = incrementNumber.toString()
    }
}