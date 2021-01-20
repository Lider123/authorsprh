package ru.babaetskv.authorsprh.data.prefs

import android.content.Context
import javax.inject.Inject

class PreferencesProvider @Inject constructor(context: Context) {
    private val prefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
    var searchQuery: String
        get() = prefs.getString(PREF_SEARCH_QUERY, "") ?: ""
        set(value) {
            prefs.edit().putString(PREF_SEARCH_QUERY, value).apply()
        }

    companion object {
        private const val APP_PREFS = "app_prefs"
        private const val PREF_SEARCH_QUERY = "pref_search_query"
    }
}