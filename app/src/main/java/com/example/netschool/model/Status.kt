package com.example.netschool.model

sealed class Status<T>(data: T?=null, exception: Exception?=null) {
    class Success<T>(val data: T) : Status<T>(data)
    class Failure<T>(val exception: Exception, val data: T?=null) : Status<T>(data,exception)
    class Loading<T> : Status<T>()
}