package com.example.finallab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class UiState(
    val todo: TodoEntity? = null,
    val index: Int = 0,
    val total: Int = 0,
    val loading: Boolean = false
)

class TodoViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app)
    private val repo = TodoRepository(db.todoDao(), RetrofitClient.api)

    private val listFlow = MutableStateFlow<List<TodoEntity>>(emptyList())
    private val idxFlow = MutableStateFlow(0)
    private val loadingFlow = MutableStateFlow(false)

    val uiState = combine(listFlow, idxFlow, loadingFlow) { list, idx, loading ->
        val safe = if (list.isEmpty()) 0 else idx.coerceIn(0, list.lastIndex)
        UiState(
            todo = list.getOrNull(safe),
            index = safe,
            total = list.size,
            loading = loading
        )
    }

    init {
        viewModelScope.launch {
            repo.observeTodos().collectLatest { list ->
                listFlow.value = list
                if (list.isEmpty()) idxFlow.value = 0
                else if (idxFlow.value > list.lastIndex) idxFlow.value = list.lastIndex
            }
        }
    }

    fun fetchTodos() {
        viewModelScope.launch {
            loadingFlow.value = true
            try {
                repo.refreshTodos()
                idxFlow.value = 0
            } finally {
                loadingFlow.value = false
            }
        }
    }

    fun next() {
        val size = listFlow.value.size
        if (size > 0 && idxFlow.value < size - 1) idxFlow.value++
    }

    fun prev() {
        if (listFlow.value.isNotEmpty() && idxFlow.value > 0) idxFlow.value--
    }
}
