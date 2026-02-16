package com.amro.app.domain.model

data class Detail(
    val id: Int,
    val title: String,
    val tagline: String?,
    val imageUrl: String?,
    val genres: List<Genre>,
    val description: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val budget: Long?,
    val revenue: Long?,
    val status: String?,
    val imdbId: String?,
    val runtime: Int?,
    val releaseDate: String?
)