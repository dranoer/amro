package com.amro.app.domain.usecase

import com.amro.app.core.ApiResult
import com.amro.app.domain.model.Detail
import com.amro.app.domain.model.Genre
import com.amro.app.domain.model.Movie
import com.amro.app.domain.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun getTrendingMovies(): ApiResult<List<Movie>> {
        return repository.getMovies()
    }

    suspend fun getMovieDetail(id: Int): ApiResult<Detail> {
        return repository.getDetail(id)
    }

    suspend fun getGenres(): ApiResult<List<Genre>> {
        return repository.getGenres()
    }
}