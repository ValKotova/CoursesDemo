package com.magni.coursesdemo.domain.entity

import java.time.LocalDate

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: Float,
    val startDate: LocalDate,
    val isLiked: Boolean,
    val imageUrl: String?
)