package com.amro.app.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val genres: List<Genre>?,
    val popularity: Double?,
    val releaseDate: String?
)