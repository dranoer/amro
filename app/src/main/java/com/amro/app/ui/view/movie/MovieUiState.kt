package com.amro.app.ui.view.movie

import com.amro.app.ui.model.GenreModel
import com.amro.app.ui.model.MovieModel

sealed class MovieUiState {
    data object Loading : MovieUiState()

    data class Success(
        val items: List<MovieModel>,
        val genres: List<GenreModel> = emptyList(),
        val selectedGenre: GenreModel? = null
    ) : MovieUiState()

    data class Error(val message: String) : MovieUiState()
}