package ru.babaetskv.authorsprh.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.babaetskv.authorsprh.data.network.model.AuthorModel

interface Api {

    @GET("resources/authors")
    suspend fun getAuthors(
        @Query("start") start: Long,
        @Query("max") max: Long,
        @Query("lastName") lastName: String,
        @Query("expandLevel") expandLevel: Int = 1
    ): List<AuthorModel>
}
