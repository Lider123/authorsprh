package ru.babaetskv.authorsprh

import androidx.core.os.bundleOf
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.ui.fragments.AuthorFragment
import ru.babaetskv.authorsprh.ui.fragments.SearchFragment

object Screens {

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagedSupport
    fun Search() = FragmentScreen {
        SearchFragment().apply {
            arguments = bundleOf()
        }
    }

    fun Author(authorId: Long) = FragmentScreen {
        AuthorFragment().apply {
            arguments = bundleOf(
                    AuthorFragment.ARG_AUTHOR_ID to authorId
            )
        }
    }
}