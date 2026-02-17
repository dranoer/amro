package com.amro.app.ui.model

import java.text.NumberFormat
import java.util.Locale

data class DetailModel(
    val id: Int,
    val title: String,
    val tagline: String?,
    val imageUrl: String?,
    val genres: List<GenreModel>,
    val description: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val budget: Long?,
    val revenue: Long?,
    val status: String?,
    val imdbId: String?,
    val runtime: Int?,
    val releaseDate: String?
) {
    private val formatter = NumberFormat.getCurrencyInstance(Locale.US)

    val genresText: String = genres.joinToString(separator = ", ") { it.name }
    
    val budgetText: String? = budget?.takeIf { it > 0 }?.let { formatter.format(it) }
    
    val revenueText: String? = revenue?.takeIf { it > 0 }?.let { formatter.format(it) }
    
    val imdbUrl: String? = imdbId?.let { "https://www.imdb.com/title/$it/" }
}
