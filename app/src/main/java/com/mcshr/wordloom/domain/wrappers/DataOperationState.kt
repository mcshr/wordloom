package com.mcshr.wordloom.domain.wrappers

sealed class DataOperationState<out T, out K> {
    class Success<T>(val data:T? = null): DataOperationState<T, Nothing>()
    class Failure<K>(val errorData:K): DataOperationState<Nothing, K>()
}