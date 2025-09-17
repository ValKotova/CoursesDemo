package com.magni.coursesdemo.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magni.coursesdemo.presentation.UiState
import com.magni.coursesdemo.presentation.toUiState
import com.magni.coursesdemo.domain.entity.Course
import com.magni.coursesdemo.domain.useCases.GetFavoritesUseCase
import com.magni.coursesdemo.domain.useCases.UpdateCourseLikeStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val updateCourseLikeStatusUseCase: UpdateCourseLikeStatusUseCase,
) : ViewModel() {
    private val _favorites = MutableStateFlow<UiState<List<Course>>>(UiState.Loading)
    val favorites = _favorites.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses(refresh: Boolean = false) {
        viewModelScope.launch {
            _favorites.value = UiState.Loading
            _favorites.value = getFavoritesUseCase.invoke(Unit).toUiState()
        }
    }

    fun updateCourseLikeStatus(id: Int) {
        viewModelScope.launch {
            var oldList: List<Course>? = null
            val currentState = _favorites.value
            if (currentState is UiState.Success) {
                oldList = currentState.data
                val updatedCourses = currentState.data.mapNotNull { course ->
                    if (course.id == id) {
                        null
                    } else course
                }
                _favorites.value = UiState.Success(updatedCourses)
            }

            oldList?.let {
                updateCourseLikeStatusUseCase(Pair(id, false))
                    .onFailure { error ->
                        _favorites.value = UiState.Success(oldList)
                    }
            }
        }
    }
}