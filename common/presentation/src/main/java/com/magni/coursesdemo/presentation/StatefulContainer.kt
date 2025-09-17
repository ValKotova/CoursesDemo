package com.magni.coursesdemo.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.magni.coursesdemo.presentation.databinding.LayoutStatefulContainerBinding

class StatefulContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutStatefulContainerBinding

    var onRetryClickListener: (() -> Unit)? = null

    init {
        binding = LayoutStatefulContainerBinding.inflate(LayoutInflater.from(context), this, true)

        binding.retryButton.setOnClickListener {
            onRetryClickListener?.invoke()
        }

        hideAll()
    }

    fun showLoading() {
        hideAll()
        binding.progressBar.visibility = View.VISIBLE
    }

    fun showContent() {
        hideAll()
        binding.recyclerView.visibility = View.VISIBLE
    }

    fun showEmpty(
        emptyMessage: String = context.getString(R.string.empty_message_generic),
        emptyIcon: Drawable? = null
    ) {
        hideAll()
        binding.emptyState.visibility = View.VISIBLE

        emptyMessage.let { binding.emptyMessage.text = it }
        emptyIcon?.let { binding.emptyIcon.setImageDrawable(it) }
    }

    fun showError(errorMessage: String? = null, errorIcon: Drawable? = null) {
        hideAll()
        binding.errorView.visibility = View.VISIBLE

        errorMessage?.let { binding.errorMessage.text = it }
        errorIcon?.let { binding.errorIcon.setImageDrawable(it) }
    }

    fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.recyclerView.layoutManager = layoutManager
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        binding.recyclerView.adapter = adapter
    }

    private fun hideAll() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
        binding.errorView.visibility = View.GONE
    }
}