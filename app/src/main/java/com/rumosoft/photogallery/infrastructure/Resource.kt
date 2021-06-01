package com.rumosoft.photogallery.infrastructure

/**
 * A generic class that holds a value with its loading status.
 */
sealed class Resource<out T> {
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}