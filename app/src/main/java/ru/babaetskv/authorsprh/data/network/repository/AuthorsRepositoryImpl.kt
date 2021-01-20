package ru.babaetskv.authorsprh.data.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.babaetskv.authorsprh.data.network.Api
import ru.babaetskv.authorsprh.data.network.DirectMappers
import ru.babaetskv.authorsprh.data.network.Result
import ru.babaetskv.authorsprh.data.network.model.GetAuthorsResponseModel
import ru.babaetskv.authorsprh.domain.repository.AuthorsRepository
import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.domain.model.GetAuthorsParams
import java.lang.Exception

class AuthorsRepositoryImpl(
    private val api: Api
) : AuthorsRepository {

    override suspend fun getAuthors(params: GetAuthorsParams): Result<List<Author>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response: GetAuthorsResponseModel =
                api.getAuthors(
                    start = params.offset,
                    max = params.offset + params.limit - 1,
                    lastName = params.searchString
                )
            val data: List<Author> = DirectMappers.mapGetAuthorsResponse.invoke(response)
            Result.Success(data)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
