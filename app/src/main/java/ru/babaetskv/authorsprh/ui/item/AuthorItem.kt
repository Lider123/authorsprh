package ru.babaetskv.authorsprh.ui.item

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import ru.babaetskv.authorsprh.R
import ru.babaetskv.authorsprh.domain.model.Author

class AuthorItem(val author: Author?) : AbstractItem<AuthorItem.ViewHolder>() {
    override val layoutRes: Int
        get() = R.layout.item_author
    override val type: Int
        get() = R.id.item_author

    override fun getViewHolder(v: View) = ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<AuthorItem>(view) {
        private val tvFullName = view.findViewById<AppCompatTextView>(R.id.tvFullName)

        override fun bindView(item: AuthorItem, payloads: List<Any>) {
            val data = item.author ?: run {
                bindPlaceholder()
                return
            }
            tvFullName.text = data.displayName
        }

        override fun unbindView(item: AuthorItem) = Unit

        private fun bindPlaceholder() {
            tvFullName.text = "..."
        }
    }
}
