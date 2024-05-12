package com.example.databinding.ui.main

sealed class State {
    object Loading : State()
    object Success : State()
    object NoText : State()
}