package com.example.databinding.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.NoText)
    val state = _state.asStateFlow()

    var resultSearch: MutableLiveData<String> = MutableLiveData("Результат поиска ->")
    val searchString = MutableStateFlow("")

    init {
        searchString.debounce(300).onEach {
            if(it.length >= 3){
                viewModelScope.launch {
                    _state.value = State.Loading
                    delay(5000)
                    _state.value = State.Success
                    resultSearch.value = "По запросу (${searchString.value}) ничего не найдено"
                }
            }
        }.launchIn(viewModelScope)
    }
}
