package ru.babaetskv.authorsprh.domain.repository

import ru.babaetskv.authorsprh.data.network.Result
import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.domain.model.GetAuthorsParams

interface AuthorsRepository {
    suspend fun getAuthors(params: GetAuthorsParams): Result<List<Author>>
}
