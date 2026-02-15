package com.amro.app.domain.repository

import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.domain.model.MovieDetail

interface MoviesRepository {
    suspend fun getTrendingMovies(): List<Movie> {
        return emptyList()
    }

    suspend fun getGenres(): List<Genre> {
        return emptyList()
    }

    suspend fun getMovieDetail(id: Int): MovieDetail {
        return MovieDetail(
            id = id,
            title = "title",
            tagline = null,
            imageUrl = null,
            genres = emptyList(),
            description = null,
            voteAverage = null,
            voteCount = null,
            budget = null,
            revenue = null,
            status = null,
            imdbId = null,
            runtime = null,
            releaseDate = null
        )
    }
}