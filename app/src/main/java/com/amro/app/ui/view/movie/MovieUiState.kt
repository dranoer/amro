package com.amro.app.ui.view.movie

import com.amro.app.ui.model.MovieModel

sealed class MovieUiState {
    data object Loading : MovieUiState()
    data class Success(val items: List<MovieModel>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}