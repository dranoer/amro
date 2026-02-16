package com.amro.app.ui.model

data class MovieModel(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val genres: List<GenreModel>,
    val popularity: Double?,
    val releaseDate: String?
) {
    val genresText: String = genres.joinToString(separator = ", ") { it.name }
}