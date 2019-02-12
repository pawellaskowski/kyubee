package com.pjl.kyubee.common

data class DataHolder<out T>(val status: Status, val data: T?) {

    companion object {
        fun <T> loading(data: T?): DataHolder<T> = DataHolder(Status.LOADING, data)
        fun <T> success(data: T?): DataHolder<T> = DataHolder(Status.SUCCESS, data)
        fun <T> error(data: T?): DataHolder<T> = DataHolder(Status.ERROR, data)
    }
}