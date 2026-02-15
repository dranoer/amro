package com.amro.app.ui.view.movie

import com.amro.app.domain.model.Movie

sealed class MovieUiState {
    data object Loading : MovieUiState()
    data class Success(val items: List<Movie>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}