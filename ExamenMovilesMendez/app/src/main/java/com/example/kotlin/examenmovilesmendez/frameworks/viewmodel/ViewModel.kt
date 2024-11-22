package com.example.kotlin.examenmovilesmendez.frameworks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.examenmovilesmendez.domain.Requirement

class ViewModel : ViewModel() {
    private val _eventos = MutableLiveData<List<Map<String, Any>>>()
    val eventos: LiveData<List<Map<String, Any>>> get() = _eventos

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val consultarEventosRequirement = Requirement()

    fun consultarEventos(page: Int) {
        consultarEventosRequirement.consultarEventos(page) { result ->
            if (result.isSuccess) {
                _eventos.postValue(result.getOrDefault(emptyList()))
            } else {
                _error.postValue(result.exceptionOrNull()?.message)
            }
        }
    }
}
