package com.amro.app.ui.view.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.app.domain.usecase.MovieUseCase
import com.amro.app.ui.mapper.MovieUiMapper.toUiMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(val useCase: MovieUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            try {
                val movies = useCase.getTrendingMovies()
                _uiState.value = MovieUiState.Success(items = movies.toUiMovies())
            } catch (e: Exception) {
                _uiState.value = MovieUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}