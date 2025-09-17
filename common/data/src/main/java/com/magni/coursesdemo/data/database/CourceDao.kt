package com.magni.coursesdemo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CourceDao {
    @Query("SELECT * FROM courses")
    suspend fun getAll(): List<CourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(courses: List<CourceEntity>)

    @Query("UPDATE courses SET isLiked = :isLiked WHERE id = :id")
    suspend fun updateLikeStatus(id: Int, isLiked: Boolean)

    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getById(id: Int): CourceEntity?

    @Query("DELETE FROM courses")
    suspend fun deleteAll()

    @Query("SELECT * FROM courses WHERE isLiked = 1")
    suspend fun getFavoriteCourses(): List<CourceEntity>
}