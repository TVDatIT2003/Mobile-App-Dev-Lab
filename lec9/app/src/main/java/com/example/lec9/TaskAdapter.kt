package com.example.lec9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter(private var items: List<Task>, private val onClick: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.VH>() {


    inner class VH(item: View) : RecyclerView.ViewHolder(item) {
        val name: TextView = item.findViewById(R.id.item_task_name)
        val priority: TextView = item.findViewById(R.id.item_task_priority)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return VH(view)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val task = items[position]
        holder.name.text = task.name
        holder.priority.text = when (task.priority) {
            1 -> "High"
            2 -> "Medium"
            else -> "Low"
        }
        holder.itemView.setOnClickListener { onClick(task) }
    }


    override fun getItemCount(): Int = items.size


    fun update(newItems: List<Task>) {
        items = newItems
        notifyDataSetChanged()
    }
}