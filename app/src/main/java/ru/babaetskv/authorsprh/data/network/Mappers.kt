package ru.babaetskv.authorsprh.data.network

import ru.babaetskv.authorsprh.domain.model.Author
import ru.babaetskv.authorsprh.data.network.model.AuthorModel

object DirectMappers {

    val mapAuthor = { src: AuthorModel ->
        Author(
            authorId = src.authorId,
            firstName = src.firstName,
            lastName = src.lastName
        )
    }
}