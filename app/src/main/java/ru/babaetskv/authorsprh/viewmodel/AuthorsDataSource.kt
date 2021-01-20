package ru.babaetskv.authorsprh.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.babaetskv.authorsprh.data.network.Result
import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.domain.model.GetAuthorsParams
import ru.babaetskv.authorsprh.domain.repository.AuthorsRepository
import ru.babaetskv.authorsprh.global.viewmodel.BaseDataSource
import ru.babaetskv.authorsprh.global.viewmodel.RequestState

class AuthorsDataSource(
    private val searchString: String,
    private val authorsRepository: AuthorsRepository
) : BaseDataSource<Author>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Author>
    ) {
        if (searchString.isEmpty()) {
            callback.onResult(listOf(), null, 2L)
            return
        }

        val p = GetAuthorsParams(
            limit = params.requestedLoadSize.toLong(),
            offset = 0,
            searchString = searchString
        )
        GlobalScope.launch(Dispatchers.Main) {
            updateState(RequestState.Progress)
            when (val result = authorsRepository.getAuthors(p)) {
                is Result.Success -> {
                    val data = result.data
                    if (data.isEmpty()) updateState(RequestState.NoData) else {
                        updateState(RequestState.Success)
                        callback.onResult(data, null, 2L)
                    }
                }
                is Result.Error -> onError(result.t)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Author>) {
        val key = params.key
        val pageSize = params.requestedLoadSize
        val p = GetAuthorsParams(
            limit = pageSize.toLong(),
            offset = pageSize * (key - 1),
            searchString = searchString
        )

        GlobalScope.launch(Dispatchers.Main) {
            updateState(RequestState.Progress)
            when (val result = authorsRepository.getAuthors(p)) {
                is Result.Success -> {
                    val data = result.data
                    updateState(RequestState.Success)
                    callback.onResult(data, key + 1)
                }
                is Result.Error -> onError(result.t)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Author>) = Unit

    private fun onError(t: Throwable) {
        updateState(RequestState.Error(t))
    }

    class Factory(
            private val authorsRepository: AuthorsRepository
    ) : DataSource.Factory<Long, Author>() {
        var searchString = ""
        val authorsDataSourceLiveData = MutableLiveData<AuthorsDataSource>()

        override fun create(): DataSource<Long, Author> =
            AuthorsDataSource(searchString, authorsRepository).also {
                authorsDataSourceLiveData.postValue(it)
            }
    }
}
