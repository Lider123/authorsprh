package ru.babaetskv.authorsprh.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.babaetskv.authorsprh.BuildConfig
import ru.babaetskv.authorsprh.data.network.Api
import ru.babaetskv.authorsprh.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RetrofitModule::class, NavigationModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}

@Module
class NavigationModule {
    private val cicerone by lazy { Cicerone.create() }

    @Provides
    @Singleton
    fun provideRouter(): Router = cicerone.router

    @Provides
    @Singleton
    fun provideNavigationHolder(): NavigatorHolder = cicerone.getNavigatorHolder()
}

@Module
class RetrofitModule {
    private val httpLoggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .serializeNulls()
            .create()
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.API_URL)
            .build()
    }
}
