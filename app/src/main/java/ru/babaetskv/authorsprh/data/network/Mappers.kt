package ru.babaetskv.authorsprh.data.network

import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.data.network.model.AuthorModel
import ru.babaetskv.authorsprh.data.network.model.GetAuthorsResponseModel

object DirectMappers {

    val mapGetAuthorsResponse: (GetAuthorsResponseModel) -> List<Author> =
        { src: GetAuthorsResponseModel ->
            src.authors?.map(mapAuthor) ?: listOf()
        }

    val mapAuthor: (AuthorModel) -> Author = { src: AuthorModel ->
        Author(
            authorId = src.authorId,
            displayName = src.displayName,
            spotlight = src.spotlight
        )
    }
}