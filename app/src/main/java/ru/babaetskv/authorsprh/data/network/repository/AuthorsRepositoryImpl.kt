package ru.babaetskv.authorsprh.data.network.repository

import ru.babaetskv.authorsprh.data.network.Api
import ru.babaetskv.authorsprh.data.network.DirectMappers
import ru.babaetskv.authorsprh.data.network.Result
import ru.babaetskv.authorsprh.data.network.model.AuthorModel
import ru.babaetskv.authorsprh.data.network.model.GetAuthorsResponseModel
import ru.babaetskv.authorsprh.domain.repository.AuthorsRepository
import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.domain.model.GetAuthorsParams
import ru.babaetskv.authorsprh.global.data.BaseRepository

class AuthorsRepositoryImpl(
    private val api: Api
) : BaseRepository(), AuthorsRepository {

    override suspend fun getAuthors(params: GetAuthorsParams): Result<List<Author>> = makeSafeCall {
        val response: GetAuthorsResponseModel =
                api.getAuthors(
                        start = params.offset,
                        max = params.offset + params.limit - 1,
                        lastName = params.searchString
                )
        DirectMappers.mapGetAuthorsResponse.invoke(response)
    }

    override suspend fun getAuthor(authorId: Long): Result<Author> = makeSafeCall {
        val response: AuthorModel = api.getAuthor(authorId)
        DirectMappers.mapAuthor(response)
    }
}
