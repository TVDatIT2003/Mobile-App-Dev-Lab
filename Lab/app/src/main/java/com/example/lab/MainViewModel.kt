package com.example.lab

import android.app.Application
import androidx.lifecycle.*
import com.example.lab.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val MainViewModel.viewModelScope: Any

data class UiState(
    val loading: Boolean = false,
    val index: Int = 0,
    val total: Int = 0,
    val comment: CommentEntity? = null,
    val error: String? = null
)

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getInstance(app).commentDao()
    private val api = RetrofitClient.api

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> = _state

    init {
        viewModelScope.launch {
            val total = dao.count()
            if (total > 0) loadAt(0, total)
            else _state.value = UiState(total = 0)
        }
    }

    fun fetchAndStore() {
        viewModelScope.launch {
            _state.value = _state.value!!.copy(loading = true, error = null)
            try {
                val remote = api.getComments()
                dao.insertAll(remote.map { it.toEntity() })

                val total = dao.count()
                loadAt(0, total)
            } catch (e: Exception) {
                _state.value = _state.value!!.copy(
                    loading = false,
                    error = e.message ?: "Fetch failed"
                )
            }
        }
    }

    fun next() {
        val s = _state.value ?: return
        if (s.total == 0) return
        val newIndex = (s.index + 1).coerceAtMost(s.total - 1)
        if (newIndex != s.index) {
            viewModelScope.launch { loadAt(newIndex, s.total) }
        }
    }

    fun prev() {
        val s = _state.value ?: return
        if (s.total == 0) return
        val newIndex = (s.index - 1).coerceAtLeast(0)
        if (newIndex != s.index) {
            viewModelScope.launch { loadAt(newIndex, s.total) }
        }
    }

    fun clearError() {
        _state.value = _state.value?.copy(error = null)
    }

    private suspend fun loadAt(index: Int, total: Int) {
        val c = dao.getByOffset(index)
        _state.postValue(
            UiState(
                loading = false,
                index = index,
                total = total,
                comment = c
            )
        )
    }
}

private fun Any.launch(block: suspend (kotlinx.coroutines.CoroutineScope) -> Unit) {}
