package com.example.finallab

class TodoRepository(
    private val dao: TodoDao,
    private val api: MyApi
) {
    fun observeTodos() = dao.observeTodos()

    suspend fun refreshTodos() {
        val remote = api.getTodos()
        val entities = remote.map {
            TodoEntity(it.id, it.userId, it.title, it.completed)
        }

        dao.clearAll()        // replace old data
        dao.insertAll(entities)
    }
}
