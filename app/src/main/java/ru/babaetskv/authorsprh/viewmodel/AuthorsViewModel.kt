package ru.babaetskv.authorsprh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.domain.repository.AuthorsRepository
import ru.babaetskv.authorsprh.global.viewmodel.RequestState
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class AuthorsViewModel @Inject constructor(
    authorsRepository: AuthorsRepository
) : ViewModel() {
    private val authorsDataSourceFactory = AuthorsDataSource.Factory(authorsRepository, viewModelScope)
    private val searchChannel = BroadcastChannel<String>(Channel.BUFFERED)
    private var searchString = ""

    val authorsLiveData: LiveData<PagedList<Author>>

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(3)
            .build()
        authorsLiveData = LivePagedListBuilder(authorsDataSourceFactory, config).build()
        viewModelScope.launch {
            searchChannel.openSubscription().consumeAsFlow().debounce(SEARCH_REQUEST_DEBOUNCE_MILLIS).collect { searchRequest: String ->
                with (authorsDataSourceFactory) {
                    searchString = searchRequest
                    authorsDataSourceLiveData.value?.invalidate()
                }
            }
        }
    }

    override fun onCleared() {
        searchChannel.cancel()
        super.onCleared()
    }

    private fun setState(state: RequestState<*>) {
        authorsDataSourceFactory.authorsDataSourceLiveData.value?.updateState(state)
    }

    fun getState(): LiveData<RequestState<Any?>> =
        Transformations.switchMap<AuthorsDataSource, RequestState<*>>(
            authorsDataSourceFactory.authorsDataSourceLiveData,
            AuthorsDataSource::state
        )

    fun updateAuthors(searchString: String) {
        this.searchString = searchString
        updateAuthors()
    }

    fun updateAuthors() {
        viewModelScope.launch {
            searchChannel.send(searchString)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val SEARCH_REQUEST_DEBOUNCE_MILLIS = 1000L
    }
}
