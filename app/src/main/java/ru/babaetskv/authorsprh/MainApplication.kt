package ru.babaetskv.authorsprh

import android.app.Application
import ru.babaetskv.authorsprh.di.*

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .navigationModule(NavigationModule())
                .apiModule(ApiModule())
                .retrofitModule(RetrofitModule())
                .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
