package ru.babaetskv.authorsprh.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.babaetskv.authorsprh.data.network.Result
import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.domain.repository.AuthorsRepository
import ru.babaetskv.authorsprh.global.viewmodel.RequestState
import javax.inject.Inject

class AuthorViewModel @Inject constructor(
        private val authorsRepository: AuthorsRepository
) : ViewModel() {
    var authorId = -1L
    val state = MutableLiveData<RequestState<Author>>()
    val authorLiveData = MutableLiveData<Author>()

    fun loadAuthor(authorId: Long) {
        this.authorId = authorId
        loadAuthor()
    }

    fun loadAuthor() {
        GlobalScope.launch(Dispatchers.Main) {
            state.value = RequestState.Progress
            when (val result = authorsRepository.getAuthor(authorId)) {
                is Result.Success -> {
                    state.value = RequestState.Success
                    authorLiveData.value = result.data
                }
                is Result.Error -> {
                    state.value = RequestState.Error(result.t)
                }
            }
        }
    }
}
