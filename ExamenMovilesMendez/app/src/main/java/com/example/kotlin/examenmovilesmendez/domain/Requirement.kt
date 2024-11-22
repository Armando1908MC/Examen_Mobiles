package com.example.kotlin.examenmovilesmendez.domain

import com.example.kotlin.examenmovilesmendez.data.repositories.Repository

class Requirement {
    private val repository: Repository = Repository()

    fun consultarEventos(
        page: Int,
        callback: (Result<List<Map<String, Any>>>) -> Unit,
    ) {
        repository.consultarEventos(page) { result ->
            if (result.isSuccess) {
                callback(Result.success(result.getOrDefault(emptyList())))
            } else {
                callback(Result.failure(result.exceptionOrNull() ?: Exception("Error desconocido")))
            }
        }
    }
}