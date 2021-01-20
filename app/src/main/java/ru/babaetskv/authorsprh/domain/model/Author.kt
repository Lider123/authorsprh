package ru.babaetskv.authorsprh.domain.model

import androidx.recyclerview.widget.DiffUtil

data class Author(
    val authorId: Long,
    val displayName: String,
    val spotlight: String?
) {

    companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Author>() {

            override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean =
                oldItem.authorId == newItem.authorId

            override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean = true
        }
    }
}
