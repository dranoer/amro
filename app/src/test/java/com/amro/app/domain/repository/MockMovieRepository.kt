package com.amro.app.domain.repository

import com.amro.app.core.ApiResult
import com.amro.app.core.ErrorType
import com.amro.app.domain.model.Detail
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie

class MockMovieRepository : MovieRepository {

    var shouldThrowNetworkError: Boolean = false
    var shouldThrowInvalidError: Boolean = false

    var movies: List<Movie> = listOf(
        Movie(
            id = 1,
            title = "Movie A",
            posterUrl = "",
            genres = null,
            popularity = 10.0,
            releaseDate = "2020-01-01"
        ),
        Movie(
            id = 2,
            title = "Movie B",
            posterUrl = "",
            genres = null,
            popularity = 20.0,
            releaseDate = "2021-01-01"
        ),
        Movie(
            id = 3,
            title = "Movie C",
            posterUrl = "",
            genres = null,
            popularity = 15.0,
            releaseDate = "2019-01-01"
        ),
    )

    var genres: List<Genre> = listOf(
        Genre(id = 35, name = "Comedy"),
        Genre(id = 12, name = "Adventure"),
    )

    var detail: Detail = Detail(
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

    override suspend fun getMovies(): ApiResult<List<Movie>> {
        if (shouldThrowNetworkError) return ApiResult.Error(ErrorType.Network)
        if (shouldThrowInvalidError) return ApiResult.Error(ErrorType.Invalid)
        return ApiResult.Success(movies)
    }

    override suspend fun getGenres(): ApiResult<List<Genre>> {
        if (shouldThrowNetworkError) return ApiResult.Error(ErrorType.Network)
        if (shouldThrowInvalidError) return ApiResult.Error(ErrorType.Invalid)
        return ApiResult.Success(genres)
    }

    override suspend fun getDetail(id: Int): ApiResult<Detail> {
        if (shouldThrowNetworkError) return ApiResult.Error(ErrorType.Network)
        if (shouldThrowInvalidError) return ApiResult.Error(ErrorType.Invalid)
        return ApiResult.Success(detail)
    }
}