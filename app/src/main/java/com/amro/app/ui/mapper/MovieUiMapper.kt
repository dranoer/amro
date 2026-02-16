package com.amro.app.ui.mapper

import com.amro.app.domain.model.Detail
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.ui.model.DetailModel
import com.amro.app.ui.model.GenreModel
import com.amro.app.ui.model.MovieModel

object MovieUiMapper {
    fun Movie.toUi(): MovieModel =
        MovieModel(
            id = id,
            title = title,
            imageUrl = posterUrl,
            genres = genres?.toUiGenres() ?: emptyList(),
            popularity = popularity,
            releaseDate = releaseDate
        )

    fun Genre.toUi(): GenreModel =
        GenreModel(
            id = id,
            name = name
        )

    fun Detail.toUi(): DetailModel =
        DetailModel(
            id = id,
            title = title,
            tagline = tagline,
            imageUrl = imageUrl,
            genres = genres.toUiGenres(),
            description = description,
            voteAverage = voteAverage,
            voteCount = voteCount,
            budget = budget,
            revenue = revenue,
            status = status,
            imdbId = imdbId,
            runtime = runtime,
            releaseDate = releaseDate
        )

    fun List<Movie>.toUiMovies(): List<MovieModel> =
        map { it.toUi() }

    fun List<Genre>.toUiGenres(): List<GenreModel> =
        map { it.toUi() }
}