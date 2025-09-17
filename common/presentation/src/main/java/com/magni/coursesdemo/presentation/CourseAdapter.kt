package com.magni.coursesdemo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.magni.coursesdemo.domain.entity.Course
import eightbitlab.com.blurview.BlurTarget
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class CourseAdapter : ListAdapter<Course, CourseAdapter.ViewHolder>(DiffCallback()) {

    var onLikeClicked: ((Course) -> Unit)? = null
    var onMoreClicked: ((Course) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val combinedPayload = Bundle()
            for (payload in payloads) {
                if (payload is Bundle) {
                    combinedPayload.putAll(payload)
                }
            }

            val course = getItem(position)
            if (combinedPayload.containsKey("IS_LIKED")) {
                holder.updateLikeButton(course)
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.tvTitle)
        private val descriptionView: TextView = itemView.findViewById(R.id.tvDescription)
        private val priceView: TextView = itemView.findViewById(R.id.tvPrice)
        private val ratingView: TextView = itemView.findViewById(R.id.tvRating)
        private val dateView: TextView = itemView.findViewById(R.id.tvDate)
        private val imageView: ImageView = itemView.findViewById(R.id.ivCourseImage)
        private val likeButton: ImageButton = itemView.findViewById(R.id.ibLike)
        private val moreContainer: MaterialButton = itemView.findViewById(R.id.btnMore)
        private val cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
        private val rating: TextView = itemView.findViewById(R.id.tvRating)
        private val date: TextView = itemView.findViewById(R.id.tvDate)
        private val blurTarget: BlurTarget = itemView.findViewById(R.id.blurTarget)
        private val likeButtonContainer: eightbitlab.com.blurview.BlurView = itemView.findViewById(R.id.likeButtonContainer)
        private val ratingContainer: eightbitlab.com.blurview.BlurView = itemView.findViewById(R.id.ratingContainer)
        private val dateContainer: eightbitlab.com.blurview.BlurView = itemView.findViewById(R.id.dateContainer)

        fun bind(course: Course) {
            titleView.text = course.title
            descriptionView.text = course.description
            priceView.text = itemView.context.getString(R.string.price_format, course.price)
            ratingView.text = String.format("%.1f", course.rating)
            dateView.text = formatDate(course.startDate)

            rating.text = String.format("%.1f", course.rating)
            date.text = formatDate(course.startDate)

            if (!course.imageUrl.isNullOrEmpty()) {
                if ((course.imageUrl as String).startsWith("android.resource://")) {
                    val resourceId = try {
                        (course.imageUrl as String).substringAfterLast("/").toInt()
                    } catch (e: Exception) {
                        R.drawable.placeholder
                    }
                    Glide.with(itemView)
                        .load(resourceId)
                        .transform(FillWidthTransformation())
                        .into(imageView)
                } else {
                    Glide.with(itemView)
                        .load(course.imageUrl)
                        .transform(FillWidthTransformation())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(imageView)
                }
            } else {
                imageView.setImageResource(R.drawable.placeholder)
            }

            updateLikeButton(course)

            setupBlurView(likeButtonContainer, blurTarget)
            setupBlurView(ratingContainer, blurTarget)
            setupBlurView(dateContainer, blurTarget)


            likeButton.setOnClickListener { onLikeClicked?.invoke(course) }
            moreContainer.setOnClickListener { onMoreClicked?.invoke(course) }
        }

        fun updateLikeButton(course: Course) {
            val likeIcon = if (course.isLiked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
            likeButton.setImageResource(likeIcon)

            val colorPrimary = ContextCompat.getColor(itemView.context, R.color.colorPrimary)
            val colorWhite = ContextCompat.getColor(itemView.context, R.color.white)
            likeButton.setColorFilter(if (course.isLiked) colorPrimary else colorWhite)
        }

        private fun formatDate(date: LocalDate): String {
            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
            return date.format(formatter)
        }

        private fun setupBlurView(
            blurView: eightbitlab.com.blurview.BlurView,
            blurTarget: BlurTarget) {

            blurView.setupWith(blurTarget)
                .setBlurRadius(16f)
                .setBlurAutoUpdate(true)

            // For rounded corners
            blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
            blurView.clipToOutline = true
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return  oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.price == newItem.price &&
                    oldItem.rating == newItem.rating &&
                    oldItem.startDate == newItem.startDate &&
                    oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.isLiked == newItem.isLiked
        }

        override fun getChangePayload(oldItem: Course, newItem: Course): Any? {
            val diff = Bundle()

            if (oldItem.isLiked != newItem.isLiked) {
                diff.putBoolean("IS_LIKED", newItem.isLiked)
            }

            return if (diff.size() > 0) diff else null
        }
    }
}