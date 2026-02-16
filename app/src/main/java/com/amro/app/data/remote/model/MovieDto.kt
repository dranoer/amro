package com.amro.app.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    @SerialName("results")
    val results: List<MovieDto> = emptyList()
)

@Serializable
data class MovieDto(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("release_date")
    val releaseDate: String? = null
)