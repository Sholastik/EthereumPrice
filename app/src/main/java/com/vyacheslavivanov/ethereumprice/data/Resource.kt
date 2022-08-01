package com.vyacheslavivanov.ethereumprice.data

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    companion object {
        fun <T : Any> Result<T>.toResource(): Resource<T> {
            return fold(
                onSuccess = {
                    Success(it)
                },
                onFailure = {
                    Error(it)
                }
            )
        }
    }
}
