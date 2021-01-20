package ru.babaetskv.authorsprh.di

import android.content.Context
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import ru.babaetskv.authorsprh.BuildConfig
import ru.babaetskv.authorsprh.data.network.Api
import ru.babaetskv.authorsprh.data.network.repository.AuthorsRepositoryImpl
import ru.babaetskv.authorsprh.data.prefs.PreferencesProvider
import ru.babaetskv.authorsprh.domain.repository.AuthorsRepository
import ru.babaetskv.authorsprh.ui.MainActivity
import ru.babaetskv.authorsprh.ui.fragments.AuthorFragment
import ru.babaetskv.authorsprh.ui.fragments.SearchFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ApiModule::class,
    RetrofitModule::class,
    NavigationModule::class,
    RepositoryModule::class,
    PrefsModule::class
])
interface AppComponent {
    fun inject(activity: MainActivity)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagedSupport
    fun inject(fragment: SearchFragment)
    fun inject(fragment: AuthorFragment)
}

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context = context
}

@Module
class PrefsModule {

    @Singleton
    @Provides
    fun providePreferencesProvider(context: Context): PreferencesProvider =
            PreferencesProvider(context)
}

@Module
class RepositoryModule {

    @Provides
    fun provideAuthorsRepository(api: Api): AuthorsRepository = AuthorsRepositoryImpl(api)
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
    private val httpLoggingInterceptor: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    private val headerInterceptor: Interceptor
        get() = Interceptor { chain ->
            val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
            chain.proceed(request)
        }

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .baseUrl(BuildConfig.API_URL)
            .build()
    }
}
