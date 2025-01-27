package ru.babaetskv.authorsprh.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.mikepenz.fastadapter.paged.PagedModelAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.babaetskv.authorsprh.MainApplication
import ru.babaetskv.authorsprh.R
import ru.babaetskv.authorsprh.Screens
import ru.babaetskv.authorsprh.databinding.FragmentSearchBinding
import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.global.ui.BaseFragment
import ru.babaetskv.authorsprh.global.ui.EmptyDividerDecoration
import ru.babaetskv.authorsprh.global.viewmodel.RequestState
import ru.babaetskv.authorsprh.ui.item.AuthorItem
import ru.babaetskv.authorsprh.utils.setGone
import ru.babaetskv.authorsprh.utils.setVisible
import ru.babaetskv.authorsprh.utils.viewBinding
import ru.babaetskv.authorsprh.viewmodel.AuthorsViewModel
import javax.inject.Inject

@ExperimentalPagedSupport
@ExperimentalCoroutinesApi
@FlowPreview
class SearchFragment : BaseFragment() {
    @Inject lateinit var viewModel: AuthorsViewModel
    @Inject lateinit var router: Router

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)
    private lateinit var adapter: FastAdapter<AuthorItem>
    private lateinit var itemAdapter: PagedModelAdapter<Author, AuthorItem>

    override val layoutRes: Int
        get() = R.layout.fragment_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
        initViewModel()
        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.updateAuthors(text.toString())
        }
    }

    override fun onPause() {
        binding.etSearch.doOnTextChanged { _, _, _, _ ->  }
        super.onPause()
    }

    private fun initViewModel() {
        viewModel.getState().observe(this, Observer { status ->
            status ?: return@Observer

            when (status) {
                is RequestState.Progress -> {
                    showProgress()
                    showEmpty("", false)
                }
                is RequestState.Success -> {
                    hideProgress()
                    showEmpty("", false)
                }
                is RequestState.NoData -> {
                    hideProgress()
                    showEmpty(getString(R.string.empty_authors_list), true)
                }
                is RequestState.Error -> {
                    status.error.printStackTrace()
                    hideProgress()
                    showEmpty(getString(R.string.error_authors_list), true)
                }
            }
        })
        viewModel.authorsLiveData.observe(this, { authors ->
            itemAdapter.submitList(authors)
        })
    }

    private fun initAdapter() {
        val config = AsyncDifferConfig.Builder(Author.CALLBACK).build()
        val placeholderInterceptor = { _: Int -> AuthorItem(null) }
        val placeholder = { author: Author -> AuthorItem(author) }
        itemAdapter = PagedModelAdapter<Author, AuthorItem>(config, placeholderInterceptor, placeholder)
        adapter = FastAdapter.with(itemAdapter).apply {
            setHasStableIds(true)
            onClickListener = object : ClickListener<AuthorItem> {

                override fun invoke(
                    v: View?,
                    adapter: IAdapter<AuthorItem>,
                    item: AuthorItem,
                    position: Int
                ): Boolean =
                    item.author?.let {
                        router.navigateTo(Screens.Author(item.author.authorId))
                        true
                    } ?: false
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerAuthors.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
            itemAnimator = null
            addItemDecoration(EmptyDividerDecoration(requireContext(), R.dimen.margin_default, false))
        }
    }

    private fun showProgress() {
        binding.progress.setVisible()
    }

    private fun hideProgress() {
        binding.progress.setGone()
    }

    private fun showEmpty(message: String, show: Boolean) {
        with (binding.emptyView) {
            if (show) {
                setMessage(message)
                setActionText(R.string.try_again)
                setActionCallback {
                    viewModel.updateAuthors()
                }
                setVisible()
            } else setGone()
        }
    }
}
