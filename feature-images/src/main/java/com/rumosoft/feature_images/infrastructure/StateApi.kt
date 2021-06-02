package com.rumosoft.feature_images.infrastructure

sealed class StateApi<out T> {
    data class Error(val throwable: Throwable) : StateApi<Nothing>()
    data class Success<T>(val data: T) : StateApi<T>()
    object Loading : StateApi<Nothing>()
}