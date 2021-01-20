package ru.babaetskv.authorsprh

import androidx.core.os.bundleOf
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.babaetskv.authorsprh.ui.fragments.SearchFragment

object Screens {
    fun Search() = FragmentScreen {
        SearchFragment().apply {
            arguments = bundleOf()
        }
    }
}