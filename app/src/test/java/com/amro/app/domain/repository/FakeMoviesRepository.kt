package com.amro.app.domain.repository

import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.domain.model.MovieDetail

class FakeMoviesRepository : MoviesRepository {

    var shouldThrow: Boolean = false

    var movies: List<Movie> = listOf(
        Movie(
            id = 1,
            title = "Movie A",
            posterUrl = "",
            genreIds = listOf(35),
            popularity = 10.0,
            releaseDate = "2020-01-01"
        ),
        Movie(
            id = 2,
            title = "Movie B",
            posterUrl = "",
            genreIds = listOf(12),
            popularity = 20.0,
            releaseDate = "2021-01-01"
        ),
        Movie(
            id = 3,
            title = "Movie C",
            posterUrl = "",
            genreIds = listOf(35, 12),
            popularity = 15.0,
            releaseDate = "2019-01-01"
        ),
    )

    var genres: List<Genre> = listOf(
        Genre(id = 35, name = "Comedy"),
        Genre(id = 12, name = "Adventure"),
    )

    var detail: MovieDetail = MovieDetail(
        id = 1,
        title = "Movie A",
        tagline = "Tagline",
        imageUrl = "",
        genres = listOf(Genre(35, "Comedy")),
        description = "Description",
        voteAverage = 8.1,
        voteCount = 100,
        budget = 1_000_000,
        revenue = 10_000_000,
        status = "Released",
        imdbId = "tt123",
        runtime = 120,
        releaseDate = "2020-01-01"
    )

    override suspend fun getTrendingMovies(): List<Movie> {
        if (shouldThrow) throw RuntimeException("Something went wrong.")
        return movies
    }

    override suspend fun getGenres(): List<Genre> {
        if (shouldThrow) throw RuntimeException("Something went wrong.")
        return genres
    }

    override suspend fun getMovieDetail(id: Int): MovieDetail {
        if (shouldThrow) throw RuntimeException("Something went wrong.")
        return detail
    }
}