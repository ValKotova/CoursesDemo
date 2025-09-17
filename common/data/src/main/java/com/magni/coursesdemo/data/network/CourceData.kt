package com.magni.coursesdemo.data.network

import com.magni.coursesdemo.domain.entity.AppError
import com.magni.coursesdemo.domain.entity.Course
import java.time.DateTimeException
import java.time.LocalDate

data class CourceData(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,
    val imageUrl: String?
) {
    fun toDomain(): Course = Course(
        id = id,
        title = title,
        description = text,
        price = price,
        rating = rate.toFloatOrNull() ?: 0f,
        startDate = parseDate(startDate),
        isLiked = hasLike,
        imageUrl = imageUrl
    )

    private fun parseDate(dateString: String): LocalDate {
        return try {
            LocalDate.parse(dateString)
        } catch (e: DateTimeException) {
            throw AppError.DataConversionError(e)
        }
    }
}