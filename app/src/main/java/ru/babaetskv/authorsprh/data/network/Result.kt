package ru.babaetskv.authorsprh.data.network

sealed class Result<out T : Any> {
    data class Success<T : Any>(val data: T) : Result<T>()
    data class Error(val t: Throwable) : Result<Nothing>()
}
