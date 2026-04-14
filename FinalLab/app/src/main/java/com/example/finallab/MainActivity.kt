package com.example.finallab

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val vm: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tvTodoId = findViewById<android.widget.TextView>(R.id.tvTodoId)
        val tvUserId = findViewById<android.widget.TextView>(R.id.tvUserId)
        val tvTitle  = findViewById<android.widget.TextView>(R.id.tvTitle)
        val tvStatus = findViewById<android.widget.TextView>(R.id.tvStatus)

        val btnFetch = findViewById<android.widget.Button>(R.id.btnFetch)
        val btnPrev  = findViewById<android.widget.Button>(R.id.btnPrev)
        val btnNext  = findViewById<android.widget.Button>(R.id.btnNext)

        btnFetch.setOnClickListener { vm.fetchTodos() }
        btnPrev.setOnClickListener { vm.prev() }
        btnNext.setOnClickListener { vm.next() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    val todo = state.todo
                    tvTodoId.text = "Todo ID: ${todo?.id ?: "-"}"
                    tvUserId.text = "User ID: ${todo?.userId ?: "-"}"
                    tvTitle.text  = "Title: ${todo?.title ?: "-"}"
                    tvStatus.text = "Status: " + when (todo?.completed) {
                        true -> "Completed"
                        false -> "Not Completed"
                        null -> "-"
                    }

                    btnFetch.isEnabled = !state.loading
                    btnPrev.isEnabled = state.total > 0 && state.index > 0 && !state.loading
                    btnNext.isEnabled = state.total > 0 && state.index < state.total - 1 && !state.loading
                }
            }
        }
    }
}
