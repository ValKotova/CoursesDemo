package com.magni.coursesdemo.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.magni.coursesdemo.domain.entity.Course
import java.time.Instant
import java.time.ZoneId

@Entity(tableName = "courses")
data class CourceEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: Float,
    val startDate: Long,
    val isLiked: Boolean,
    val imageUrl: String?
) {
    fun toDomain(): Course = Course(
        id = id,
        title = title,
        description = description,
        price = price,
        rating = rating,
        startDate = Instant.ofEpochMilli(startDate).atZone(ZoneId.systemDefault()).toLocalDate(),
        isLiked = isLiked,
        imageUrl = imageUrl
    )
}

fun Course.toEntity(): CourceEntity {
    return CourceEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        rating = rating,
        startDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        isLiked = isLiked,
        imageUrl = imageUrl
    )
}