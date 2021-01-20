package ru.babaetskv.authorsprh.domain.model

import androidx.recyclerview.widget.DiffUtil

data class Author(
    val authorId: Long,
    val firstName: String,
    val lastName: String
) {

    companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Author>() {

            override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean =
                oldItem.authorId == newItem.authorId

            override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean = true
        }
    }
}
