package com.amro.app.ui.view.movie

import com.amro.app.R
import com.amro.app.ui.model.GenreModel
import com.amro.app.ui.model.MovieModel

sealed class MovieUiState {
    data object Loading : MovieUiState()

    data class Success(
        val items: List<MovieModel>,
        val genres: List<GenreModel> = emptyList(),
        val selectedGenre: GenreModel? = null,
        val sortOrder: SortOrder = SortOrder.POPULARITY_DESC
    ) : MovieUiState()

    data class Error(val message: String) : MovieUiState()
}

enum class SortOrder(val resId: Int) {
    POPULARITY_DESC(R.string.popularity_desc),
    POPULARITY_ASC(R.string.popularity_asc),
    TITLE_ASC(R.string.title_asc),
    TITLE_DESC(R.string.title_desc),
    RELEASE_DATE_DESC(R.string.release_date_desc),
    RELEASE_DATE_ASC(R.string.release_date_asc)
}