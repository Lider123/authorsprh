package ru.babaetskv.authorsprh.data.network.model

import com.google.gson.annotations.SerializedName

data class AuthorModel(
    @SerializedName("authorid") val authorId: Long,
    @SerializedName("authorfirst") val firstName: String,
    @SerializedName("authorlast") val lastName: String
)
