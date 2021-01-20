package ru.babaetskv.authorsprh.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.babaetskv.authorsprh.MainApplication
import ru.babaetskv.authorsprh.R
import ru.babaetskv.authorsprh.Screens
import javax.inject.Inject

class MainActivity : FragmentActivity() {
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var router: Router

    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainApplication.appComponent.inject(this)
        router.newRootScreen(Screens.Search())
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