package com.magni.coursesdemo.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magni.coursesdemo.domain.entity.Course
import com.magni.coursesdemo.domain.useCases.GetCourcesUseCase
import com.magni.coursesdemo.presentation.UiState
import com.magni.coursesdemo.presentation.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCoursesUseCase: GetCourcesUseCase
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
}