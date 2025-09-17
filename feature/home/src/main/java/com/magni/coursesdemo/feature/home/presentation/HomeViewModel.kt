package com.magni.coursesdemo.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magni.coursesdemo.presentation.UiState
import com.magni.coursesdemo.presentation.toUiState
import com.magni.coursesdemo.domain.entity.Course
import com.magni.coursesdemo.domain.useCases.GetCourcesUseCase
import com.magni.coursesdemo.domain.useCases.UpdateCourseLikeStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCourcesUseCase,
    private val updateCourseLikeStatusUseCase: UpdateCourseLikeStatusUseCase
) : ViewModel() {
    private val _courses = MutableStateFlow<UiState<List<Course>>>(UiState.Loading)
    val courses: StateFlow<UiState<List<Course>>> = _courses.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses(refresh: Boolean = false) {
        viewModelScope.launch {
            _courses.value = UiState.Loading
            _courses.value = getCoursesUseCase(refresh).toUiState()
        }
    }

    fun sortByDate() = viewModelScope.launch {
        val currentState = _courses.value
        if (currentState is UiState.Success) {
            _courses.emit(UiState.Success(currentState.data.sortedByDescending { it.startDate }))
        }
    }

    fun updateCourseLikeStatus(id: Int) {
        var isLiked: Boolean? = null
        viewModelScope.launch {
            val currentState = _courses.value
            if (currentState is UiState.Success) {
                val updatedCourses = currentState.data.map { course ->
                    if (course.id == id) {
                        val newCourse = course.copy(isLiked = !course.isLiked)
                        isLiked = !course.isLiked
                        newCourse
                    }
                    else
                        course
                }
                _courses.emit(UiState.Success(updatedCourses))
            }

            isLiked?.let {
                updateCourseLikeStatusUseCase(Pair(id, it))
                    .onFailure { error ->
                        val currentState = _courses.value
                        if (currentState is UiState.Success) {
                            val revertedCourses = currentState.data.map { course ->
                                if (course.id == id) course.copy(isLiked = !it) else course
                            }
                            _courses.emit(UiState.Success(revertedCourses))
                        }
                    }
            }
        }
    }
}