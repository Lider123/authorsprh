package ru.babaetskv.authorsprh.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.button.MaterialButton
import ru.babaetskv.authorsprh.R
import ru.babaetskv.authorsprh.utils.setGone
import ru.babaetskv.authorsprh.utils.setVisible

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRef: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRef) {
    private var actionCallback: (() -> Unit)? = null
    private val btnAction: MaterialButton
    private val tvMessage: AppCompatTextView

    init {
        View.inflate(context, R.layout.view_empty, this)
        btnAction = findViewById(R.id.btnAction)
        tvMessage = findViewById(R.id.tvMessage)
        val a = context.obtainStyledAttributes(attrs, R.styleable.EmptyView)
        val message = a.getString(R.styleable.EmptyView_message)
        val action = a.getString(R.styleable.EmptyView_action)
        a.recycle()
        btnAction.setOnClickListener {
            actionCallback?.invoke()
        }
        setMessage(message)
        setActionText(action)
    }

    fun setMessage(text: String?) {
        tvMessage.text = text
        if (text.isNullOrEmpty()) tvMessage.setGone() else tvMessage.setVisible()
    }

    fun setMessage(@StringRes stringRes: Int) = setMessage(resources.getString(stringRes))

    fun setActionText(text: String?) {
        btnAction.text = text
        if (text.isNullOrEmpty()) btnAction.setGone() else btnAction.setVisible()
    }

    fun setActionText(@StringRes stringRes: Int) = setActionText(resources.getString(stringRes))

    fun setActionCallback(callback: (() -> Unit)?) {
        actionCallback = callback
    }
}
