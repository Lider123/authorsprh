package ru.babaetskv.authorsprh.domain.model

data class GetAuthorsParams(
    val limit: Long,
    val offset: Long,
    val searchString: String
)
