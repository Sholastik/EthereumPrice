package com.vyacheslavivanov.ethereumprice.api.util

import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

inline fun <T> Response<T>.fold(
    toBody: Response<T>.() -> T? = {
        body()
    },
    toException: Response<T>.() -> Exception = {
        HttpException(this)
    }
): Result<T> {
    return runCatching {
        if (isSuccessful) {
            toBody() ?: throw UnknownError(toString())
        } else {
            throw toException()
        }
    }
}

fun <T> Result<T>.log(): Result<T> =
    onFailure {
        Timber.e(it)
    }
