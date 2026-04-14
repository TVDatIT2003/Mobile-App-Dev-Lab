package com.example.lecture8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lecture8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User(
            name = "John Doe",
            email = "john.doe@example.com"
        )
        binding.user = user

        binding.btnChangeUser.setOnClickListener {
            val newUser = User(
                name = "Alice Nguyen",
                email = "alice@example.com"
            )
            binding.user = newUser
        }
    }
}