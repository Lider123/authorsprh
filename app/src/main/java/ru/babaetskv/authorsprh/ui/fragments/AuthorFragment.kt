package ru.babaetskv.authorsprh.ui.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import ru.babaetskv.authorsprh.MainApplication
import ru.babaetskv.authorsprh.R
import ru.babaetskv.authorsprh.databinding.FragmentAuthorBinding
import ru.babaetskv.authorsprh.global.ui.BaseFragment
import ru.babaetskv.authorsprh.global.viewmodel.RequestState
import ru.babaetskv.authorsprh.utils.setGone
import ru.babaetskv.authorsprh.utils.setVisible
import ru.babaetskv.authorsprh.utils.viewBinding
import ru.babaetskv.authorsprh.viewmodel.AuthorViewModel
import javax.inject.Inject

class AuthorFragment : BaseFragment() {
    @Inject lateinit var viewModel: AuthorViewModel

    private val binding: FragmentAuthorBinding by viewBinding(FragmentAuthorBinding::bind)

    override val layoutRes: Int
        get() = R.layout.fragment_author

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
        val authorId = requireArguments().getLong(ARG_AUTHOR_ID, -1L)
        initViewModel(authorId)
    }

    private fun initViewModel(authorId: Long) {
        viewModel.state.observe(this, Observer { status ->
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
                    showEmpty(getString(R.string.empty_author), true)
                }
                is RequestState.Error -> {
                    status.error.printStackTrace()
                    hideProgress()
                    showEmpty(getString(R.string.error_author), true)
                }
            }
        })
        viewModel.authorLiveData.observe(this, { author ->
            with (binding) {
                tvFullName.text = author.displayName
                tvDescription.text = author.spotlight ?: getString(R.string.no_author_description)
            }
        })
        viewModel.loadAuthor(authorId)
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
                    viewModel.loadAuthor()
                }
                setVisible()
            } else setGone()
        }
    }

    companion object {
        const val ARG_AUTHOR_ID = "ARG_AUTHOR_ID"
    }
}
