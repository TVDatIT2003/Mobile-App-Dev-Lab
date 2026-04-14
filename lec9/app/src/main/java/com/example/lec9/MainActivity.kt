package com.example.lec9

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private val vm: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val nameInput: EditText = findViewById(R.id.input_task_name)
        val prioritySpinner: Spinner = findViewById(R.id.spinner_priority)
        val addButton: Button = findViewById(R.id.button_add)
        val recycler: RecyclerView = findViewById(R.id.recycler_tasks)


        adapter = TaskAdapter(listOf()) { task ->
            val i = Intent(this, DetailActivity::class.java)
            i.putExtra("task_id", task.id)
            startActivity(i)
        }


        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter


        vm.allTasks.observe(this, Observer { tasks ->
            adapter.update(tasks)
        })


        addButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val priority = when (prioritySpinner.selectedItemPosition) {
                0 -> 1 // High
                1 -> 2 // Medium
                else -> 3 // Low
            }
            if (name.isNotEmpty()) {
                vm.insert(name, priority)
                nameInput.text.clear()
            }
        }
    }
}