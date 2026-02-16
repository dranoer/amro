package com.amro.app.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailDto(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String? = null,

    @SerialName("tagline")
    val tagline: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genres")
    val genres: List<GenreDto> = emptyList(),

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    @SerialName("budget")
    val budget: Long? = null,

    @SerialName("revenue")
    val revenue: Long? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("imdb_id")
    val imdbId: String? = null,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("release_date")
    val releaseDate: String? = null
)