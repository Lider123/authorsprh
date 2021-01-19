package ru.babaetskv.authorsprh.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.babaetskv.authorsprh.MainApplication
import ru.babaetskv.authorsprh.R
import javax.inject.Inject

class MainActivity : FragmentActivity() {
    @Inject lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainApplication.appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}