package com.amro.app.data.mapper

import com.amro.app.core.Constants
import com.amro.app.data.remote.model.DetailDto
import com.amro.app.data.remote.model.GenreDto
import com.amro.app.data.remote.model.MovieDto
import com.amro.app.domain.model.Detail
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie

internal fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title ?: "",
    posterUrl = posterPath?.let { "${Constants.IMAGE_BASE_URL}${Constants.POSTER_SIZE_W342}$it" },
    genres = emptyList(),
    popularity = popularity ?: 0.0,
    releaseDate = releaseDate
)

internal fun GenreDto.toDomain(): Genre = Genre(
    id = id,
    name = name ?: ""
)

internal fun DetailDto.toDomain(): Detail = Detail(
    id = id,
    title = title ?: "",
    tagline = tagline,
    imageUrl = (backdropPath ?: posterPath)?.let { "${Constants.IMAGE_BASE_URL}${Constants.BACKDROP_SIZE_W780}$it" },
    genres = genres.map { it.toDomain() },
    description = overview,
    voteAverage = voteAverage,
    voteCount = voteCount,
    budget = budget,
    revenue = revenue,
    status = status,
    imdbId = imdbId,
    runtime = runtime,
    releaseDate = releaseDate
)