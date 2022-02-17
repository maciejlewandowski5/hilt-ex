package com.example.android.hilt.data

import org.json.JSONObject

data class ParseResponseException(val response: Response<*>) : RuntimeException()

sealed class Response<out T> {
    data class Success<out T>(val value: T) : Response<T>()
    data class Error(val statusCode: Int = 0, val errorBody: JSONObject? = null) : Response<Nothing>() {
    }
    object NetworkError : Response<Nothing>()
}

val <T> Response<T>.valueOrNull: T?
    get() = (this as? Response.Success)?.value

val <T> Response<T>.value: T
    get() = valueOrNull ?: throw ParseResponseException(this)
