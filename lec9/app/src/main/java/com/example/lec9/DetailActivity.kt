package com.example.lec9

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer


class DetailActivity : AppCompatActivity() {
    private val vm: TaskViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val id = intent.getIntExtra("task_id", -1)
        val nameTv: TextView = findViewById(R.id.detail_name)
        val priorityTv: TextView = findViewById(R.id.detail_priority)


        if (id != -1) {
            vm.getTaskById(id).observe(this, Observer { task ->
                if (task != null) {
                    nameTv.text = task.name
                    priorityTv.text = when (task.priority) {
                        1 -> "High"
                        2 -> "Medium"
                        else -> "Low"
                    }
                }
            })
        }
    }
}