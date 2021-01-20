package ru.babaetskv.authorsprh.global.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource

abstract class BaseDataSource<T> : PageKeyedDataSource<Long, T>() {
    val state: MutableLiveData<RequestState<*>> = MutableLiveData()

    fun updateState(state: RequestState<*>) = this.state.postValue(state)
}